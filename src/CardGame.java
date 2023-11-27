import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CardGame {
    //defining attributes:
    public static int numberOfPlayers = 0;
    public static ArrayList<Deck> decks = new ArrayList<Deck>();
    public static ArrayList<Player> players = new ArrayList<Player>();;
    public static ArrayList<Card> pack = new ArrayList<Card>();
    public static boolean[] flags; //a boolean list, one for each player, to track whether a player has won yet


    public static void main(String[] args) {
        setUpGame();

        //starts threads
        for(Player player : players)
        {
            player.start();
        }

        //checks to see if a player has won
        int gameFlag = gameOver(flags);
        try {
            while(gameFlag == -1){
                Thread.sleep(100);
                gameFlag = gameOver(flags);
            }
        } catch (Exception e){}

        for(Player player : players)
        {
            player.stopThread(gameFlag);
        }

        for(Deck deck : decks)
        {
            deck.writeToLog();
        }

        System.out.println("Player " + Integer.toString(gameFlag) + " wins.");

    }

    //resets all the game values
    public static void clearGame(){
        decks.clear();
        players.clear();
        pack.clear();
        numberOfPlayers = 0;
        flags = new boolean[]{};
    }

    //creates all the necessary game objects
    public static void setUpGame()
    {
        Boolean valid = false;

        //loops ensure the game only runs with a valid number of players and valid pack file
        while (!valid)
        {
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Please enter the number of players: ");
            String userInput = scanner1.nextLine();
            try
            {
                numberOfPlayers = Integer.parseInt(userInput);
                if (numberOfPlayers <= 0 )
                {
                    throw new Exception();
                }
                else
                {
                    valid = true;
                }
            } catch (Exception e)
            {
                System.out.print("Please enter a positive integer.");
            }
        }

        valid = false;
        ArrayList<Card> cardList = new ArrayList<Card>();

        while (!valid) {
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Please enter location of pack to load: ");
            String fileName = scanner1.nextLine();

            try {
                File packFile = new File(fileName);
                Scanner scanner = new Scanner(packFile);
                try {
                    while (scanner.hasNextLine()) {
                        int value = Integer.parseInt(scanner.nextLine());
                        Card card = new Card(value);
                        cardList.add(card);

                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid file.");
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            }

            if (cardList.size() != 8 * numberOfPlayers) {
                System.out.println("Invalid file.");
                cardList.clear();
            } else {
                valid = true;
            }
        }


        flags = new boolean[numberOfPlayers];

        Pack packObject = new Pack(numberOfPlayers, cardList);
        pack = packObject.getCards();

        makeDecks();
        makePlayers();
        dealHands();
        dealDecks();
    }

    //checks to see if a flag has been set to true - if so, that player has won
    private static int gameOver(boolean[] flags) {
        int counter = 0;
        for (boolean flag : flags) {
            counter++;
            if (flag) {
                return counter;
            }
        }
        return -1;
    }

    //creates the Deck objects
    public static void makeDecks()
    {
        for(int i=1; i <= numberOfPlayers; i++)
        {
            String name = Integer.toString(i);
            Deck deck = new Deck(name);
            decks.add(deck);
        }
    }


    //Creates the Player objects and assigns the Decks they draw from and drop to
    public static void makePlayers()
    {
        for(int i=1; i <= numberOfPlayers; i++)
        {
            Deck drawDeck = decks.get(i-1);
            Deck dropDeck;
            if (i == numberOfPlayers) //The last player in the circle drops cards to the first deck
            {
                dropDeck = decks.get(0);
            }
            else
            {
                dropDeck = decks.get(i);
            }
            Player player = new Player(i,drawDeck, dropDeck, flags);
            players.add(player);
        }
    }

    //Deals cards to the players from the pack
    public static void dealHands()
    {

        int counter = 0;
        for (int i=0; i < (numberOfPlayers * 4) ; i++ ) //each Player has 4 cards in hand, so 4 * number of players cards are dealt
        {
            players.get(counter).addCardToHand(pack.get(1));
            pack.remove(pack.get(1));
            counter++;
            if (counter == numberOfPlayers)
            {
                counter = 0;
            }
        }
    }

    //Deals remaining cards to the Decks from the pack
    public static void dealDecks()
    {
        int counter = 0;
        for (int i=0; i < (numberOfPlayers * 4) ; i++ ) //each Deck starts with 4 cards, so 4 * number of players cards are dealt
        {
            decks.get(counter).addCard(pack.get(i));
            counter++;
            if (counter == numberOfPlayers)
            {
                counter = 0;
            }
        }
    }
}