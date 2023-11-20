import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Player extends Thread {

    private volatile boolean done = false;
    private int name;
    private ArrayList<Card> hand = new ArrayList<Card>();;
    private Deck drawDeck;
    private Deck dropDeck;
    private String actionLogFilename;
    private final boolean[] flags;
    private ArrayList<Card> discardList = new ArrayList<Card>();;


    public Player(int name, Deck drawDeck, Deck dropDeck, boolean[] flags){
        this.name = name;
        this.drawDeck = drawDeck;
        this.dropDeck = dropDeck;
        this.flags = flags;

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
            discardList.add(card);
        }
        if (hand.size() == 4)
        {
            String currentHand = "player "+(Integer.toString(name)+" initial hand: ");
            for (Card handCard : hand){
                currentHand += Integer.toString(handCard.getValue()) + " ";
            }

            writeToLog(currentHand, false);
        }
    }

    public void writeToLog(String message, Boolean append){
        try {
            FileWriter writer = new FileWriter(actionLogFilename, append);
            writer.write(message + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error.");
        }
    }

    public synchronized Boolean checkWin(){
        int value = hand.get(0).getValue();
        for (Card card : hand){
            if (value != card.getValue()){
                return false;
            }
        }
        return true;
    }

    public synchronized Card getDiscardedCard(){
        double i = Math.floor(Math.random() * discardList.size());
        return discardList.get((int) i);
    }
    public synchronized void run(){
        while(!done)
        {
            Card discardedCard = getDiscardedCard();
            dropDeck.addCard(discardedCard);
            notifyAll();
            //dropDeck.writeToLog();
            hand.remove(discardedCard);

            if (discardList.size() != 0)
            {
                discardList.remove(discardedCard);
            }

            String discardMessage = "player "+(Integer.toString(name))+" discards a "+ (Integer.toString(discardedCard.getValue())) +" to deck " +dropDeck.getName();

            Card newCard = null;
            while (newCard == null)
            {
                try
                {
                    newCard = drawDeck.getCard(0);
                    drawDeck.removeCard(0);
                    hand.add(newCard);
                    if (newCard.getValue() != name){
                        discardList.add(newCard);
                    }
                } catch (IndexOutOfBoundsException a)
                {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }


            String drawMessage = "player "+(Integer.toString(name))+" draws a "+ (Integer.toString(newCard.getValue())) +" from deck " +drawDeck.getName();

            writeToLog(drawMessage, true);
            writeToLog(discardMessage, true);

            if (checkWin())
            {
                flags[name-1] = true;
                break;
            }

            String currentHand = "player "+(Integer.toString(name)+" hand: ");
            for (Card card : hand){
                currentHand += Integer.toString(card.getValue()) + " ";
            }

            writeToLog(currentHand, true);
        }
    }

    public void stopThread(int PlayerNameWon)
    {
        writeToLog("player "+(Integer.toString(PlayerNameWon))+" wins", true);
        writeToLog("player "+(Integer.toString(name))+" exits", true);

        String currentHand = "player "+(Integer.toString(name)+" final hand: ");
        for (Card card : hand){
            currentHand += Integer.toString(card.getValue()) + " ";
        }

        writeToLog(currentHand, true);
        this.done = true;
    }

}
