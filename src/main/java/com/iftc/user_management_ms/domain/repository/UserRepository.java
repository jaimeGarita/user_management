package com.iftc.user_management_ms.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iftc.user_management_ms.domain.persistence.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
