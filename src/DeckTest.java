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
        ArrayList<Card> testCards = new ArrayList<Card>()
        for (int i = 1; i < 5; i++){
            Card testCard = new Card(i);
            testCards.add(testCard);
            testDeck.addCard(testCard);

        }
        assert(testDeck.getDeck() == testCards);
    }

    @Test
    void addCard() {

    }

    @Test
    void getNumberOfCards() {
    }

    @Test
    void removeCard() {
    }

    @Test
    void getCard() {
    }

    @Test
    void writeToLog() {
    }
}