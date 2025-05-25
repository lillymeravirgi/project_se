package strategies;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.alerts.AlertGenerator;
import com.alerts.strategies.OxygenSaturationStrategy;
import com.data_management.Patient;
import com.data_management.PatientRecord;

class OxygenSaturationStrategyTest {

    @Test
    void testLowSpO2TriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();

        // Add low SpO2 data 
        patient.addRecord(91.0, "SpO2", now);

        // Use a test subclass of AlertGenerator to capture alerts
        TestAlertGenerator generator = new TestAlertGenerator();

        // Run the strategy
        new OxygenSaturationStrategy().checkAlerts(patient, generator);

        // Verify an alert was triggered
        assertFalse(generator.alerts.isEmpty(), "Expected at least one alert");
        assertTrue(generator.alerts.get(0).getCondition().contains("Low SpO2"),
                "Expected 'Low SpO2' alert condition");
    }

    // Subclass to capture alerts
    static class TestAlertGenerator extends AlertGenerator {
        public List<com.alerts.SimpleAlert> alerts = new ArrayList<>();

        public TestAlertGenerator() {
            super(null); // Passing null since we won't use dataStorage
        }

        @Override
        public void triggerAlert(com.alerts.SimpleAlert alert) {
            alerts.add(alert);
        }

        @Override
        public void checkSpO2Alerts(int patientId, List<PatientRecord> spo2Records) {
            super.checkSpO2Alerts(patientId, spo2Records);
        }
    }
}
