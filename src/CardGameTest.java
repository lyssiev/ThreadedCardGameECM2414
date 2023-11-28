import org.junit.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class CardGameTest {

    @Test
    public void setUpGame() {
        CardGame testGame = new CardGame();
        testGame.clearGame();
        setInput("3\n", "pack1.txt");
        testGame.setUpGame();
        assert(testGame.players.get(0).getHand().size() == 4 && testGame.decks.get(0).getDeck().size() == 4 && testGame.players.size() == 3);

    }

    //Creates an arbitrary input stream to test our methods
    public void setInput(String input1, String input2){
        ByteArrayInputStream testInput1 = new ByteArrayInputStream(input1.getBytes());
        ByteArrayInputStream testInput2 = new ByteArrayInputStream(input2.getBytes());
        SequenceInputStream testInput = new SequenceInputStream(testInput1, testInput2);
        System.setIn(testInput);
    }

    @Test
    public void makeDecks() {
        CardGame testGame = new CardGame();
        testGame.clearGame();
        boolean[] testFlags = {false, false, false};
        testGame.flags = testFlags;
        testGame.numberOfPlayers = 3;
        testGame.makeDecks();

        assert(testGame.decks.size() == testGame.numberOfPlayers);

    }

    @Test
    public void makePlayers() {
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
    public void dealHands() throws FileNotFoundException{
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
    public void dealDecks() throws FileNotFoundException {
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