package strategies;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.alerts.AlertGenerator;
import com.alerts.strategies.BloodPressureStrategy;
import com.data_management.Patient;
import com.data_management.PatientRecord;

class BloodPressureStrategyTest {

    @Test
    void testCriticalBloodPressureTriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();

        // Add critical blood pressure readings
        patient.addRecord(185.0, "BloodPressureSystolic", now);
        patient.addRecord(60.0, "BloodPressureDiastolic", now);

        TestAlertGenerator generator = new TestAlertGenerator();

        new BloodPressureStrategy().checkAlerts(patient, generator);

        assertFalse(generator.alerts.isEmpty(), "Expected alert for critical BP");
        assertTrue(generator.alerts.get(0).getCondition().contains("Critical Blood Pressure"),
                "Expected 'Critical Blood Pressure' alert");
    }

    @Test
    void testBloodPressureTrendTriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();

        // Add BP records showing rising trend (>10 units increase twice consecutively)
        patient.addRecord(110.0, "BloodPressureSystolic", now - 3000);
        patient.addRecord(125.0, "BloodPressureSystolic", now - 2000);
        patient.addRecord(140.0, "BloodPressureSystolic", now - 1000);

        TestAlertGenerator generator = new TestAlertGenerator();

        new BloodPressureStrategy().checkAlerts(patient, generator);

        assertFalse(generator.alerts.isEmpty(), "Expected alert for BP trend");
        assertTrue(generator.alerts.get(0).getCondition().contains("BP Trend Alert"),
                "Expected 'BP Trend Alert'");
    }

    // Subclass to capture alerts
    static class TestAlertGenerator extends AlertGenerator {
        public List<com.alerts.SimpleAlert> alerts = new ArrayList<>();

        public TestAlertGenerator() {
            super(null);
        }

        @Override
        public void triggerAlert(com.alerts.SimpleAlert alert) {
            alerts.add(alert);
        }

        @Override
        public void checkBloodPressureAlerts(int patientId, List<PatientRecord> bpRecords) {
            super.checkBloodPressureAlerts(patientId, bpRecords);
        }
    }
}
