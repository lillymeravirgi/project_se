package data_management;

import com.cardio_generator.outputs.TcpOutputStrategy;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

/*
 * the test starts the outputstrategy on a free port,connects a client to that port,
 * then sends data from the outputstrategy and reads it via the client
 */
class TcpOutputStrategyTest {

    @Test
    void testTcpOutputStrategySendsData() throws Exception {
        int port = 12345; 
        TcpOutputStrategy testStrategy = new TcpOutputStrategy(port);

        Thread.sleep(500);

  
        try (Socket client = new Socket("localhost", port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

            
            Thread.sleep(500);
           
            testStrategy.output(47, 1746730876, "ECG", "-0.290513876898046");

            String received = reader.readLine();
            assertEquals("47,1746730876,ECG,-0.290513876898046", received);
        }
    }
}
