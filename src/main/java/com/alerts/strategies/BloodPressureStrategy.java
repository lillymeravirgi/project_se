package com.alerts.strategies;

import com.data_management.Patient;
import com.alerts.AlertGenerator;

public class BloodPressureStrategy implements AlertStrategy {
    @Override
    public void checkAlerts(Patient patient, AlertGenerator generator) {
        generator.checkBloodPressureAlerts(patient.getPatientId(), patient.getRecordsByType("BloodPressure"));
    }
}

