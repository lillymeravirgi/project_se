package com.data_management;

import java.net.URI;

public class WebsocketReader implements DataReader {

    private URI uri;

    public WebsocketReader(URI uri) {
        this.uri = uri;
    }

    @Override
    public void readData(DataStorage dataStorage) {
        WebSocketClientImpl client = new WebSocketClientImpl(uri, dataStorage);
        client.connect();
    }
    
}
