package com.example.demo.entity;

import java.time.LocalDate;
import java.util.Set;

import com.example.demo.enums.AccountStatus;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data // tu dong tao ra getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String username;
    String password;
    String fullName;
    String email;
    LocalDate dob;
    Integer failedPassword;
    Integer failedOtp;
    String phone;

    @Enumerated(EnumType.STRING)
    AccountStatus status;

    @ManyToMany
    Set<Role> roles;
}
