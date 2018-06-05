package com.helpmycity.controller;

import com.helpmycity.exception.ResourceNotFoundException;
import com.helpmycity.model.Reclamation;
import com.helpmycity.model.User;
import com.helpmycity.payload.UserIdentityAvailability;
import com.helpmycity.payload.UserProfile;
import com.helpmycity.payload.UserSummary;
import com.helpmycity.repository.ReclamationRepository;
import com.helpmycity.repository.UserRepository;
import com.helpmycity.security.CurrentUser;
import com.helpmycity.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

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

        UserProfile userProfile = new UserProfile(user.getId(), user.getEmail(), user.getUsername(), user.getName(), user.getLastName(), null);

        return userProfile;
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