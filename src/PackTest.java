import org.junit.Test;

import java.util.ArrayList;

public class PackTest {


    @Test
    public void getCards() {
        ArrayList<Card> testCards = new ArrayList<Card>();
        for (int i=1; i<=24; i++ ){
            Card testCard = new Card(i);
            testCards.add(testCard);
        }
        Pack testPack = new Pack(3,testCards);
        assert (testPack.getCards() == testCards);

    }
    @Test
    public void setupPack() {
        ArrayList<Card> testCards = new ArrayList<Card>();
        for (int i=1; i<=24; i++ ){
            Card testCard = new Card(i);
            testCards.add(testCard);
        }
        Pack testPack = new Pack(3,testCards);
        assert (testPack.getCards() == testCards);

    }



}