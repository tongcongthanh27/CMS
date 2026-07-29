package com.example.demo.dto.request.role;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data // tu dong tao ra getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionRequest{
    String name;
    String description;

}
