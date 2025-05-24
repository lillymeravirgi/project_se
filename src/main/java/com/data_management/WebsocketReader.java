package com.data_management;

public class WebsocketReader implements DataReader {

    private WebSocketClientImpl client;

    public WebsocketReader(WebSocketClientImpl client){
        this.client = client;
    }

    
    @Override
    public void readData() {
        client.connect();
    }
    
}
