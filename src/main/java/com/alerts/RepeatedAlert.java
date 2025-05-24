package com.alerts;

public class RepeatedAlert extends AlertDecorator{

    private int repeatCount;
    private long repeatIntervalMs;
    

    public RepeatedAlert(Alert alert, String repetitionCondition) {
        super(alert);

        this.repeatCount = repeatCount;
        this.repeatIntervalMs = repeatIntervalMs;


    }
    
    @Override
    public String getAllDetails() {
        
        return baseAlert.getAllDetails() +
               " | Repeated " + repeatCount + "x every " + repeatIntervalMs + "ms";
    }
}
