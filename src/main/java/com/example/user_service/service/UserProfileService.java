package com.example.user_service.service;

import com.example.user_service.model.UserProfile;
import com.example.user_service.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository repository;

    public List<UserProfile> getAllUserProfiles() {
        return repository.findAll();
    }

    public Optional<UserProfile> getUserProfileById(UUID id) {
        return repository.findById(id);
    }

    public UserProfile createUserProfile(UserProfile profile) {
        return repository.save(profile);
    }

    public UserProfile updateUserProfile(UUID id, UserProfile updatedProfile) {
        return repository.findById(id)
                .map(profile -> {
                    profile.setFirstName(updatedProfile.getFirstName());
                    profile.setLastName(updatedProfile.getLastName());
                    profile.setPhone(updatedProfile.getPhone());
                    profile.setAddress(updatedProfile.getAddress());
                    return repository.save(profile);
                })
                .orElseGet(() -> {
                    updatedProfile.setId(id);
                    return repository.save(updatedProfile);
                });
    }

    public void deleteUserProfile(UUID id) {
        repository.deleteById(id);
    }
}
