import org.junit.jupiter.api.Test;
import java.lang.reflect.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void getName() {
        Deck testDeck = new Deck("1");
        assert(testDeck.getName() == "1");
    }

    @Test
    void getDeck() {
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
    void addCard() {
        Deck testDeck = new Deck("1");
        Card testCard = new Card(1);
        testDeck.addCard(testCard);
        assert(testDeck.getCard(0) == testCard);
    }

    @Test
    void removeCard() {
        Deck testDeck = new Deck("1");
        Card testCard = new Card(1);
        testDeck.addCard(testCard);
        testDeck.removeCard(0);
        assert(testDeck.getDeck().size() == 0);
    }

    @Test
    void getCard() {
        Deck testDeck = new Deck("1");
        Card testCard = new Card(1);
        testDeck.addCard(testCard);
        assert(testDeck.getCard(0) == testCard);
    }

    //@Test
    //void writeToLog() {
    //}
}