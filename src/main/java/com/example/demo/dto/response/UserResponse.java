package com.example.demo.dto.response;

import java.time.LocalDate;
import java.util.Set;

import com.example.demo.dto.response.role.RoleResponse;
import com.example.demo.enums.Status;
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
    LocalDate dob;
    String fullName;
    String email;
    String phone;
    Status status;
    Set<RoleResponse> roles;
}
