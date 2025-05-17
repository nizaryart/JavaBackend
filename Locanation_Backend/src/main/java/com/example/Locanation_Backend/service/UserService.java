package com.example.Locanation_Backend.service;

import com.example.Locanation_Backend.model.User;
import com.example.Locanation_Backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User findById(int id) {
        Optional<User> user = userRepo.findById(id);
        return user.orElse(null);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User save(User user) {
        // You might want to add validation or business logic here
        return userRepo.save(user);
    }

    public void deleteById(int id) {
        userRepo.deleteById(id);
    }

    public List<User> searchUsers(String login, String role) {
        return userRepo.searchUsers(login, role);
    }

    // Additional business logic methods could be added here
    public boolean validateUserCredentials(String login, String password) {
        // Implementation would depend on your security requirements
        return true;
    }
}