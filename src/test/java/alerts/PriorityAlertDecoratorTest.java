package alerts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.alerts.Alert;
import com.alerts.PriorityAlertDecorator;
import com.alerts.SimpleAlert;

/*
 * class tests if a decorator can be applied 
 * to a simple alert by testing changing methods
 */
public class PriorityAlertDecoratorTest {
    @Test
    public void checkPriorityTest(){
       
        Alert alert = new SimpleAlert("12345", "Low SpO2 Alert", 1748714791);
        PriorityAlertDecorator wrappedAlert = new PriorityAlertDecorator(alert, "HIGH");

    
        assertEquals("HIGH", wrappedAlert.getPriority());
    }

    public void checkAllDetails(){
       
        Alert alert = new SimpleAlert("12345", "Low SpO2 Alert", 1748714791);
        PriorityAlertDecorator wrappedAlert = new PriorityAlertDecorator(alert, "HIGH");

        String expectedOutput =  "[Priority: HIGH ] " + alert.getAllDetails();
    
        assertEquals(expectedOutput, wrappedAlert.getAllDetails());
    }
}
