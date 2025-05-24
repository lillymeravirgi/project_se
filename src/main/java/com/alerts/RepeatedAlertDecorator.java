package com.alerts;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RepeatedAlertDecorator extends AlertDecorator{

    private int repetitions;
    private long repeatIntervalSec;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private int runCount = 0;
    

    public RepeatedAlertDecorator(Alert alert, int repetitions,long repeatIntervalSec) {
        super(alert);

        this.repetitions = repetitions;
        this.repeatIntervalSec = repeatIntervalSec;

        startRepeatedChecking();
    }

    private void startRepeatedChecking() {
        scheduler.scheduleAtFixedRate(() -> {
            runCount++;
            System.out.println("Repeated check #" + runCount + " for patient " + getPatientId() +
                               " | Condition: " + getCondition() +
                               " | Timestamp: " + System.currentTimeMillis());

            if (runCount >= repetitions) {
                System.out.println("Max repeats reached. Stopping checks.");
                scheduler.shutdown();
            }

        }, 0, repeatIntervalSec, TimeUnit.SECONDS);
    }
    
    @Override
    public String getAllDetails() {
        
        return baseAlert.getAllDetails() +
               " | Repeated " + repetitions + " times every " + repeatIntervalSec + " Seconds";
    }
}
