package com.vedant.e_commerce.app.dtos;

import lombok.Data;

@Data
public class AuthRequest {
    private String Username;
    private String email;
    private String password;
}
