package com.example.LocationReceiver.socket;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SocketConnectionHandler extends TextWebSocketHandler {

    SocketStorer socketStorer;

    SocketConnectionHandler(SocketStorer socketStorer) {
        this.socketStorer = socketStorer;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        socketStorer.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        socketStorer.add(message.getPayload(), session);
    }
}
