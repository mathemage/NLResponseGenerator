import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NextResponseTest {

    private Map<Integer, ConversationalMessage> history;

    @BeforeEach
    void setUp() {
        history = new HashMap<>();
        history.put(1, new ConversationalMessage("message 1", true));
        history.put(2, new ConversationalMessage("message 2", true));
    }

    @Test
    void getNextMessage() {
        String nextMessage = NextResponse.getNextMessage(history, 1);
        assertEquals(nextMessage, "message 2");
    }

    @Test
    void getNextMessage_empty() {
        String nextMessage = NextResponse.getNextMessage(history, 2);
        assertEquals(nextMessage, "");
    }

}
