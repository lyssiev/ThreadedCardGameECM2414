import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getDrawDeck() {
        //set up player
        Deck dropDeck = new Deck("2");
        Deck drawDeck = new Deck("1");
        boolean[] flags = {true};
        Player testPlayer = new Player(1, drawDeck, dropDeck, flags);

        //make sure correct object
        assert(testPlayer.getDrawDeck() == drawDeck);
    }

    @Test
    void getDropDeck() {
        //set up player
        Deck dropDeck = new Deck("2");
        Deck drawDeck = new Deck("1");
        boolean[] flags = {true};
        Player testPlayer = new Player(1, drawDeck, dropDeck, flags);

        //make sure correct object
        assert(testPlayer.getDropDeck() == dropDeck);
    }

    @Test
    void addCardToHand() {
        //set up player
        Deck dropDeck = new Deck("2");
        Deck drawDeck = new Deck("1");
        boolean[] flags = {true};
        Player testPlayer = new Player(1, drawDeck, dropDeck, flags);

        //add card
        Card testCard = new Card(1);
        testPlayer.addCardToHand(testCard);

        //check card is in hand
        assert((testPlayer.getHand()).get(0) == testCard);
    }

    @Test
    void writeToLog() throws FileNotFoundException {

        // set up player
        Deck dropDeck = new Deck("2");
        Deck drawDeck = new Deck("1");
        boolean[] flags = {true};
        Player testPlayer = new Player(1, drawDeck, dropDeck, flags);

        // adding to file
        testPlayer.writeToLog("test", true);

        // checking the write-to-log function
        String fileName = testPlayer.getActionLogFilename();
        File packFile = new File(fileName);
        Scanner scanner = new Scanner(packFile);
        boolean found = false;
        while (scanner.hasNextLine()) {
            if (scanner.nextLine().equals("test")) {
                found = true;
                break; // Exit the loop once you find the string
            }
        }
        scanner.close();
        assertTrue(found);
    }

    @Test
    void checkWin() {
        //set up player for false
        Deck dropDeck = new Deck("2");
        Deck drawDeck = new Deck("1");
        boolean[] flags = {true};
        Player testPlayer = new Player(1, drawDeck, dropDeck, flags);

        //add cards
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        Card card3 = new Card(3);
        Card card4 = new Card(4);

        testPlayer.addCardToHand(card1);
        testPlayer.addCardToHand(card2);
        testPlayer.addCardToHand(card3);
        testPlayer.addCardToHand(card4);

        //make sure player does not win
        assert(!testPlayer.checkWin());

        //set up player for true
        Deck dropDeck2 = new Deck("3");
        Deck drawDeck2 = new Deck("2");
        boolean[] flags2 = {true};
        Player testPlayer2 = new Player(2, drawDeck2, dropDeck2, flags2);

        //add cards
        Card testCard1 = new Card(2);
        Card testCard2 = new Card(2);
        Card testCard3 = new Card(2);
        Card testCard4 = new Card(2);

        testPlayer2.addCardToHand(testCard1);
        testPlayer2.addCardToHand(testCard2);
        testPlayer2.addCardToHand(testCard3);
        testPlayer2.addCardToHand(testCard4);

        //make sure player does win
        assert(testPlayer2.checkWin());
    }

    @Test
    void getDiscardedCard() {


        //setting up player
        Deck dropDeck = new Deck("2");
        Deck drawDeck = new Deck("1");
        boolean[] flags = {true};
        Player testPlayer = new Player(1, drawDeck, dropDeck, flags);

        //populate hand and discard list
        ArrayList<Card> discardList = testPlayer.getDiscardList();
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        Card card3 = new Card(3);
        Card card4 = new Card(4);

        ArrayList<Card> testHand = testPlayer.getHand();

        testPlayer.addCardToHand(card1);
        testPlayer.addCardToHand(card2);
        testPlayer.addCardToHand(card3);
        testPlayer.addCardToHand(card4);

        //check that there are no cards with the same value as the player name to be discarded
        for (Card card : discardList)
        {
            assert(card.getValue() != testPlayer.getPlayerName());
        }


        // check random card is in the discard list and hand
        Card testCard = testPlayer.getDiscardedCard();
        assert(testPlayer.getHand().contains(testCard));
        assert(discardList.contains(testCard));

    }

    @Test
    void run() {

    }

    @Test
    void stopThread() throws FileNotFoundException {
        // setting up test player and cards
        Deck dropDeck = new Deck("2");
        Deck drawDeck = new Deck("1");
        boolean[] flags = {false};
        Player testPlayer = new Player(1, drawDeck, dropDeck, flags);

        Card card1 = new Card(1);
        Card card2 = new Card(2);
        Card card3 = new Card(3);
        Card card4 = new Card(4);

        testPlayer.addCardToHand(card1);
        testPlayer.addCardToHand(card2);
        testPlayer.addCardToHand(card3);
        testPlayer.addCardToHand(card4);

        // running and stopping thread
        testPlayer.start();
        testPlayer.stopThread(1);

        String fileName = testPlayer.getActionLogFilename();
        File packFile = new File(fileName);
        Scanner scanner = new Scanner(packFile);

        boolean foundWin = false;
        boolean foundExit = false;
        boolean foundFinalHand = false;

        String finalHand = "player 1 final hand: ";
        for (Card card : testPlayer.getHand())
        {
            finalHand += Integer.toString(card.getValue()) + " ";
        }

        // check that all necessary outputs have been written
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("player 1 wins")) {
                foundWin = true;
            }
            if (line.equals("player 1 exits")) {
                foundExit = true;
            }
           if (line.equals(finalHand)) {
                foundFinalHand = true;
           }
        }
        scanner.close();
        assertTrue(foundWin && foundExit && foundFinalHand);
    }
}