package controller;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class RegisterController {

    private static List<User> registeredUsers = new ArrayList<>();

    public String registerUser(String username, String password) {
        // Create a User object
        User newUser = new User(username, password);

        // Register the user
        if (registerUser(newUser)) {
            return "User registration successful!";
        } else {
            return "User registration failed. User already exists.";
        }
    }

    private static boolean registerUser(User user) {
        // Check if the user already exists
        if (registeredUsers.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            return false; // User already exists
        }

        // Register the user in memory
        registeredUsers.add(user);
        return true; // User registered successfully
    }
}
