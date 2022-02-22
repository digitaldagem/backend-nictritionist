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
}
