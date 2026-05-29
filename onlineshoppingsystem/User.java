package onlineshoppingsystem;

import java.io.Serializable;

public abstract class User implements Serializable{
    private String userId;
    private String name;
    private String email;
    private String password;

    public User(String userId, String name, String email, String password) {

        // User ID Validation
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        
        this.userId = userId;

        // Name Validation
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        this.name = name;

        // Email Validation
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid Email");
        }

        this.email = email;

        // Password Validation
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Password must contain at least 4 characters");
        }

        this.password = password;
    }

    // Accessors(Getters)
    public String getUserId() { 
        return userId; 
    }

    public String getName() { 
        return name; 
    }

    public String getEmail() { 
        return email; 
    }

    public String getPassword() { 
        return password; 
    }

    // Mutators (Setters)
    public void setUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("Invalid User ID");
        }

        this.userId = userId;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid Name");
        }

        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid Email");
        }

        this.email = email;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Password must contain at least 4 characters");
        }

                this.password = password;
    }

    public void display() {
        System.out.println("User ID: " + userId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
    }

    // Abstract method to be implemented by subclasses
    public abstract String displayRole();
}
