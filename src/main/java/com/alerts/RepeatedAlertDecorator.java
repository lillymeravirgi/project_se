package com.alerts;

public class RepeatedAlertDecorator extends AlertDecorator{

    private int repeatCount;
    private long repeatIntervalMs;
    

    public RepeatedAlertDecorator(Alert alert, String repetitionCondition) {
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
