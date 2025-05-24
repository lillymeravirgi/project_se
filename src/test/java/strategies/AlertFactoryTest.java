package strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.alerts.Alert;
import com.alerts.factories.AlertFactory;
import com.alerts.factories.BloodOxygenAlertFactory;
import com.alerts.factories.BloodPressureAlertFactory;
import com.alerts.factories.ECGAlertFactory;

public class AlertFactoryTest {

    @Test
    public void testBloodPressureAlertFactoryCreatesAlert() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("123", "High BP", 1623456789L);

        assertNotNull(alert);
        assertEquals("123", alert.getPatientId());
        assertEquals("High BP", alert.getCondition());
        assertEquals(1623456789L, alert.getTimestamp());
    }

    @Test
    public void testBloodOxygenAlertFactoryCreatesAlert() {
        AlertFactory factory = new BloodOxygenAlertFactory();
        Alert alert = factory.createAlert("456", "Low SpO2", 1623456790L);

        assertNotNull(alert);
        assertEquals("456", alert.getPatientId());
        assertEquals("Low SpO2", alert.getCondition());
        assertEquals(1623456790L, alert.getTimestamp());
    }

    @Test
    public void testECGAlertFactoryCreatesAlert() {
        AlertFactory factory = new ECGAlertFactory();
        Alert alert = factory.createAlert("789", "ECG Irregularity", 1623456791L);

        assertNotNull(alert);
        assertEquals("789", alert.getPatientId());
        assertEquals("ECG Irregularity", alert.getCondition());
        assertEquals(1623456791L, alert.getTimestamp());
    }
}


