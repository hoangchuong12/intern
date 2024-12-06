package com.user.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


import com.user.demo.entity.User;

public interface UserRepository extends JpaRepository<User , UUID>{
    Optional<User> findByGmail(String gmail);
    Optional<User> findByName(String name);

}
