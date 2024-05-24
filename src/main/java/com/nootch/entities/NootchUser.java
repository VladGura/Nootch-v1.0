package com.nootch.entities;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "users")
public class NootchUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private long password;

    private String email;

    @Nullable
    private String verificationCode;

    @Nullable
    private String HttpSessionId; //will fix bugs later

}
