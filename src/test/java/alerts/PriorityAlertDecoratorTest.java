package alerts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.alerts.Alert;
import com.alerts.PriorityAlertDecorator;
import com.alerts.SimpleAlert;

public class PriorityAlertDecoratorTest {
    @Test
    public void checkPriority(){
       
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
