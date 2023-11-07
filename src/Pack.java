import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Pack {
    private ArrayList<Card> cards;

    public Pack(String fileName)
    {
        try {
            File packFile = new File(fileName +".txt");
            Scanner scanner = new Scanner(packFile);
            while (scanner.hasNextLine()) {
                int value = Integer.parseInt(scanner.nextLine());
                Card card = new Card(value);
                this.cards.add(card);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

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
