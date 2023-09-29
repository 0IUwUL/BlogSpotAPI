package com.blogspot.api.controllers;

import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogspot.api.dto.AuthResponseDTO;
import com.blogspot.api.dto.LoginDTO;
import com.blogspot.api.dto.RegisterDTO;
import com.blogspot.api.models.Roles;
import com.blogspot.api.models.Users;
import com.blogspot.api.repositories.RoleRepository;
import com.blogspot.api.repositories.UserRepository;
import com.blogspot.api.security.TokenGenerator;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private PasswordEncoder passwordEncoder;
    private TokenGenerator tokenGenerator;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepo, RoleRepository roleRepo,
            PasswordEncoder passwordEncoder, TokenGenerator tokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
    }
    
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        if(userRepo.existsByUsername(registerDTO.getUsername()))
            return new ResponseEntity<>("Username is taken.", HttpStatus.BAD_REQUEST);
        Users user = new Users();
        user.setUsername(registerDTO.getUsername());
        user.setCreatedOn(LocalDateTime.now());
        user.setUpdatedOn(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Roles role = roleRepo.findByTitle("USER").get();
        user.setRoles(Collections.singletonList(role));

        userRepo.save(user);

        return new ResponseEntity<>("User registered.", HttpStatus.OK); 

    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                                            new UsernamePasswordAuthenticationToken(
                                                loginDTO.getUsername(), 
                                                loginDTO.getPassword()
                                            ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token),HttpStatus.OK);
    }
    
}
