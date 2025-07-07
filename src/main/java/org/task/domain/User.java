package org.task.domain;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Document(collection = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonIgnore
    private String password;

    private String name;

    @Column(unique = true)
    private String email;

    @NotNull
    private Date createdAt;

    public User(){

    }
    public User(String id, String password, String email, String name ) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
    }
}
