package com.blogspot.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogspot.api.models.Roles;


public interface RoleRepository extends JpaRepository<Roles, Integer>{
    Optional<Roles> findByTitle(String title);
}
