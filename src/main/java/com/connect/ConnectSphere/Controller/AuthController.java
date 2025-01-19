package com.connect.ConnectSphere.Controller;

import com.connect.ConnectSphere.Repository.UserRepository;
import com.connect.ConnectSphere.Service.CustomerUserDetailsService;
import com.connect.ConnectSphere.Service.UserService;
import com.connect.ConnectSphere.config.JwtProvider;
import com.connect.ConnectSphere.model.User;
import com.connect.ConnectSphere.model.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    // Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<Response<AuthResponse>> registerUser(@Valid @RequestBody User user) {
        try {
            // Check if email already exists
            Optional<User> isExist = userRepository.findByEmail(user.getEmail());
            if (isExist.isPresent()) {
                return ResponseEntity.status(400).body(new Response<>("Email already exists", "error", null));
            }

            // Create and save new user
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());

            User savedUser = userRepository.save(newUser);

            // Generate JWT token
            Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
            String token = JwtProvider.generateToken(authentication);

            // Return success response
            AuthResponse authResponse = new AuthResponse(token, "Registered Successfully");
            return ResponseEntity.ok(new Response<>("Registration successful", "success", authResponse));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response<>(e.getMessage(), "error", null));
        }
    }

    // Signin endpoint
    @PostMapping("/signin")
    public ResponseEntity<Response<AuthResponse>> signin(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            String token = JwtProvider.generateToken(authentication);

            // Return success response
            AuthResponse authResponse = new AuthResponse(token, "Login Successful");
            return ResponseEntity.ok(new Response<>("Login successful", "success", authResponse));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new Response<>("Invalid username or password", "error", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response<>(e.getMessage(), "error", null));
        }
    }

    // Authentication logic
    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
