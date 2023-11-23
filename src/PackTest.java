import org.junit.jupiter.api.Test;
import java.lang.reflect.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PackTest {

    @Test
    void getCards() {
        ArrayList<Card> testCards = new ArrayList<Card>();
        for (int i=1; i<=24; i++ ){
            Card testCard = new Card(i);
            testCards.add(testCard);
        }
        Pack testPack = new Pack(3,testCards);
        assert (testPack.getCards() == testCards);

    }
    //@Test
    //void setupPack() {
    //}


}