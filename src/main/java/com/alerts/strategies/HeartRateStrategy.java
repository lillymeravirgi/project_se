package com.alerts.strategies;

import com.alerts.AlertGenerator;
import com.data_management.Patient;

public class HeartRateStrategy implements AlertStrategy {
    @Override
    public void checkAlerts(Patient patient, AlertGenerator generator) {
        generator.checkECGAlerts(patient.getPatientId(), patient.getRecordsByType("HeartRate"));
    }
}
