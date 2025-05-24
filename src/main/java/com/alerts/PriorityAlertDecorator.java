package com.alerts;

public class PriorityAlertDecorator extends AlertDecorator{

    private String priorityLevel;

    public PriorityAlertDecorator(Alert alert, String priorityLevel) {
        super(alert);
        this.priorityLevel = priorityLevel;
    }

    public String getPriority(){
        return priorityLevel;
    }

    @Override
    public String getAllDetails() {
        return "[Priority: " + priorityLevel + "] " + baseAlert.getAllDetails();
    }

}