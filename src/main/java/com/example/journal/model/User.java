package com.example.journal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String username;
    private String email;
    private String password; // encrypted

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}