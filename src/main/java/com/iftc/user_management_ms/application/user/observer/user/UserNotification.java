package com.iftc.user_management_ms.application.user.observer.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iftc.user_management_ms.application.kafka.KafkaProducer;
import com.iftc.user_management_ms.domain.proto.UserOuterClass;

@Component
public class UserNotification implements UserObserver{

    @Autowired
    KafkaProducer kafkaProducer;

    @Override
    public void onUserRegister(UserOuterClass.User user) {
        kafkaProducer.sendMessage(user);
        System.out.println("USUARIO CREADO A VER");
    }
    
}
