package com.alerts;

public abstract class AlertDecorator implements Alert {

    protected Alert baseAlert;

    public AlertDecorator(Alert alert){
        this.baseAlert = alert;
    }

    @Override
    public String getPatientId() {
    return baseAlert.getPatientId();
    }

    @Override
    public String getCondition() {
       return baseAlert.getCondition();
    }

    @Override
    public long getTimestamp() {
        return baseAlert.getTimestamp();    
    }
    
    @Override
    public String getAllDetails(){
       return baseAlert.getAllDetails();
       
    }
}
