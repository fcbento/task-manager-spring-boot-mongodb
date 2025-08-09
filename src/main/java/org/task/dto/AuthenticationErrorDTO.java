package org.task.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class AuthenticationErrorDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String error;
    private String message;
    private HttpStatus status;

    public AuthenticationErrorDTO(String error, String message, HttpStatus status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }
}
