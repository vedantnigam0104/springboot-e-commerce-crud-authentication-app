package com.vedant.e_commerce.app.Controller;

import com.vedant.e_commerce.app.Services.UserService;
import com.vedant.e_commerce.app.dtos.AuthRequest;
import com.vedant.e_commerce.app.dtos.AuthResponse;
import com.vedant.e_commerce.app.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    // Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        boolean isCreated = userService.registerUser(user);
        if (isCreated) {
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.badRequest().body("Username is already taken");
        }
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        String token = userService.loginUser(authRequest);
        if (token == null) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
