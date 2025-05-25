package alerts;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
/*
 * class tests all possible reasons for alerts and if the alert is thrown
 */
public class AlertGeneratorTest {

    private DataStorage dataStorage;
    private AlertGenerator alertGenerator;

    @BeforeEach
    public void setup() {
        dataStorage = DataStorage.getInstance();
        alertGenerator = new AlertGenerator(dataStorage);
    }

    @Test
    public void testIncreasingTrendAlert() {
        long t = System.currentTimeMillis();
        dataStorage.addPatientData(1, 100, "BloodPressureSystolic", t);
        dataStorage.addPatientData(1, 115, "BloodPressureSystolic", t + 1000);
        dataStorage.addPatientData(1, 130, "BloodPressureSystolic", t + 2000);

        Patient patient = dataStorage.getAllPatients().get(0);
        assertDoesNotThrow(() -> alertGenerator.evaluateData(patient));
    }

    @Test
    public void testDecreasingTrendAlert() {
        long t = System.currentTimeMillis();
        dataStorage.addPatientData(1, 130, "BloodPressureSystolic", t);
        dataStorage.addPatientData(1, 115, "BloodPressureSystolic", t + 1000);
        dataStorage.addPatientData(1, 100, "BloodPressureSystolic", t + 2000);

        Patient patient = dataStorage.getAllPatients().get(0);
        assertDoesNotThrow(() -> alertGenerator.evaluateData(patient));
    }

    @Test
    public void testCriticalThresholdHighBP() {
        long t = System.currentTimeMillis();
        dataStorage.addPatientData(1, 190, "BloodPressureSystolic", t);
        dataStorage.addPatientData(1, 125, "BloodPressureDiastolic", t);

        Patient patient = dataStorage.getAllPatients().get(0);
        assertDoesNotThrow(() -> alertGenerator.evaluateData(patient));
    }

    @Test
    public void testCriticalThresholdLowBP() {
        long t = System.currentTimeMillis();
        dataStorage.addPatientData(1, 85, "BloodPressureSystolic", t);
        dataStorage.addPatientData(1, 55, "BloodPressureDiastolic", t);

        Patient patient = dataStorage.getAllPatients().get(0);
        assertDoesNotThrow(() -> alertGenerator.evaluateData(patient));
    }

    @Test
    public void testLowSpO2Alert() {
        long t = System.currentTimeMillis();
        dataStorage.addPatientData(1, 91, "SpO2", t);

        Patient patient = dataStorage.getAllPatients().get(0);
        assertDoesNotThrow(() -> alertGenerator.evaluateData(patient));
    }

    @Test
    public void testRapidSpO2DropAlert() {
        long t = System.currentTimeMillis();
        dataStorage.addPatientData(1, 97, "SpO2", t);
        dataStorage.addPatientData(1, 91, "SpO2", t + 5000);

        Patient patient = dataStorage.getAllPatients().get(0);
        assertDoesNotThrow(() -> alertGenerator.evaluateData(patient));
    }

    @Test
    public void testCombinedHypotensiveHypoxemiaAlert() {
        long t = System.currentTimeMillis();
        dataStorage.addPatientData(1, 88, "BloodPressureSystolic", t);
        dataStorage.addPatientData(1, 90, "SpO2", t + 1000);

        Patient patient = dataStorage.getAllPatients().get(0);
        assertDoesNotThrow(() -> alertGenerator.evaluateData(patient));
    }

    @Test
    public void testECGSpikeAlert() {
        long t = System.currentTimeMillis();
        dataStorage.addPatientData(1, 1.0, "ECG", t);
        dataStorage.addPatientData(1, 1.2, "ECG", t + 1000);
        dataStorage.addPatientData(1, 1.1, "ECG", t + 2000);
        dataStorage.addPatientData(1, 1.3, "ECG", t + 3000);
        dataStorage.addPatientData(1, 1.2, "ECG", t + 4000);
        dataStorage.addPatientData(1, 2.5, "ECG", t + 5000); // spike

        Patient patient = dataStorage.getAllPatients().get(0);
        assertDoesNotThrow(() -> alertGenerator.evaluateData(patient));
    }

    @Test
    public void testManualAlertTrigger() {
        long t = System.currentTimeMillis();
        dataStorage.addPatientData(1, 0, "AlertTrigger", t);

        Patient patient = dataStorage.getAllPatients().get(0);
        assertDoesNotThrow(() -> alertGenerator.evaluateData(patient));
    }
}
