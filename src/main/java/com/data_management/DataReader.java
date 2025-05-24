package com.data_management;



public interface DataReader {
    /**
     * Reads data from a specified source and stores it in the data storage.
     * 
     * @param dataStorage the storage where data will be stored
     * 
     */
    void readData(DataStorage dataStorage);
}
