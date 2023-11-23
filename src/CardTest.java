import org.junit.jupiter.api.Test;
import java.lang.reflect.*;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void getValue() {
        Card testCard = new Card(3);
        assert(testCard.getValue() == 3);
    }
}