package com.nictritionist.backend.bff.dtos.responses;

import lombok.Value;

import java.util.List;

@Value
public class UserDTO {
    Long userId;
    String username;
    String email;
    List<String> roles;
}
