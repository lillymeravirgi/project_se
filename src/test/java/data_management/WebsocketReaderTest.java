package data_management;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.cardio_generator.outputs.WebSocketOutputStrategy;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.WebSocketClientImpl;
import com.data_management.WebsocketReader;

public class WebsocketReaderTest {
    static DataStorage storage;
    static WebsocketReader reader;
    static WebSocketOutputStrategy output;

    @BeforeAll
    static void setUp() throws URISyntaxException{

        //server is started on the 8887 port
         output = new WebSocketOutputStrategy(8887);
        storage = DataStorage.getInstance();

        //then the client is connected to the same port
        URI serverUri = new URI("ws://localhost:8887");
        WebSocketClientImpl client = new WebSocketClientImpl(serverUri, storage);
        reader = new WebsocketReader(client);

        try {
            Thread.sleep(1000);  // wait 1 second
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
    }
    
    @Test
    void addAndReadData(){

        reader.readData();



        output.output(431813, 1236419, "ECG", "98.7");

        try {
            Thread.sleep(1000);  // wait 1 second
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        Patient patient = storage.getAllPatients().getFirst();
        assertEquals(431813,patient.getPatientId());

    }
    
}
