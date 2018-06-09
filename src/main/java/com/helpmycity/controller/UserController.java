package com.helpmycity.controller;

import com.google.gson.Gson;
import com.helpmycity.exception.ResourceNotFoundException;
import com.helpmycity.model.Reclamation;
import com.helpmycity.model.Role;
import com.helpmycity.model.RoleName;
import com.helpmycity.model.User;
import com.helpmycity.payload.ApiResponse;
import com.helpmycity.payload.UserIdentityAvailability;
import com.helpmycity.payload.UserProfile;
import com.helpmycity.payload.UserSummary;
import com.helpmycity.repository.ReclamationRepository;
import com.helpmycity.repository.RoleRepository;
import com.helpmycity.repository.UserRepository;
import com.helpmycity.security.CurrentUser;
import com.helpmycity.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private RoleRepository roleRepository;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReclamationRepository reclamationRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUser(@CurrentUser UserPrincipal currentUser) {
        return userRepository.findAll();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(),
                currentUser.getName());
        return userSummary;
    }

    @GetMapping("/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        //long reclamationsCount = reclamationRepository.countByCreatedBy(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getEmail(), user.getUsername(), user.getName(), user.getLastName(), null, user.getRoles());

        return userProfile;
    }

    @PostMapping("/update_user")
    public Object setRole(@RequestBody String userProfile) {
        Gson gson = new Gson();
        UserProfile profile = gson.fromJson(userProfile, UserProfile.class);
        if (userRepository.existsById(profile.getId())) {
            User user = userRepository.findById(profile.getId()).orElseThrow(() -> new RuntimeException("User not found"));


            Role userRole = roleRepository.findByRole(RoleName.getRole(profile.getRoles().iterator().next().getRole().name()))
                    .orElseThrow(() -> new RuntimeException("User Role not set."));

            PasswordEncoder passwordEncoder = new PasswordEncoder() {
                @Override
                public String encode(CharSequence charSequence) {
                    return null;
                }

                @Override
                public boolean matches(CharSequence charSequence, String s) {
                    return false;
                }
            };
            user.setRoles(Collections.singleton(userRole));
            user.setName(profile.getName());
            user.setLastName(profile.getLastName());
            //user.setPassword(passwordEncoder.encode(profile.));
            User result = userRepository.save(user);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/users/{email}")
                    .buildAndExpand(result.getEmail()).toUri();

            return ResponseEntity.created(location).body(new ApiResponse(true,
                    "User update successfully"));

        } else {

            return new ResponseEntity(
                    new ApiResponse(false, "internal error!"),
                    HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/{username}/reclamations")
    public Page<Reclamation> getReclamationsCreatedBy(Pageable pageable, @PathVariable(value = "username")
            String username  /*@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(value = "size",
                                                defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size*/) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return reclamationRepository.findByCreatedBy(user.getId(), pageable);
    }

}