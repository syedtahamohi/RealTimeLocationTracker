package com.example.LocationReceiver.socket;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SocketStorer {
    Map<String, Set<WebSocketSession>> webSocketSessions = new HashMap<>();
    Map<WebSocketSession, String> sessionShareIDMap = new HashMap<>();

    public void add(String shareID, WebSocketSession session) {
        remove(session);
        sessionShareIDMap.put(session, shareID);
        System.out.println(session.getId() + " " + shareID);
        getOrCreateWebSocketSessions(shareID).add(session);
    }

    public void remove(WebSocketSession session) {
        Set<WebSocketSession> sessions = webSocketSessions.get(sessionShareIDMap.get(session));
        if(sessions != null) {
            sessions.remove(session);
        }
        sessionShareIDMap.remove(session);
    }

    public Set<WebSocketSession> getWebSocketSessions(String shareID) {
        return webSocketSessions.get(shareID);
    }

    public Set<WebSocketSession> getOrCreateWebSocketSessions(String shareID) {
        return webSocketSessions.computeIfAbsent(shareID, k -> new HashSet<>());
    }
}
