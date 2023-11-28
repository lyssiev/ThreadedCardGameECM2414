import org.junit.Test;
import java.util.ArrayList;



public class DeckTest {

    @Test
    public void getName() {
        Deck testDeck = new Deck("1");
        assert(testDeck.getName() == "1");
    }

    @Test
    public void getDeck() {
        Deck testDeck = new Deck("1");
        ArrayList<Card> testCards = new ArrayList<Card>();
        for (int i = 1; i < 5; i++){
            Card testCard = new Card(i);
            testCards.add(testCard);
            testDeck.addCard(testCard);

        }
        assert(testDeck.getDeck().equals(testCards));
    }

    @Test
    public void addCard() {
        Deck testDeck = new Deck("1");
        Card testCard = new Card(1);
        testDeck.addCard(testCard);
        assert(testDeck.getCard(0) == testCard);
    }

    @Test
    public void removeCard() {
        Deck testDeck = new Deck("1");
        Card testCard = new Card(1);
        testDeck.addCard(testCard);
        testDeck.removeCard(0);
        assert(testDeck.getDeck().size() == 0);
    }

    @Test
    public void getCard() {
        Deck testDeck = new Deck("1");
        Card testCard = new Card(1);
        testDeck.addCard(testCard);
        assert(testDeck.getCard(0) == testCard);
    }


}
