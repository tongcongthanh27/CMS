package com.example.demo.dto.response.phonePrefix;

import com.example.demo.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data // tu dong tao ra getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhonePrefixResponse {
    Long id;
    String phonePrefix;
    private String creator;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createAt;
    Status status;

}
