package com;
import com.data_management.DataStorage;

import java.io.IOException;

import com.cardio_generator.HealthDataSimulator;

public class Main {
    public static void main(String[] args) throws IOException{
        if (args.length > 0 && args[0].equals("DataStorage")) {
            DataStorage.main(new String[]{});
        } else if (args.length > 0 && args[0].equals("HealthDataSimulator")){
            HealthDataSimulator.main(new String[]{});
        }
        else{
            System.out.println("Class not found");
        }
    }
}

