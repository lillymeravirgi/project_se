package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import com.data_management.DataFileReader;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class DataFileReaderTest {

    private Path tempFile;
    private DataFileReader dataFileReader;
    private DataStorage storage;

    @BeforeEach
    void setUp() throws IOException {


        tempFile = Files.createTempFile("patient_data", ".txt");

        String data =" Patient ID: 47, Timestamp: 1746730876, Label: ECG, Data: -0.2905138768980464 \n"+ 
        "Patient ID: 47, Timestamp: 1746730877, Label: Saturation, Data: 99.0%\n";
        Files.writeString(tempFile, data);


        dataFileReader = new DataFileReader(tempFile.toString());
        storage = new DataStorage();

    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testReadData() {
        try {
            dataFileReader.readData(storage);

       
            List<PatientRecord> records = storage.getRecords(47, 0, 1746730880);
            assertEquals(2, records.size());

            assertEquals(47, records.get(0).getPatientId());
            assertEquals(1746730876, records.get(0).getTimestamp());
            assertEquals("ECG", records.get(0).getRecordType());
            assertEquals(-0.2905138768980464, records.get(0).getMeasurementValue());

            assertEquals(47, records.get(1).getPatientId());
            assertEquals(1746730877, records.get(1).getTimestamp());
            assertEquals("Saturation", records.get(1).getRecordType());
            assertEquals(0.99, records.get(1).getMeasurementValue());

        } catch (IOException e) {
            fail("IOException should not have occurred: " + e.getMessage());
        }
    }
    @Test
    void handleWrongData(){

    }
}
