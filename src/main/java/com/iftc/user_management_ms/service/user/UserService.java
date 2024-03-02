package com.iftc.user_management_ms.service.user;

import java.util.List;

import com.iftc.user_management_ms.model.User;

public interface UserService {
    
    List<User> getAllUsers();
    User saveUser(User user);

}