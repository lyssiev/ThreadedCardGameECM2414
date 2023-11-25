import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void run() throws FileNotFoundException, InterruptedException {
        //set up pack
        ArrayList<Card> testPack = new ArrayList<Card>();
        File packFile = new File("pack1.txt");
        Scanner scanner = new Scanner(packFile);
        while (scanner.hasNextLine()) {
            int value = Integer.parseInt(scanner.nextLine());
            Card card = new Card(value);
            testPack.add(card);
        }
        scanner.close();

        Pack packObject = new Pack(2, testPack);
        testPack = packObject.getCards(); //making sure cards are shuffled

        //set up players
        boolean[] flags = {false, false, false};

        Deck Deck1 = new Deck("1");
        Deck Deck2 = new Deck("2");
        Deck Deck3 = new Deck("2");

        Player testPlayer1 = new Player(1, Deck1, Deck2, flags);
        Player testPlayer2 = new Player(2, Deck2, Deck3, flags);
        Player testPlayer3= new Player(2, Deck3, Deck1, flags);

        // adding cards from pack to players and decks
        for (int i = 0; i < testPack.size(); i += 6) {
            Card card1 = testPack.get(i);
            Card card2 = testPack.get(i + 1);
            Card card3 = testPack.get(i + 2);
            Card card4 = testPack.get(i + 3);
            Card card5 = testPack.get(i + 4);
            Card card6 = testPack.get(i + 5);

            testPlayer1.addCardToHand(card1);
            testPlayer2.addCardToHand(card2);
            testPlayer3.addCardToHand(card3);
            Deck1.addCard(card4);
            Deck2.addCard(card5);
            Deck3.addCard(card6);
        }

        //start player threads
        testPlayer1.start();
        testPlayer2.start();
        testPlayer3.start();

        //check if one of the threads has won
        boolean done = false;
        while(!done)
        {
            Thread.sleep(100);
            int counter = 0;
            for (boolean flag : flags)
            {
                if (flag)
                {
                    done = true;
                    testPlayer1.stopThread(counter);
                    testPlayer2.stopThread(counter);
                    testPlayer3.stopThread(counter);
                }
                counter++;
            }

        }

        assertTrue(flags[0] || flags[1] || flags[2]); // make sure flag array is changed if won
    }


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
        //set up player
        Deck dropDeck = new Deck("2");
        Deck drawDeck = new Deck("1");
        boolean[] flags = {true};
        Player testPlayer = new Player(1, drawDeck, dropDeck, flags);

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