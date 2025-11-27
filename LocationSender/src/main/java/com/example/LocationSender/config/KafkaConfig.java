package com.example.LocationSender.config;

import com.example.LocationSender.constant.Constant;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic() {
        // Create a kafka topic for location
        return TopicBuilder.name(Constant.LOCATION).build();
    }
}
