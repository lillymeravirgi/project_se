package data_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;


public class GetRecordsTest {
    @Test
    void testInclusivelowerBound() {
        Patient patient = new Patient(123);
        patient.addRecord(123,"ECG", (100));

        patient.addRecord( 456,"ECG", (101));
            PatientRecord recordOne = new PatientRecord(123, 456, "ECG", 101);
        patient.addRecord( 789,"ECG", (102));
            PatientRecord recordTwo = new PatientRecord(123, 789, "ECG", 102);

            List<PatientRecord> wantedRecords = new ArrayList<PatientRecord>();
            wantedRecords.add(recordOne);
            wantedRecords.add(recordTwo);

        
        List<PatientRecord> retrievedRecords = patient.getRecords(101, 110);
        
        assertEquals(wantedRecords, retrievedRecords);

    }
    @Test
    void testInclusiveupperBound() {
        Patient patient = new Patient(123);
        patient.addRecord(123,"ECG", (100));
            PatientRecord recordOne = new PatientRecord(123, 123, "ECG", 100);  
        patient.addRecord( 456,"ECG", (101));
            PatientRecord recordTwo = new PatientRecord(123, 456,"ECG", 101);
        patient.addRecord( 789,"ECG", (102));

            List<PatientRecord> wantedRecords = new ArrayList<PatientRecord>();
            wantedRecords.add(recordOne);
            wantedRecords.add(recordTwo);

        List<PatientRecord> retrievedRecords = patient.getRecords(90, 101);

        assertEquals(wantedRecords, retrievedRecords);
    }



}
