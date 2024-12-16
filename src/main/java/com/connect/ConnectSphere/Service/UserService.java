package com.connect.ConnectSphere.Service;

import com.connect.ConnectSphere.Repository.UserRepository;
import com.connect.ConnectSphere.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register a new user
//    public User registerUser(User user) {
//        User newUser = new User();
//        newUser.setEmail(user.getEmail());
//        newUser.setPassword(user.getPassword());
//        newUser.setFirstName(user.getFirstName());
//        newUser.setLastName(user.getLastName());
//        return userRepository.save(newUser);
//    }

    // Get all users
    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            throw new RuntimeException("No users available.");
        }
        return allUsers;
    }

    // Get user by ID
//    public User getUserById(Long id) {
//        return Optional.ofNullable(userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User with ID " + id + " does not exist.")));
//
//    }
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " does not exist."));
    }


    // Get user by email
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " does not exist.")));
    }

    // Delete a user by ID
    public void deleteUserById(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " does not exist."));
        userRepository.delete(existingUser);
    }

    // Update user info
    public User updateUser(User user, Long id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser != null) {
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());

            return userRepository.save(existingUser);
        }
        else {
            throw new RuntimeException("User with ID " + id + " does not exist.");
        }
    }

    // Follow a user
    // Follow a user
//    public User followUser(Long id1, Long id2) {
//        // Retrieve users by ID
//        User user1 = getUserById(id1).orElseThrow(() -> new RuntimeException("User with ID " + id1 + " does not exist."));
//        User user2 = getUserById(id2).orElseThrow(() -> new RuntimeException("User with ID " + id2 + " does not exist."));
//
//        // Check if user1 is already following user2
//        if (user2.getFollowers().contains(user1.getId())) {
//            throw new RuntimeException("User " + id1 + " is already following User " + id2);
//        }
//
//        // Add user1's ID to user2's followers and user2's ID to user1's followings
//        user2.getFollowers().add(user1.getId());  // Storing the ID, not the User object
//        user1.getFollowings().add(user2.getId()); // Storing the ID, not the User object
//
//        // Save updated user data
//        userRepository.save(user1);
//        userRepository.save(user2);
//
//        return user1;
//    }

    public User followUser(Long reqUserId, Long id2) {
        // Retrieve users by their IDs
        User user1 = getUserById(reqUserId); // Already throws exception if not found
        User user2 = getUserById(id2); // Already throws exception if not found

        // Check if user1 is already following user2
        if (user2.getFollowers().contains(reqUserId)) {
            throw new RuntimeException("User " + reqUserId + " is already following User " + id2);
        }

        // Add user1's ID to user2's followers and user2's ID to user1's followings
        user2.getFollowers().add(reqUserId); // Use reqUserId directly (already a Long ID)
        user1.getFollowings().add(id2); // Use id2 directly (already a Long ID)

        // Save updated user data
        userRepository.save(user1);
        userRepository.save(user2);

        return user1; // Return the updated user1 object
    }



    // Search user by query
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }




    public User   findUserByJwt(String jwt) {
        String email = com.connect.ConnectSphere.config.JwtProvider.getEmailFromJwtToken(jwt);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        System.out.println(optionalUser);
        return optionalUser.orElse(null);

    }

}
