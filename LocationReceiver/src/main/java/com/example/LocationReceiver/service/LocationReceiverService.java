package com.example.LocationReceiver.service;

import com.example.LocationReceiver.socket.SocketStorer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

@Service
public class LocationReceiverService {

    @Autowired
    SocketStorer socketStorer;

    @KafkaListener(topics = "Location", groupId = "user-group")
    public void fetchLocation(String message) {
        try {
            sendLocationToClients(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendLocationToClients(String message) throws IOException {

        JSONObject locationInfo = convertToJSON(message);
        if (locationInfo == null) {
            return;
        }
        Set<WebSocketSession> sessions = socketStorer.getWebSocketSessions(locationInfo.get("shareID").toString());

        if (sessions == null) {
            return;
        }
        locationInfo.remove("shareID");
        TextMessage textMessage = new TextMessage(locationInfo.toString());
        for (WebSocketSession webSocketSession : sessions) {
            System.out.println("Message to send " + message);
            webSocketSession.sendMessage(textMessage);
        }
    }

    private JSONObject convertToJSON(String message) {
        JSONObject locationInfo = null;
        try {
            locationInfo = (JSONObject) new JSONParser().parse(message);
        } catch (ParseException ignore) {

        }
        return locationInfo;
    }
}
