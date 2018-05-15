package com.helpmycity.auth.service;

import com.helpmycity.model.Role;
import com.helpmycity.model.RoleName;
import com.helpmycity.model.User;
import com.helpmycity.repository.RoleRepository;
import com.helpmycity.repository.UserRepository;
import com.helpmycity.util.Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(Crypt.getSecurePassword(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("User Role not set."));
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }



}