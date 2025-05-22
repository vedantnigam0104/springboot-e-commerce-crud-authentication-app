package com.vedant.e_commerce.app.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;  // username field for login

    @Column(nullable = false, unique = true)
    private String email;     // email field, also unique

    @Column(nullable = false)
    private String password;

    private String name;

    private String role = "USER"; // default role

    // Implement methods from UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can add role-based authorities here if needed
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return username; // this method returns username, used by Spring Security
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // account is valid (not expired)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // account is not locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // credentials are valid
    }

    @Override
    public boolean isEnabled() {
        return true; // user is enabled
    }
}
