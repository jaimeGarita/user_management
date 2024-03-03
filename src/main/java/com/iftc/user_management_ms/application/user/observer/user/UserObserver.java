package com.iftc.user_management_ms.application.user.observer.user;


import com.iftc.user_management_ms.domain.proto.UserOuterClass;

public interface UserObserver {

    void onUserRegister(UserOuterClass.User user);
    
}
