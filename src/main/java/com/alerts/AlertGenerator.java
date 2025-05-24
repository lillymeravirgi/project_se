package com.alerts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.alerts.factories.AlertFactory;
import com.alerts.factories.BloodOxygenAlertFactory;
import com.alerts.factories.BloodPressureAlertFactory;
import com.alerts.factories.ECGAlertFactory;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    private final AlertFactory bpAlertFactory = new BloodPressureAlertFactory();
    private final AlertFactory spo2AlertFactory = new BloodOxygenAlertFactory();
    private final AlertFactory ecgAlertFactory = new ECGAlertFactory();


    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     * @param dataStorage the data storage system that provides access to patient data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    // Fix the link
    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert(Alert)}
     * method. This method should define the specific conditions under which an
     * alert will be triggered.
     * @param patient the patient data to evaluate for alert conditions
     */

    public void evaluateData(Patient patient) {
        List<PatientRecord> records = patient.getRecords(Long.MIN_VALUE, Long.MAX_VALUE);
        List<PatientRecord> bpRecords = new ArrayList<>();
        List<PatientRecord> spo2Records = new ArrayList<>();
        List<PatientRecord> ecgRecords = new ArrayList<>();

        for (PatientRecord record : records) {
            switch (record.getRecordType()) {
                case "BloodPressureSystolic":
                case "BloodPressureDiastolic":
                    bpRecords.add(record);
                    break;
                case "SpO2":
                    spo2Records.add(record);
                    break;
                case "ECG":
                    ecgRecords.add(record);
                    break;
                case "AlertTrigger":
                    triggerAlert(new SimpleAlert(String.valueOf(patient.getPatientId()), "Manual Alert Triggered", record.getTimestamp()));
                    break;
            }
        }

        checkBloodPressureAlerts(patient.getPatientId(), bpRecords);
        checkSpO2Alerts(patient.getPatientId(), spo2Records);
        checkCombinedAlert(patient.getPatientId(), bpRecords, spo2Records);
        checkECGAlerts(patient.getPatientId(), ecgRecords);
    }

    private void checkBloodPressureAlerts(int patientId, List<PatientRecord> bpRecords) {
        bpRecords.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        for (int i = 2; i < bpRecords.size(); i++) {
            double val1 = bpRecords.get(i - 2).getMeasurementValue();
            double val2 = bpRecords.get(i - 1).getMeasurementValue();
            double val3 = bpRecords.get(i).getMeasurementValue();
            long timestamp = bpRecords.get(i).getTimestamp();

            if ((val2 - val1 > 10 && val3 - val2 > 10) || (val1 - val2 > 10 && val2 - val3 > 10)) {
                // triggerAlert(new SimpleAlert(String.valueOf(patientId), "BP Trend Alert", timestamp));
                triggerAlert((SimpleAlert) bpAlertFactory.createAlert(String.valueOf(patientId), "BP Trend Alert", timestamp));
            }
        }

        for (PatientRecord record : bpRecords) {
            double val = record.getMeasurementValue();
            if (val > 180 || val < 90) {
                // changed alert
                triggerAlert((SimpleAlert) bpAlertFactory.createAlert(String.valueOf(patientId), "Critical Blood Pressure", record.getTimestamp()));
            }
        }
    }

    private void checkSpO2Alerts(int patientId, List<PatientRecord> spo2Records) {
        spo2Records.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        for (int i = 0; i < spo2Records.size(); i++) {
            PatientRecord record = spo2Records.get(i);
            if (record.getMeasurementValue() < 92) {
                // changed alert
                triggerAlert((SimpleAlert) spo2AlertFactory.createAlert(String.valueOf(patientId), "Low SpO2 Alert", record.getTimestamp()));
            }

            for (int j = i + 1; j < spo2Records.size(); j++) {
                PatientRecord later = spo2Records.get(j);
                if (later.getTimestamp() - record.getTimestamp() <= 600000 &&
                    record.getMeasurementValue() - later.getMeasurementValue() >= 5) {
                    // changed alert
                    triggerAlert((SimpleAlert) spo2AlertFactory.createAlert(String.valueOf(patientId), "Rapid SpO2 Drop", later.getTimestamp()));
                    break;
                }
            }
        }
    }

    private void checkCombinedAlert(int patientId, List<PatientRecord> bpRecords, List<PatientRecord> spo2Records) {
        for (PatientRecord bp : bpRecords) {
            if (bp.getMeasurementValue() < 90) {
                for (PatientRecord spo2 : spo2Records) {
                    if (Math.abs(bp.getTimestamp() - spo2.getTimestamp()) <= 600000 && spo2.getMeasurementValue() < 92) {
                        triggerAlert(new SimpleAlert(String.valueOf(patientId), "Hypotensive Hypoxemia Alert", spo2.getTimestamp()));
                    }
                }
            }
        }
    }

    private void checkECGAlerts(int patientId, List<PatientRecord> ecgRecords) {
        final int WINDOW_SIZE = 5;
        for (int i = WINDOW_SIZE; i < ecgRecords.size(); i++) {
            double sum = 0;
            for (int j = i - WINDOW_SIZE; j < i; j++) {
                sum += ecgRecords.get(j).getMeasurementValue();
            }
            double avg = sum / WINDOW_SIZE;
            double peak = ecgRecords.get(i).getMeasurementValue();
            if (peak > avg * 1.5) {
                // changed alert
                triggerAlert((SimpleAlert) ecgAlertFactory.createAlert(String.valueOf(patientId), "ECG Spike Alert", ecgRecords.get(i).getTimestamp()));
            }
        }
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     * @param alert the alert object containing details about the alert condition
     */
    //Added TODO comment
    private void triggerAlert(SimpleAlert alert) {
        System.out.println("ALERT TRIGGERED: " + alert.getCondition() +
                " | Patient ID: " + alert.getPatientId() +
                " | Time: " + alert.getTimestamp());
    }
}
