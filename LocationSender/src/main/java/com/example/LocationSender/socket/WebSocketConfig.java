package com.example.LocationSender.socket;

import com.example.LocationSender.service.LocationSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        LocationSenderService locationSenderService = applicationContext.getBean(LocationSenderService.class);
        webSocketHandlerRegistry
                .addHandler(new SocketConnectionHandler(locationSenderService), "/updateLocation")
                .setAllowedOrigins("*");
    }
}
