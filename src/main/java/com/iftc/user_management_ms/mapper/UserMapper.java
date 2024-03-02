package com.iftc.user_management_ms.mapper;

import com.iftc.user_management_ms.model.User;
import com.iftc.user_management_ms.model.proto.UserOuterClass;

public interface UserMapper {
    
    public UserOuterClass.User contractDTO (User user);

}
