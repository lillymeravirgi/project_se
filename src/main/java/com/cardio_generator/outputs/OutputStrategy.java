package com.cardio_generator.outputs;

/**
 * Interface for all outputstrategies
 * used to show/store data
 */
public interface OutputStrategy {
    /**
    * adds patient data to a type of database
    *
    * @param patientId the ID of the patient 
    * @param timestamp the time at which the measurement was taken, in milliseconds 
    * @param label the type of data eg. ECG, Saturdation,... 
    * @param data data value eg. 90.0%
    */
    void output(int patientId, long timestamp, String label, String data);
}
