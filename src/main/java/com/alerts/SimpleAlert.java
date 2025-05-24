package com.alerts;

// Represents an alert
public class SimpleAlert implements Alert{
    private String patientId;
    private String condition;
    private long timestamp;

    public SimpleAlert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getCondition() {
        return condition;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getAllDetails(){
        return String.format("Patient ID: %s, Timestamp: %d, Condition: %s", 
        patientId, timestamp, condition);
    }
}
