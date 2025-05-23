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
        


        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
        assertEquals(200.0, records.get(1).getMeasurementValue()); // Validate second record
            
    }
    
    /*
     * method checks if the singleton pattern is correctly implemented
     */
    @Test
    void checkSingleton(){
        DataStorage dataStorage = DataStorage.getInstance();
        DataStorage seconDataStorage = DataStorage.getInstance();
        assertEquals(dataStorage, seconDataStorage);
    }
}
