package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class DataFileReader implements DataReader{
    String filepath;
    /** 
    * The DataFileReader can be used to read data of type: Patient ID: , Timestamp: , Label: , Data:  from a file and store it 
    * in a DataStorage
    * @param filePath the filePath to the file with the patient data 
    */
    public DataFileReader (String filePath){
        
        this.filepath = filePath;

        if (!pathExists()){
            System.out.println("Path not found: " + filePath);
        }
    }
    /** 
    * the method assumes the data in the file has the pattern: Patient ID: , Timestamp: , Label: , Data: 
    * @param dataStorage the datastorage used to store the data that is read from the file
    */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        
        String regex = "Patient ID: (\\d+), Timestamp: (\\d+), Label: (\\w+), Data: (.+)\\s*";
        ;

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            Pattern pattern = Pattern.compile(regex);
            String line;

            
            
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    int patientId = Integer.parseInt(matcher.group(1));
                    long timestamp = Long.parseLong(matcher.group(2));
                    String recordType = matcher.group(3);
                    String value = matcher.group(4);
                    double measurementValue = removePercentSign(value);

                    dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
                    
                }
            }
            bufferedReader.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } }

        private boolean pathExists(){
            Path path = Paths.get(filepath);
            if (Files.exists(path)) {
                return true;
            } else {
                System.out.println("Path does not exist: " + path.toAbsolutePath());
                return false;
            }


        }
        private double removePercentSign(String data) {
          
            if (data.contains("%")) {
               
                data = data.replace("%", "");
                return Double.parseDouble(data) / 100.0; 
            } else {
                return Double.parseDouble(data);
            }
        }
    }
