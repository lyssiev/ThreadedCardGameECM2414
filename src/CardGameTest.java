import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;

class CardGameTest {

    //@Test
    //void main() {
    //}

    //@Test
    //void setUpGame() {
    //StringWriter output = new StringWriter();
    //String input = "3 \n" + "pack1.txt \n";
    //}

    @Test
    void makeDecks() {
        CardGame testGame = new CardGame();
        testGame.makeDecks();
        assert(testGame.decks != null);

    }

    @Test
    void makePlayers() {
        CardGame testGame = new CardGame();
        boolean[] testFlags = {false, false, false};
        testGame.flags = testFlags;
        testGame.numberOfPlayers = 3;
        testGame.makeDecks();
        testGame.makePlayers();
        Player testPlayer = testGame.players.get(0);
        assert(testGame.players != null && testPlayer.getDropDeck() == testGame.decks.get(1) && testPlayer.getDrawDeck() == testGame.decks.get(0));
    }

    @Test
    void dealHands() {
        CardGame testGame = new CardGame();
        boolean[] testFlags = {false, false, false};
        testGame.flags = testFlags;
        testGame.numberOfPlayers = 3;
        testGame.makeDecks();
        testGame.makePlayers();

        testGame.dealHands();

        assert(testGame.players.get(0).getHand().size() == 4);

    }

    @Test
    void dealDecks() {
        CardGame testGame = new CardGame();
        boolean[] testFlags = {false, false, false};
        testGame.flags = testFlags;
        testGame.numberOfPlayers = 3;
        testGame.makeDecks();
        testGame.makePlayers();
        testGame.dealHands();
        testGame.dealDecks();

        assert(testGame.decks.get(0).getDeck().size() == 4);
    }


}