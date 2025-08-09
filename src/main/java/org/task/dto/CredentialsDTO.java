package org.task.dto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class CredentialsDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String email;
    private String password;
}
