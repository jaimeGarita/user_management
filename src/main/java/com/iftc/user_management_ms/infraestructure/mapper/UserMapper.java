package com.iftc.user_management_ms.infraestructure.mapper;

import com.iftc.user_management_ms.domain.persistence.User;
import com.iftc.user_management_ms.domain.proto.UserOuterClass;

public interface UserMapper {
    
    public UserOuterClass.User contractDTO (User user);

}
