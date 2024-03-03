package com.iftc.user_management_ms.application.user.servivces;

import java.util.List;

import com.iftc.user_management_ms.domain.persistence.User;

public interface UserService {
    
    List<User> getAllUsers();
    User saveUser(User user);

}