package com.example.demo.dto.request;

import com.example.demo.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data // tu dong tao ra getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusUpdate {
    Status status;
}
