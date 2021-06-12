package com.nictritionist.backend.storage.user;

import com.nictritionist.backend.bff.dtos.responses.UserDTO;
import com.nictritionist.backend.storage.user.role.Role;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password, ArrayList<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserDTO getUserDTO() {
        return new UserDTO(
                id,
                username,
                email,
                roles.stream()
                        .map(role -> role.getName().toString())
                        .collect(Collectors.toList()));
    }

}
