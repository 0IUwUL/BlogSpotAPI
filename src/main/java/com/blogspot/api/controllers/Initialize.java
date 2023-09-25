package com.blogspot.api.controllers;

import org.springframework.stereotype.Component;

import com.blogspot.api.models.Roles;
import com.blogspot.api.repositories.RoleRepository;

import jakarta.annotation.PostConstruct;

@Component
public class Initialize {
    private final RoleRepository roleRepo;

    public Initialize(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @PostConstruct
    public void init(){
        if(roleRepo.count()==0){
            Roles role1 = new Roles();
            role1.setTitle("ADMIN");

            roleRepo.save(role1);

            Roles role2 = new Roles();
            role2.setTitle("USER");
            roleRepo.save(role2);

            Roles role3 = new Roles();
            role3.setTitle("AUTHOR");
            roleRepo.save(role3);
        }
    }
}
