import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Player extends Thread {

    //define attributes
    private volatile boolean done = false;
    private int name;
    private ArrayList<Card> hand = new ArrayList<Card>();;
    private Deck drawDeck;
    private Deck dropDeck;
    private String actionLogFilename;
    private final boolean[] flags;
    private ArrayList<Card> discardList = new ArrayList<Card>(); //discard list contains all cards that the player wants to drop - none that have the same name as them

    //constructor
    public Player(int name, Deck drawDeck, Deck dropDeck, boolean[] flags){
        this.name = name;
        this.drawDeck = drawDeck;
        this.dropDeck = dropDeck;
        this.flags = flags;

        //creates file name for player actions to be written to
        actionLogFilename = "player"+ (Integer.toString(name)) + "_output.txt";
    }

    public synchronized Deck getDrawDeck(){
        return drawDeck;
    }

    public synchronized Deck getDropDeck(){
        return dropDeck;
    }

    public synchronized void addCardToHand(Card card)
    {
        hand.add(card);
        if (card.getValue() != this.name){
            discardList.add(card); //as the player never gets rid of a card with the same value as their name, only other cards are added to the discard list
        }
        if (hand.size() == 4) //once hand is full
        {
            String currentHand = "player "+(Integer.toString(name)+" initial hand: ");
            for (Card handCard : hand){
                currentHand += Integer.toString(handCard.getValue()) + " ";
            }

            writeToLog(currentHand, false);  //output initial hand to log and overwrite anything from previous games
        }
    }

    public void writeToLog(String message, Boolean append){
        try {
            FileWriter writer = new FileWriter(actionLogFilename, append);
            writer.write(message + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error."); //catch IO exceptions
        }
    }

    public synchronized Boolean checkWin(){
        int value = hand.get(0).getValue();  //checks all cards are the same as the first card
        for (Card card : hand){
            if (value != card.getValue()){
                return false;
            }
        }
        return true; //returns true if they are as the player has won
    }

    public synchronized Card getDiscardedCard(){
        double i = Math.floor(Math.random() * discardList.size()); //picks a random card from the discard list
        return discardList.get((int) i);
    }
    public synchronized void run(){
        while(!done)
        {
            Card discardedCard = getDiscardedCard();
            dropDeck.addCard(discardedCard); // add discarded card to the next deck
            notifyAll();  // notify all waiting threads that the state has changed
            //dropDeck.writeToLog();
            hand.remove(discardedCard);

            if (discardList.size() != 0) // make sure doesn't discard if there are no cards to discard
            {
                discardList.remove(discardedCard);
            }

            String discardMessage = "player "+(Integer.toString(name))+" discards a "+ (Integer.toString(discardedCard.getValue())) +" to deck " +dropDeck.getName();

            Card newCard = null;
            while (newCard == null) // wait until the draw deck isn't empty
            {
                try
                {
                    newCard = drawDeck.getCard(0);
                    drawDeck.removeCard(0);
                    hand.add(newCard);
                    if (newCard.getValue() != name){
                        discardList.add(newCard); // add to discard list if not same as player name
                    }
                } catch (IndexOutOfBoundsException a) // catch if the draw deck is empty
                {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e); // throw a runtime exception if the thread is interrupted while waiting
                    }
                }
            }


            String drawMessage = "player "+(Integer.toString(name))+" draws a "+ (Integer.toString(newCard.getValue())) +" from deck " +drawDeck.getName();
            //add actions to log
            writeToLog(drawMessage, true);
            writeToLog(discardMessage, true);

            //if the thread has won, change the flag array
            if (checkWin())
            {
                flags[name-1] = true;
                break;
            }

            // add updated hand to log
            String currentHand = "player "+(Integer.toString(name)+" hand: ");
            for (Card card : hand){
                currentHand += Integer.toString(card.getValue()) + " ";
            }

            writeToLog(currentHand, true);
        }
    }

    public void stopThread(int PlayerNameWon)
    {
        // write final messages to log
        writeToLog("player "+(Integer.toString(PlayerNameWon))+" wins", true);
        writeToLog("player "+(Integer.toString(name))+" exits", true);

        String currentHand = "player "+(Integer.toString(name)+" final hand: ");
        for (Card card : hand){
            currentHand += Integer.toString(card.getValue()) + " ";
        }

        writeToLog(currentHand, true);
        this.done = true; // update done so thread stops running
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public String getActionLogFilename() {
        return actionLogFilename;
    }

    public int getPlayerName()
    {
        return name;
    }

    public ArrayList<Card> getDiscardList() {
        return discardList;
    }
}