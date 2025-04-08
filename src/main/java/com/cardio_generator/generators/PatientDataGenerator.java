package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Inteface for several different types of data generation
 * 
 * @param patientId specifies the patient
 * @param outputStrategy an element of the type outputstrategy specifies the way in which the data is stored/displayed
 * 
 * @return Randomly synthesises health data and outputs/inserts it in the chosen way.
 */
public interface PatientDataGenerator {
    void generate(int patientId, OutputStrategy outputStrategy);
}
