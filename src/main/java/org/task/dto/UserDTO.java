package org.task.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message="Email is required")
    @Email(message="Invalid email")
    private String email;

    @NotEmpty(message="Password is required")
    private String password;

    @NotEmpty(message = "Name is required")
    private String name;
}