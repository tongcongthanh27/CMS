package com.example.demo.dto.request.PhonePrefix;

import com.example.demo.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data // tu dong tao ra getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhonePrefixRequest {
    @NotBlank(message = "Đầu số không được để trống")
    @Size(max = 10, message = "Đầu số không được vượt quá 10 ký tự")
    @Pattern(regexp = "^[0-9]+$", message = "Đầu số chỉ được chứa ký tự số") // Đảm bảo người dùng không nhập chữ
    String phonePrefix;
}
