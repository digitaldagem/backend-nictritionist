package com.nictritionist.backend.bff.dtos.responses;

import lombok.Value;

import java.util.List;

@Value
public class JwtDTO {

    String token;
    String type = "Bearer";
    Long id;
    String username;
    String email;
    List<String> roles;

    public JwtDTO(String accessToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
