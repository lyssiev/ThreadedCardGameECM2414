import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class Player {

    private int name;
    private ArrayList<Card> hand;
    private Deck drawDeck;
    private Deck dropDeck;
    private String actionLogFilename;
    private ArrayList<Card> discardList;


    public Player(int name, ArrayList<Card> hand, Deck drawDeck, Deck dropDeck){
        this.name = name;
        this.hand = hand;
        this.drawDeck = drawDeck;
        this.dropDeck = dropDeck;

        for (Card card : this.hand){
            if (card.getValue() != this.name){
                discardList.add(card);
            }
        }
        actionLogFilename = "player"+ (Integer.toString(name)) + "_output.txt";
    }



    public Deck getDrawDeck(){
        return drawDeck;
    }

    public Deck getDropDeck(){
        return dropDeck;
    }

    public void writeToLog(String message){
        try {
            FileWriter writer = new FileWriter(actionLogFilename);
            writer.write(message);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error.");
        }
    }

    public Boolean checkWin(){
        int value = hand.get(0).getValue();
        for (Card card : hand){
            if (value != card.getValue()){
                return false;
            }
        }
        return true;
    }

    public Card getDiscardedCard(){
        double i = Math.floor(Math.random() * discardList.size());
        return discardList.get((int) i);
    }
    public void takeTurn(){

        Card discardedCard = getDiscardedCard();
        dropDeck.addCard(discardedCard);
        hand.remove(discardedCard);
        discardList.remove(discardedCard);
        String discardMessage = "player "+(Integer.toString(name))+" discards a "+ (Integer.toString(discardedCard.getValue())) +" to deck " +dropDeck.getName();

        Card newCard = drawDeck.getCard(0);
        drawDeck.removeCard(0);
        hand.add(newCard);
        if (newCard.getValue() != name){
            discardList.add(newCard);
        }
        String drawMessage = "player "+(Integer.toString(name))+" draws a "+ (Integer.toString(newCard.getValue())) +" from deck " +drawDeck.getName();

        writeToLog(drawMessage);
        writeToLog(discardMessage);

        if (checkWin()){
            writeToLog("player "+(Integer.toString(name))+" wins");
            writeToLog("player "+(Integer.toString(name))+" exits");
            //NOTIFY OTHER PLAYERS + CHECK OTHER PLAYER WINS

        }

        String currentHand = "player "+(Integer.toString(name)+" hand: ");
        for (Card card : hand){
            currentHand += Integer.toString(card.getValue()) + " ";
        }

        writeToLog(currentHand);

    }
}
