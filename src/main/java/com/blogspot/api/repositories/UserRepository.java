package com.blogspot.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogspot.api.models.Users;

import java.util.Optional;


public interface UserRepository extends JpaRepository<Users, Integer>{
    Optional<Users> findByUsername(String username);
    Boolean existsByUsername(String username);
    Integer countByFollowersId(int id);
}
