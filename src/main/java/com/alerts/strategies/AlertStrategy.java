package com.alerts.strategies;

import com.alerts.AlertGenerator;
import com.data_management.Patient;

public interface AlertStrategy {
    void checkAlerts(Patient patient, AlertGenerator generator);
}
