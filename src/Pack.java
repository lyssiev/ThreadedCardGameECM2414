import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Pack {
    private ArrayList<Card> cards;

    public Pack(int n) {
        Boolean valid = false;

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
                        this.cards.add(card);

                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid file.");
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            }

            if (cards.size() != 8 * n) {
                System.out.println("Invalid file.");
                this.cards.clear();
            } else {
                valid = true;
            }
        }
        this.shuffle();
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
