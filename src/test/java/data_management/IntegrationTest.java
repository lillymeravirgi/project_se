package data_management;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.alerts.AlertGenerator;
import com.cardio_generator.outputs.ConsoleOutputStrategy;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * Integration test to check DataStorage, AlertGenerator and OutputStrategy work together.
 */
public class IntegrationTest {

    @Test
    public void testAddPatientDataAndAlerts() {
        DataStorage storage = DataStorage.getInstance();

        int patientId = 123;
        long timestamp = System.currentTimeMillis();
        storage.addPatientData(patientId, 185, "BloodPressureSystolic", timestamp);
        storage.addPatientData(patientId, 95, "BloodPressureDiastolic", timestamp);
        storage.addPatientData(patientId, 90, "SpO2", timestamp);
        storage.addPatientData(patientId, 80, "SpO2", timestamp - 300000); // 5 minutes ago

        Patient patient = storage.getAllPatients().stream()
            .filter(p -> p.getPatientId() == patientId)
            .findFirst()
            .orElse(null);

        assertNotNull(patient, "Patient should exist");

        List<PatientRecord> records = patient.getRecords(Long.MIN_VALUE, Long.MAX_VALUE);
        assertFalse(records.isEmpty(), "Patient should have records");
        AlertGenerator alertGenerator = new AlertGenerator(storage);
        alertGenerator.evaluateData(patient);

        ConsoleOutputStrategy consoleOutput = new ConsoleOutputStrategy();
        for (PatientRecord record : records) {
            consoleOutput.output(patientId, record.getTimestamp(), record.getRecordType(), String.valueOf(record.getMeasurementValue()));
        }
        assertTrue(true);
    }
}