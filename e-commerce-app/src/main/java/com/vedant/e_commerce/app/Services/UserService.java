package com.vedant.e_commerce.app.Services;

import com.vedant.e_commerce.app.Repository.UserRepo;
import com.vedant.e_commerce.app.dtos.AuthRequest;
import com.vedant.e_commerce.app.entities.User;
import com.vedant.e_commerce.app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean registerUser(User user1) {
        if (userRepository.findByUsername(user1.getUsername()) != null) {
            return false; // Username already exists
        }
        user1.setPassword(passwordEncoder.encode(user1.getPassword()));
        userRepository.save(user1);
        return true;
    }

    public String loginUser(AuthRequest authRequest) {
        User user1 = null;

        // Try login using username
        if (authRequest.getUsername() != null && !authRequest.getUsername().isEmpty()) {
            user1 = userRepository.findByUsername(authRequest.getUsername());
        }

        // Fallback to login using email
        if (user1 == null && authRequest.getEmail() != null && !authRequest.getEmail().isEmpty()) {
            user1 = userRepository.findByEmail(authRequest.getEmail());
        }

        // Validate password
        if (user1 == null || !passwordEncoder.matches(authRequest.getPassword(), user1.getPassword())) {
            return null; // Invalid credentials
        }

        return jwtUtil.generateToken(user1);
    }

    // Implementation for UserDetailsService interface
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }
}

