package com.alerts.strategies;

import com.data_management.Patient;
import com.alerts.AlertGenerator;

public class OxygenSaturationStrategy implements AlertStrategy {
    @Override
    public void checkAlerts(Patient patient, AlertGenerator generator) {
        generator.checkSpO2Alerts(patient.getPatientId(), patient.getRecordsByType("SpO2"));
    }
}
