import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Pack {
    private ArrayList<Card> cards = new ArrayList<Card>();

    //Constructor
    public Pack(int n, ArrayList<Card> cards) {
        this.cards = cards;
        this.shuffle();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }


    //shuffles the pack
    private void shuffle()
    {
        for (int i = cards.size() - 1; i > 0; i--)
        {
            double j = Math.floor(Math.random() * (i+1));
            Card temp = cards.get(i);
            cards.set(i, cards.get((int)j));
            cards.set((int)j, temp);
        }
    }
}
