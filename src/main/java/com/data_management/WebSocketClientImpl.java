package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;


import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
            int patientId = Integer.parseInt(matcher.group(1));
            long timestamp = Long.parseLong(matcher.group(2));
            String recordType = matcher.group(3);
            String value = matcher.group(4);
            double measurementValue = removePercentSign(value);

            dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
            
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected - Reason = " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
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
