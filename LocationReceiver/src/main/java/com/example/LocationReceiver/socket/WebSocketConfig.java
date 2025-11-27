package com.example.LocationReceiver.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig
        implements WebSocketConfigurer {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void registerWebSocketHandlers(
            WebSocketHandlerRegistry webSocketHandlerRegistry) {

        SocketStorer socketStorer = applicationContext.getBean(SocketStorer.class);
        webSocketHandlerRegistry
                .addHandler(new SocketConnectionHandler(socketStorer), "/fetchLocation")
                .setAllowedOrigins("*");
    }
}
