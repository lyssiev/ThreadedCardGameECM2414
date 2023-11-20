import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {
    static int numberOfPlayers = 0;
    public static ArrayList<Deck> decks = new ArrayList<Deck>();
    public static ArrayList<Player> players = new ArrayList<Player>();;
    public static ArrayList<Card> pack = new ArrayList<Card>();;
    public static boolean[] flags;
    public static void main(String[] args) {
        setUpGame();

        for(Player player : players)
        {
            player.start();
        }

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

    public static void setUpGame()
    {
        Boolean valid = false;

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

        flags = new boolean[numberOfPlayers];

        Pack packObject = new Pack(numberOfPlayers);
        pack = packObject.getCards();

        makeDecks();
        makePlayers();
        dealHands();
        dealDecks();
    }
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
    public static void makeDecks()
    {
        for(int i=1; i <= numberOfPlayers; i++)
        {
            String name = Integer.toString(i);
            Deck deck = new Deck(name);
            decks.add(deck);
        }
    }


    public static void makePlayers()
    {
        for(int i=1; i <= numberOfPlayers; i++)
        {
            Deck drawDeck = decks.get(i-1);
            Deck dropDeck;
            if (i == numberOfPlayers)
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

    public static void dealHands()
    {
        int counter = 0;
        for (int i=0; i < (numberOfPlayers * 4) ; i++ )
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

    public static void dealDecks()
    {
        int counter = 0;
        for (int i=0; i < (numberOfPlayers * 4) ; i++ )
        {
            decks.get(counter).addCard(pack.get(i));
            counter++;
            if (counter == numberOfPlayers)
            {
                counter = 0;
            }
        }


        for (Deck deck : decks)
        {
            deck.writeToLog();
        }
    }
}

