package com.iftc.user_management_ms.infraestructure.mapper;

import org.springframework.stereotype.Component;

import com.iftc.common.model.UserDTO;
import com.iftc.user_management_ms.domain.persistence.User;
import com.iftc.user_management_ms.domain.proto.UserOuterClass;

@Component
public class UserMapperImpl implements UserMapper{

    @Override
    public UserOuterClass.User contractDTO(User user) {

        UserOuterClass.User userClass = UserOuterClass.User.newBuilder()
        .setId(user.getId().intValue())
        .setUsername(user.getUsername())
        .setPassword(user.getPassword())
        .setSalt(user.getSalt())
        .setEmail(user.getEmail())
        .build();
    
       return userClass;
    }

    
}
