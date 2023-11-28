import org.junit.Test;


public class CardTest {

    @Test
    public void getValue() {
        Card testCard = new Card(3);
        assert(testCard.getValue() == 3);
    }
}