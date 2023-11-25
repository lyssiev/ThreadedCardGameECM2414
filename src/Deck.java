import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Deck{

    private ArrayList<Card> cards;
    private String name;
    private String deckLogFilename;
    public Deck(String name)
    {
        this.cards = new ArrayList<Card>();
        this.name = name;
        deckLogFilename = "deck"+ (name) + "_output.txt";
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getDeck()
    {
        return cards;
    }

    public synchronized void addCard(Card card)
    {
        this.cards.add(card);
    }

    public synchronized void removeCard(int index)
    {
        cards.remove(index);
    }

    public synchronized Card getCard(int index){
        return cards.get(index);
    }

    public void writeToLog(){
        try {
            FileWriter writer = new FileWriter(deckLogFilename, false);
            String message = "Deck "+ name + " contents: ";
            for (Card deckCard : cards){
                message += Integer.toString(deckCard.getValue()) + " ";
            }
            writer.write(message + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error.");
        }
    }
}
