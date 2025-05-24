package strategies;

import java.util.ArrayList;
import java.util.List;

import com.alerts.AlertGenerator;
import com.alerts.SimpleAlert;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;

public class TestAlertGenerator extends AlertGenerator {

    public List<SimpleAlert> triggeredAlerts = new ArrayList<>();

    public TestAlertGenerator(DataStorage dataStorage) {
        super(dataStorage);
    }

    @Override
    public void triggerAlert(SimpleAlert alert) {
        triggeredAlerts.add(alert);  // Capture alerts for test verification
    }

    // Make public access if not already
    @Override
    public void checkBloodPressureAlerts(int patientId, List<PatientRecord> records) {
        super.checkBloodPressureAlerts(patientId, records);
    }

    @Override
    public void checkSpO2Alerts(int patientId, List<PatientRecord> records) {
        super.checkSpO2Alerts(patientId, records);
    }

    @Override
    public void checkECGAlerts(int patientId, List<PatientRecord> records) {
        super.checkECGAlerts(patientId, records);
    }
}

