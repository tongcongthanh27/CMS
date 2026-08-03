package com.example.demo.controller;

import com.example.demo.dto.request.ApiResponse;
import com.example.demo.dto.request.PhonePrefix.PhonePrefixRequest;
import com.example.demo.dto.request.StatusUpdate;
import com.example.demo.dto.response.phonePrefix.PhonePrefixResponse;
import com.example.demo.entity.PhonePrefix;
import com.example.demo.service.PhonePrefixService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phone-prefixs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PhonePrefixController {
    PhonePrefixService phonePrefixService;

    @PostMapping
    ApiResponse<PhonePrefixResponse> create (@RequestBody @Valid PhonePrefixRequest request){
        return ApiResponse.<PhonePrefixResponse>builder()
                .result(phonePrefixService.creatPhonePrefix(request))
                .message("Đầu số được tạo thành công")
                .build();
    }
    @GetMapping
    ApiResponse<List<PhonePrefix>> getAll(){
        return ApiResponse.<List<PhonePrefix>>builder()
                .result(phonePrefixService.getAll())
                .build();
    }
    @PutMapping("/{namePhonePrefix}")
    ApiResponse<PhonePrefixResponse> update(@PathVariable String namePhonePrefix,
                                            @RequestBody PhonePrefixRequest request){
        return ApiResponse.<PhonePrefixResponse>builder()
                .result(phonePrefixService.updatePhonePrefix(request,namePhonePrefix))
                .message("cập nhật đầu số thành công")
                .build();
    }
    @DeleteMapping("/{id}")
    ApiResponse<Void> delete (@PathVariable Long id){
        return ApiResponse.<Void>builder()
                .message("xóa đầu số thành công")
                .build();
    }
    @PutMapping("/{id}/status")
    public ApiResponse<Void> updateStatus(@PathVariable Long id, @RequestBody @Valid StatusUpdate statusUpdate){
        phonePrefixService.updateStatus(id, statusUpdate);
        return ApiResponse.<Void>builder()
                .message("cập nhật thành công")
                .build();
    }

}
