package com.example.LocationSender.socket;

import com.example.LocationSender.service.LocationSenderService;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SocketConnectionHandler extends TextWebSocketHandler {
    private final LocationSenderService mLocationSenderService;

    public SocketConnectionHandler(LocationSenderService locationSenderService) {
        mLocationSenderService = locationSenderService;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        mLocationSenderService.updateLocation(message);
    }
}
