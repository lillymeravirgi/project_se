package data_management;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.cardio_generator.HealthDataSimulator;

public class HealthDataSimulatorTest {

    @Test
    public void testSingleton(){
        HealthDataSimulator firstSimulator = HealthDataSimulator.getInstance();
        HealthDataSimulator secondSimulator = HealthDataSimulator.getInstance();
        assertEquals(firstSimulator, secondSimulator);

    }
}
