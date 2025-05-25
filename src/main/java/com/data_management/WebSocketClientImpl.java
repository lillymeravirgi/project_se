package com.data_management;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;


public class WebSocketClientImpl extends WebSocketClient{

    private DataStorage dataStorage;
    public WebSocketClientImpl(URI uri, DataStorage dataStorage){
        super(uri);
        this.dataStorage = dataStorage;

    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to server");
        reconnectAttempts =0;
    }

    /*
    * when the client receives a message/data it is turned seperated into its components
    * using a pattern and a matcher and then inserted into the storage
    */
    @Override
    public void onMessage(String data) {
        String regex = "(\\d+),(\\d+),(\\w+),(.+)\\s*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            try {
                int patientId = Integer.parseInt(matcher.group(1));
                long timestamp = Long.parseLong(matcher.group(2));
                String recordType = matcher.group(3);
                String value = matcher.group(4);
                double measurementValue = removePercentSign(value);

                dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
            } catch (NumberFormatException e) {
                System.err.println("Data parse error: " + data);
            }
        } else {
            System.err.println("Corrupted data format: " + data);
        }
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket error: " + ex.getMessage());
        ex.printStackTrace();
    }

    private int reconnectAttempts = 0;
    private final int maxReconnectAttempts = 5;
    
    /*
     * tries to reconnect with the server for a set amount of times (maxReconnectAttemps)
     * waits for an exponentially increasing time between tries
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected - Reason = " + reason);
    
        if (reconnectAttempts >= maxReconnectAttempts) {
            System.err.println("Max reconnect attempts reached. Giving up.");
            return;
        }
    
        new Thread(() -> {
            try {
                // Exponential backoff: wait time increases with each attempt
                long waitTime = (long) Math.pow(2, reconnectAttempts) * 1000; // 1s, 2s, 4s, 8s, ...
                System.out.println("Attempting to reconnect in " + waitTime / 1000 + " seconds...");
                Thread.sleep(waitTime);
    
                reconnectAttempts++;
                this.reconnect();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    

       /**
     * Adds a new record to this patient's list of medical records.
     * The record is created with the specified measurement value, record type, and
     * timestamp.
     *                       
     * @param data        String that contains the measurement value
     * @return the string without the perecentage sign (if it had one)
     * 
     */ 
    private double removePercentSign(String data) {
        
        if (data.contains("%")) {
            data = data.replace("%", "");
            return Double.parseDouble(data) / 100.0;
        } else {
            return Double.parseDouble(data);
        }
    }
    
}
