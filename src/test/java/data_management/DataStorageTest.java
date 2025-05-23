package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


class DataStorageTest {
    
    /*
     * Unit test for adding and retrieving patientdata to/from the data storage
     */
    @Test
    void testAddAndGetRecords() {
        Path tempFile;
        try {
            tempFile = Files.createTempFile("patient_data", ".txt");
            String data =" Patient ID: 47, Timestamp: 1746730876, Label: ECG, Data: -0.2905138768980464 \n"+ 
        "Patient ID: 47, Timestamp: 1746730877, Label: Saturation, Data: 99.0%\n";
        Files.writeString(tempFile, data);


        DataStorage storage = new DataStorage();
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
        assertEquals(100.0, records.get(1).getMeasurementValue()); // Validate first record
    
            
        } catch (Exception e) {
            e.printStackTrace();
        }
            

        
    }
    
}
