import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class CardGameTest {

    //@Test
    //void setUpGame() {
    //    CardGame testGame = new CardGame();
    //    setInput("3" + "\n pack1.txt");
    //    testGame.setUpGame();
    //    assert(testGame.players != null && testGame.decks != null);

    //}

    void setInput(String input){
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(testInput);
    }

    @Test
    void makeDecks() {
        CardGame testGame = new CardGame();
        testGame.clearGame();
        boolean[] testFlags = {false, false, false};
        testGame.flags = testFlags;
        testGame.numberOfPlayers = 3;
        testGame.makeDecks();

        assert(testGame.decks.size() == testGame.numberOfPlayers);

    }

    @Test
    void makePlayers() {
        CardGame testGame = new CardGame();
        testGame.clearGame();
        boolean[] testFlags = {false, false, false};
        testGame.flags = testFlags;
        testGame.numberOfPlayers = 3;
        testGame.makeDecks();
        testGame.makePlayers();
        Player testPlayer = testGame.players.get(0);
        assert(testGame.players != null && testPlayer.getDropDeck() == testGame.decks.get(1) && testPlayer.getDrawDeck() == testGame.decks.get(0));
    }

    @Test
    void dealHands() throws FileNotFoundException{
        CardGame testGame = new CardGame();
        testGame.clearGame();
        boolean[] testFlags = {false, false, false};
        testGame.flags = testFlags;
        testGame.numberOfPlayers = 3;
        testGame.makeDecks();
        testGame.makePlayers();

        ArrayList<Card> testPack = new ArrayList<Card>();
        File packFile = new File("pack1.txt");
        Scanner scanner = new Scanner(packFile);
        while (scanner.hasNextLine()) {
            int value = Integer.parseInt(scanner.nextLine());
            Card card = new Card(value);
            testPack.add(card);
        }
        scanner.close();
        testGame.pack = testPack;

        testGame.dealHands();

        assert(testGame.players.get(0).getHand().size() == 4);

    }

    @Test
    void dealDecks() throws FileNotFoundException {
        CardGame testGame = new CardGame();
        testGame.clearGame();
        boolean[] testFlags = {false, false, false};
        testGame.flags = testFlags;
        testGame.numberOfPlayers = 3;
        testGame.makeDecks();
        testGame.makePlayers();

        ArrayList<Card> testPack = new ArrayList<Card>();
        File packFile = new File("pack1.txt");
        Scanner scanner = new Scanner(packFile);
        while (scanner.hasNextLine()) {
            int value = Integer.parseInt(scanner.nextLine());
            Card card = new Card(value);
            testPack.add(card);
        }
        scanner.close();
        testGame.pack = testPack;

        testGame.dealHands();
        testGame.dealDecks();

        assert(testGame.decks.get(0).getDeck().size() == 4);
    }


}