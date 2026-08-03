package com.example.demo.mapper;

import com.example.demo.dto.request.PhonePrefix.PhonePrefixRequest;
import com.example.demo.dto.response.phonePrefix.PhonePrefixResponse;
import com.example.demo.entity.PhonePrefix;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhonePrefixMapper {
    PhonePrefix toPhonePrefix(PhonePrefixRequest request);
    PhonePrefixResponse toPhonePrefixResponse(PhonePrefix phonePrefix);
}
