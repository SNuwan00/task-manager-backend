package com.example.task_manager_backend.service;

import com.example.task_manager_backend.dto.response.UserDitailsResponseDTO;
import com.example.task_manager_backend.model.User;
import com.example.task_manager_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public User signup(User user) {
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User updateProfile(Long id, User updatedUser, MultipartFile profilePhoto) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if (updatedUser.getUsername() != null) {
            user.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null) {
            user.setPassword(updatedUser.getPassword());
        }
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            // Delete old photo if exists
            fileStorageService.deleteFile(user.getProfilePhotoPath());
            // Save new photo
            String newPhotoPath = fileStorageService.storeFile(profilePhoto);
            user.setProfilePhotoPath(newPhotoPath);
        }

        return userRepository.save(user);
    }

    public UserDitailsResponseDTO mapToUserDetailsResponseDTO(User user) {
        UserDitailsResponseDTO dto = new UserDitailsResponseDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setProfilePhotoPath(user.getProfilePhotoPath());
        return dto;
    }

    public User getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }
}