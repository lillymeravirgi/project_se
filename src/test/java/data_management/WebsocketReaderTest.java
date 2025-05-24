package data_management;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.WebSocketClientImpl;

/**
 * Tests for WebSocketClientImpl to verify handling of corrupted messages,
 * partial messages, and reconnect behavior.
 */
public class WebsocketReaderTest {

    private WebSocketClientImpl client;
    private DataStorage dataStorage;

    @BeforeEach
    public void setup() throws URISyntaxException {
        dataStorage = DataStorage.getInstance();
        client = new WebSocketClientImpl(new URI("ws://localhost:8080"), dataStorage);
    }

    @Test
    public void testCorruptedMessageIsHandled() {
        String corruptedMessage = "THIS_IS_CORRUPTED_DATA";
        assertDoesNotThrow(() -> client.onMessage(corruptedMessage));
    }

    @Test
    public void testPartialMessageIsHandled() {
        String partialMessage = "123,4567890123,HeartRate,";  
        assertDoesNotThrow(() -> client.onMessage(partialMessage));
    }

    @Test
    public void testValidMessageIsProcessed() {
        String validMessage = "1,1700000000000,HeartRate,75";

        assertDoesNotThrow(() -> client.onMessage(validMessage));
        var records = dataStorage.getRecords(1, 1699999999999L, 1700000000001L);
        assertFalse(records.isEmpty(), "Records should be present after valid message");
    }

    @Test
    public void testReconnectOnClose() {
        assertDoesNotThrow(() -> {
            client.onClose(1000, "Test close", true);
        });
    }

    
}

