package alerts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.alerts.Alert;
import com.alerts.RepeatedAlertDecorator;
import com.alerts.SimpleAlert;

public class RepeatedAlertDecoratorTest {

    /*
     * methods tests if the decorator changes the checkAllDetails method of the simple alert successfully
     */
    @Test
    public void checkAllDetailsTest(){
       
        Alert alert = new SimpleAlert("12345", "Low SpO2 Alert", 1748714791);
        Alert wrappedAlert = new RepeatedAlertDecorator(alert,4, 1351628);

        String expectedOutput = alert.getAllDetails() +
        " | Repeated 4 times every 1351628 Seconds"; 
    
        assertEquals(expectedOutput, wrappedAlert.getAllDetails());
    }
}
