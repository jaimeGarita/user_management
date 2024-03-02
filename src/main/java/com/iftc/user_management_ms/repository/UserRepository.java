package com.iftc.user_management_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iftc.user_management_ms.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
