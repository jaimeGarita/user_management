package com.iftc.user_management_ms.observer.user;


import com.iftc.user_management_ms.model.proto.UserOuterClass;

public interface UserObserver {

    void onUserRegister(UserOuterClass.User user);
    
}
