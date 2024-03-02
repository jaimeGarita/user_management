package com.iftc.user_management_ms.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iftc.common.model.UserDTO;
import com.iftc.user_management_ms.mapper.UserMapper;
import com.iftc.user_management_ms.model.User;
import com.iftc.user_management_ms.model.proto.UserOuterClass;
import com.iftc.user_management_ms.observer.user.UserObserver;
import com.iftc.user_management_ms.repository.UserRepository;
import com.iftc.user_management_ms.utils.AESEncryptDecrypt;
import com.iftc.user_management_ms.utils.SHASecurityUtil;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SHASecurityUtil shaSecurityUtil;

    @Autowired
    UserObserver userObserver;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        user.setSalt(shaSecurityUtil.getSalt());
        String decryptPassw = AESEncryptDecrypt.decrypt(user.getPassword());
        shaSecurityUtil.hashPassword(decryptPassw, user.getSalt());
        User userDB = userRepository.save(user);
        UserOuterClass.User userClass = userMapper.contractDTO(userDB);
        userObserver.onUserRegister(userClass);
        return userDB;
    }

}
