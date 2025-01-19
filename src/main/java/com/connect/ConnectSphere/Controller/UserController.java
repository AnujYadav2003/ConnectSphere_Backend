////package com.connect.ConnectSphere.Controller;
////
////import com.connect.ConnectSphere.Repository.UserRepository;
////import com.connect.ConnectSphere.Service.UserService;
////import com.connect.ConnectSphere.model.User;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.web.bind.annotation.*;
////
////import java.util.List;
////import java.util.Optional;
////
////@RestController
//////@RequestMapping("/users")
////public class UserController {
////
////    @Autowired
////    private UserService userService;
////    @Autowired
////    private UserRepository userRepository;
////
////    @GetMapping("/")
////    public String solve() {
////        return "Welcome to the User Service!";
////    }
////
////    //    @PostMapping("/create")
//////    public User registerUser(@RequestBody User user) {
//////        return userService.registerUser(user);
//////    }
////    @GetMapping("/api/getusers")
////    public List<User> getAllUsers()
////    {
////        return userService.getAllUsers();
////    }
////
//////    @GetMapping("/getbyid/{id}")
//////    public User getUserById(@PathVariable Long id)
//////    {
//////        return userService.getUserById(id);
//////    }
////
////    @GetMapping("/getbyemail/{email}")
////    public Optional<User> getUserByEmail(@PathVariable String email)
////    {
////        return userService.getUserByEmail(email);
////    }
////
////    @DeleteMapping("/delete/{id}")
////    public String deleteUserById(@PathVariable Long id)
////    {
////        userService.deleteUserById(id);
////        return "Deleted successfully";
////    }
//////    @PostMapping("/updatebyid/{id}")
//////    public String updateUserInfo(@RequestBody User user, @PathVariable Long id) {
//////        try {
//////            userService.updateUser(user, id);
//////            return "Updated successfully";
//////        } catch (RuntimeException e) {
//////            return e.getMessage();
//////        }
//////    }
//////    @PutMapping("/followuser/user1/{id1}/user2/{id2}")
//////    public String followUser(@PathVariable Long id1, @PathVariable Long id2) {
//////        try {
//////            userService.followUser(id1, id2);
//////            return "Followed successfully";
//////        } catch (RuntimeException e) {
//////            return e.getMessage();
//////        }
//////    }
////
////    @GetMapping("/searchuser")
////    public List<User> searchUser(@RequestParam("query") String query) {
////        return userService.searchUser(query);
////    }
////
////
//////    --------------------
////
////    //update users
////    @PutMapping("/api/users/update")
////    public User updateUser(@RequestHeader("Authorization") String jwt, @RequestBody User user) {


import com.connect.ConnectSphere.Repository.UserRepository;
import com.connect.ConnectSphere.Service.UserService;
import com.connect.ConnectSphere.model.User;
import com.connect.ConnectSphere.model.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")  // Use base path to make routing cleaner
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    // Welcome endpoint
    @GetMapping("/")
    public ResponseEntity<Response<String>> solve() {
        return ResponseEntity.ok(new Response<>("Welcome to the User Service!", "success", null));
    }

    // Get all users
    @GetMapping("/getusers")
    public ResponseEntity<Response<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new Response<>("Users fetched successfully", "success", users));
    }

    // Get user by email
    @GetMapping("/getbyemail/{email}")
    public ResponseEntity<Response<User>> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok(new Response<>("User found", "success", user.get()));
        } else {
            return ResponseEntity.status(404).body(new Response<>("User not found", "error", null));
        }
    }

    // Delete user by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<String>> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok(new Response<>("User deleted successfully", "success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response<>(e.getMessage(), "error", null));
        }
    }

    // Update user profile
    @PutMapping("/update")
    public ResponseEntity<Response<User>> updateUser(@RequestHeader("Authorization") String jwt,@Valid @RequestBody User user) {
        try {
            User reqUser = userService.findUserByJwt(jwt);
            if (reqUser == null) {
                return ResponseEntity.status(401).body(new Response<>("Unauthorized access", "error", null));
            }
            User updatedUser = userService.updateUser(user, reqUser.getId());
            return ResponseEntity.ok(new Response<>("User updated successfully", "success", updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response<>(e.getMessage(), "error", null));
        }
    }

    // Follow a user
    @PutMapping("/follow/{id2}")
    public ResponseEntity<Response<User>> followUser(@RequestHeader("Authorization") String jwt, @PathVariable Long id2) {
        try {
            User reqUser = userService.findUserByJwt(jwt);
            if (reqUser == null) {
                return ResponseEntity.status(401).body(new Response<>("Unauthorized access", "error", null));
            }
            User user = userService.followUser(reqUser.getId(), id2);
            return ResponseEntity.ok(new Response<>("User followed successfully", "success", user));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response<>(e.getMessage(), "error", null));
        }
    }

    // Get user profile by JWT
    @GetMapping("/profile")
    public ResponseEntity<Response<User>> findUserByJwt(@RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwt(jwt);
            if (user == null) {
                return ResponseEntity.status(401).body(new Response<>("Unauthorized access", "error", null));
            }
            user.setPassword(null);  // Hide the password field
            return ResponseEntity.ok(new Response<>("User profile fetched successfully", "success", user));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response<>(e.getMessage(), "error", null));
        }
    }
}
