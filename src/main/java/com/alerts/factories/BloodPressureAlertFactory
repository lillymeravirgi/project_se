package com.alerts.factories;

import com.alerts.Alert;
import com.alerts.SimpleAlert;

public class BloodPressureAlertFactory implements AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new SimpleAlert(patientId, condition, timestamp);
    }
}
