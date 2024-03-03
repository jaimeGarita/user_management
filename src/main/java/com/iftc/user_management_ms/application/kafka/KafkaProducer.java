package com.iftc.user_management_ms.application.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.iftc.common.model.UserDTO;
import com.iftc.user_management_ms.domain.proto.UserOuterClass;


@Component
public class KafkaProducer {
    
    @Value("${kafka.topic}")
    private String TOPIC;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(UserOuterClass.User user){
        kafkaTemplate.send(TOPIC, user);
    }

}
