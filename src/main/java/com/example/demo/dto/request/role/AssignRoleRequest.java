package com.example.demo.dto.request.role;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data // tu dong tao ra getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignRoleRequest {
    Set<String> roles;
}
