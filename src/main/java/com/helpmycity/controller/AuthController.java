package com.helpmycity.controller;

import com.helpmycity.model.Role;
import com.helpmycity.model.RoleName;
import com.helpmycity.model.User;
import com.helpmycity.payload.ApiResponse;
import com.helpmycity.payload.JwtAuthenticationResponse;
import com.helpmycity.repository.RoleRepository;
import com.helpmycity.repository.UserRepository;
import com.helpmycity.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public Object login(@RequestParam String email, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email, password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));

        /*User user = userService.findUserByEmail(email);
        if (user == null)
            return new Status(StatusCode.FAILED, "Email and password incorrect");
        else if (user.getPassword().equals(Crypt.getSecurePassword(password)))
            return user;

        else
            return new Status(StatusCode.FAILED, "Password incorrect");*/
    }

    @PostMapping("/signup")
    public Object createNewUser(@RequestParam String email, @RequestParam String username, @RequestParam String password, @RequestParam String name,
                                @RequestParam String lastName) {
        if(userRepository.existsByEmail(email)){
            return new ResponseEntity(
                    new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }



        // Creating user's account
        User user = new User();

        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setLastName(lastName);



        Role userRole = roleRepository.findByRole(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{email}")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true,
                "User registered successfully"));

    }


}