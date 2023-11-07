
import java.util.ArrayList;
public class Hand {
    private ArrayList<Card> cards;
    public Hand()
    {
        this.cards = new ArrayList<Card>();
    }

    public ArrayList<Card> getHand()
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

    public void removeCard(Card card)
    {
        this.cards.remove(card);
    }

}

