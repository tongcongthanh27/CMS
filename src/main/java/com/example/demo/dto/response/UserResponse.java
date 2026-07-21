package com.example.demo.dto.response;

import java.time.LocalDate;
import java.util.Set;

import com.example.demo.enums.AccountStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data // tu dong tao ra getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String password;
    LocalDate dob;
    String fullName;
    String email;
    String phone;
    AccountStatus status;
}
