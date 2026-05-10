package onlineshoppingsystem;

import java.util.Scanner;

public abstract class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    
    protected Scanner input = new Scanner(System.in);


    public User(String userId, String name, String email, String password) {

        // User ID Validation
        while (true) {
            try {
                if (userId == null || userId.isEmpty()) {
                    throw new Exception("User ID cannot be empty");
                }

                this.userId = userId;
                break;
            } 
            
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print("Enter User ID again: ");
                userId = input.nextLine();
            }
        }

        // Name Validation
        while (true) {
            try {
                if (name == null || name.isEmpty()) {
                    throw new Exception("Name cannot be empty");
                }

                this.name = name;
                break;
            } 
            
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print("Enter Name again: ");
                name = input.nextLine();
            }
        }

        // Email Validation
        while (true) {
            try {
                if (email == null || !email.contains("@")) {
                    throw new Exception("Invalid Email");
                }

                this.email = email;
                break;
            } 

            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print("Enter Email again: ");
                email = input.nextLine();
            }
        }

        // Password Validation
        while (true) {
            try {
                if (password == null || password.length() < 4) {
                    throw new Exception("Password must contain at least 4 characters");
                }

                this.password = password;
                break;
            } 
            
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print("Enter Password again: ");
                password = input.nextLine();
            }
        }
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
        while (true) {
            try {
                if (userId == null || userId.isEmpty()) {
                    throw new Exception("Invalid User ID");
                }

                this.userId = userId;
                break;

            } 
            
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print("Enter User ID again: ");
                userId = input.nextLine();
            }
        }
    }

    public void setName(String name) {
        while (true) {
            try {
                if (name == null || name.isEmpty()) {
                    throw new Exception("Invalid Name");
                }

                this.name = name;
                break;
            } 
            
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print("Enter Name again: ");
                name = input.nextLine();
            }
        }
    }

    public void setEmail(String email) {
        while (true) {
            try {
                if (email == null || !email.contains("@")) {
                    throw new Exception("Invalid Email");
                }

                this.email = email;
                break;
            } 
            
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print("Enter Email again: ");
                email = input.nextLine();
            }
        }
    }

    public void setPassword(String password) {
        while (true) {
            try {
                if (password == null || password.length() < 4) {
                    throw new Exception("Password must contain at least 4 characters");
                }

                this.password = password;
                break;
            } 
            
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print("Enter Password again: ");
                password = input.nextLine();
            }
        }
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
