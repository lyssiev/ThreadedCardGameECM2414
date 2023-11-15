import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {
    static int numberOfPlayers = 0;
    public static void main(String[] args) {
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
            } catch (Exception e)
            {
                System.out.print("Please enter a positive integer.");
            }
        }

        Pack pack = new Pack(numberOfPlayers);
    }

    public ArrayList<Deck> decks()
    {
        ArrayList<Deck> decks = new ArrayList<Deck>();
        for(int i=1; i <= numberOfPlayers; i++)
        {
            String name = Integer.toString(i);
            Deck deck = new Deck(name);
            decks.add(deck);
        }
        return decks;
    }


    public void makePlayer(int playerNo)
    {
        Player player = new Player(playerNo,);
    }
}

