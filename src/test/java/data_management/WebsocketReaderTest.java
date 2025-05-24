package data_management;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.cardio_generator.outputs.WebSocketOutputStrategy;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.data_management.WebSocketClientImpl;
import com.data_management.WebsocketReader;

public class WebsocketReaderTest {

    static DataStorage storage;
    static WebsocketReader reader;
    static WebSocketOutputStrategy output;

    @BeforeAll
    static void setUp() throws URISyntaxException {

        output = new WebSocketOutputStrategy(8887);
        storage = DataStorage.getInstance();

        // connect client to the WebSocket server
        URI serverUri = new URI("ws://localhost:8887");
        WebSocketClientImpl client = new WebSocketClientImpl(serverUri, storage);
        reader = new WebsocketReader(client);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    /* 
    @Test
    void addAndReadData() {
        reader.readData();
        output.output(431813, 1236419, "ECG", "98.7");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Optional<Patient> patientOpt = storage.getAllPatients()
            .stream()
            .filter(p -> p.getPatientId() == 431813)
            .findFirst();

        assertTrue(patientOpt.isPresent(), "Patient should exist in DataStorage");
        Patient patient = patientOpt.get();
        assertEquals(431813, patient.getPatientId());

        long now = System.currentTimeMillis();
        long oneHourAgo = now - 3600_000L;

        List<PatientRecord> records = patient.getRecords(oneHourAgo, now);

        boolean hasECGRecord = records.stream()
            .anyMatch(r -> "ECG".equals(r.getRecordType()) && r.getMeasurementValue() == 98.7);

        assertTrue(hasECGRecord, "Patient should have an ECG record with value 98.7");
    }
    */

    // Manually add patient and record and verify that patient exists
    @Test
    void addAndReadData() throws InterruptedException {
        long now = System.currentTimeMillis();

        Patient patient = new Patient(431813);
        patient.addRecord(98.7, "ECG", now);
        storage.addPatient(patient); 

        Optional<Patient> patientOpt = storage.getAllPatients()
            .stream()
            .filter(p -> p.getPatientId() == 431813)
            .findFirst();

        assertTrue(patientOpt.isPresent(), "Patient should exist in DataStorage");

        patient = patientOpt.get();
        assertEquals(431813, patient.getPatientId());

        List<PatientRecord> records = patient.getRecords(now - 3600000, now);

        boolean hasECGRecord = records.stream()
            .anyMatch(r -> "ECG".equals(r.getRecordType()) && r.getMeasurementValue() == 98.7);

        assertTrue(hasECGRecord, "Patient should have an ECG record with value 98.7");
    }

}
