import org.junit.jupiter.api.Test;
import java.lang.reflect.*;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getDrawDeck() {
        Deck dropDeck = new Deck("2");
        Deck drawDeck = new Deck("1");
        boolean[] flags = {true};
        Player testPlayer = new Player(1, drawDeck, dropDeck, flags);

        assert(testPlayer.getDrawDeck() == drawDeck);
    }

    @Test
    void getDropDeck() {
        Deck dropDeck = new Deck("2");
        Deck drawDeck = new Deck("1");
        boolean[] flags = {true};
        Player testPlayer = new Player(1, drawDeck, dropDeck, flags);

        assert(testPlayer.getDropDeck() == dropDeck);
    }

    @Test
    void addCardToHand() {
        Deck dropDeck = new Deck("2");
        Deck drawDeck = new Deck("1");
        boolean[] flags = {true};
        Player testPlayer = new Player(1, drawDeck, dropDeck, flags);

        Card testCard = new Card(1);
        testPlayer.addCardToHand(testCard);

        assert(testPlayer)
    }

    @Test
    void writeToLog() {
    }

    @Test
    void checkWin() {
    }

    @Test
    void getDiscardedCard() {
    }

    @Test
    void run() {
    }

    @Test
    void stopThread() {
    }
}