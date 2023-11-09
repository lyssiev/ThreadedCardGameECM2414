import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private String name;
    public Deck(String name)
    {
        this.cards = new ArrayList<Card>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getDeck()
    {
        return cards;
    }

    public void addCard(Card card)
    {
        this.cards.add(card);
    }

    public int getNumberOfCards()
    {
        return cards.size();
    }

    public void removeCard(int index)
    {
        cards.remove(index);
    }

    public Card getCard(int index){
        return cards.get(index);
    }
}
