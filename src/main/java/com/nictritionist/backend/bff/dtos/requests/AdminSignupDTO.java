package com.nictritionist.backend.bff.dtos.requests;

import lombok.Value;

import javax.validation.constraints.*;

@Value
public class AdminSignupDTO {

    @NotBlank
    @Size(max = 50)
    @Email String email;

    @NotBlank
    @Size(min = 6, max = 40) String password;

    @NotBlank
    @Size(max = 20) String firstName;

    @NotBlank
    @Size(max = 20) String lastName;

}
