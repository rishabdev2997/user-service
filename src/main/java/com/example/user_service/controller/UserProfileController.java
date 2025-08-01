package com.example.user_service.controller;

import com.example.user_service.model.UserProfile;
import com.example.user_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService service;

    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUserProfiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserById(@PathVariable UUID id, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String currentUserId = jwt.getClaimAsString("userId");
        String role = jwt.getClaimAsString("role");
        boolean isAdmin = role != null && role.equalsIgnoreCase("ADMIN");

        // ADMIN: can fetch any user; USER: can fetch themselves
        if (isAdmin || (currentUserId != null && id.toString().equals(currentUserId))) {
            return service.getUserProfileById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping
    public ResponseEntity<UserProfile> createUser(@RequestBody UserProfile userProfile) {
        UserProfile saved = service.createUserProfile(userProfile);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateUser(@PathVariable UUID id, @RequestBody UserProfile userProfile) {
        UserProfile updated = service.updateUserProfile(id, userProfile);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        service.deleteUserProfile(id);
        return ResponseEntity.noContent().build();
    }
}
