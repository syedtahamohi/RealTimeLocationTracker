package com.example.LocationSender.service;

import com.example.LocationSender.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketMessage;

@Service
public class LocationSenderService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void updateLocation(WebSocketMessage<?> message) {
        kafkaTemplate.send(Constant.LOCATION, message.getPayload().toString());
    }
}
