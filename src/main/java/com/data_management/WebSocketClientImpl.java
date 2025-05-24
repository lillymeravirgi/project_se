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
    }

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

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected - Reason = " + reason);
        // Optional: Auto-reconnect
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                this.reconnect();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
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
