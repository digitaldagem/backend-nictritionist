package com.nictritionist.backend.bff.dtos.requests;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class ResetPasswordDTO {

    @NotBlank String username;
    @NotBlank @Email String email;
    @NotBlank String newPassword;

}
