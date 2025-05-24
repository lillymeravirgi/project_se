package com.alerts;

public class PriorityAlert extends AlertDecorator{

    private String priorityLevel;

    public PriorityAlert(Alert alert, String priorityLevel) {
        super(alert);
        this.priorityLevel = priorityLevel;
    }

    @Override
    public String getAllDetails() {
        return "[Priority: " + priorityLevel + "] " + baseAlert.getAllDetails();
    }

}