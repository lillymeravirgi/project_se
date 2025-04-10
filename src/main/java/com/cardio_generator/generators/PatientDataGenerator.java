package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Inteface for several different types of data generation.
 * It outputs/inserts it in the chosen output strategy.
 * 
 * @param patientId specifies the patient
 * @param outputStrategy an element of the type outputstrategy specifies the way in which the data is stored/displayed
 * 
 */
public interface PatientDataGenerator {
    void generate(int patientId, OutputStrategy outputStrategy);
}
