package com.nictritionist.backend.bff.dtos.requests;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class LoginDTO {

    @NotBlank String username;
    @NotBlank String password;

}
