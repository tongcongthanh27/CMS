package com.example.demo.service;

import com.example.demo.dto.request.PhonePrefix.PhonePrefixRequest;
import com.example.demo.dto.request.StatusUpdate;
import com.example.demo.dto.response.phonePrefix.PhonePrefixResponse;
import com.example.demo.entity.PhonePrefix;
import com.example.demo.enums.Status;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.PhonePrefixMapper;
import com.example.demo.repository.PhonePrefixRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor // thay the cho autowired
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PhonePrefixService {
    PhonePrefixRepository phonePrefixRepository;
    PhonePrefixMapper phonePrefixMapper;
    public PhonePrefixResponse creatPhonePrefix (PhonePrefixRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PhonePrefix phonePrefix = phonePrefixMapper.toPhonePrefix(request);
        if (phonePrefixRepository.existsByPhonePrefix(request.getPhonePrefix()))
            throw new AppException(ErrorCode.PHONE_PREFIX_EXISTED);
        phonePrefix.setStatus(Status.LOCKED);
        phonePrefix.setCreator(username);
        phonePrefix = phonePrefixRepository.saveAndFlush(phonePrefix);
        return phonePrefixMapper.toPhonePrefixResponse(phonePrefix);
    }
    public List<PhonePrefix> getAll(){
        return phonePrefixRepository.findAll();
    }

    public PhonePrefixResponse updatePhonePrefix(PhonePrefixRequest request
            ,String phonePrefix1 ){
        if(phonePrefixRepository.existsByPhonePrefix(request.getPhonePrefix()))
            throw new AppException(ErrorCode.PHONE_PREFIX_EXISTED);
        PhonePrefix phonePrefix = phonePrefixRepository.findByPhonePrefix(phonePrefix1)
                .orElseThrow(() -> new AppException(ErrorCode.PHONE_PREFIX_NOT_FOUND));
        phonePrefix.setPhonePrefix(request.getPhonePrefix());
        phonePrefixRepository.save(phonePrefix);
        return phonePrefixMapper.toPhonePrefixResponse(phonePrefix);
    }

    public void delete (Long id){
        phonePrefixRepository.deleteById(id);
    }

    public void updateStatus(Long id, @Valid StatusUpdate statusUpdate) {
        PhonePrefix phonePrefix = phonePrefixRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PHONE_PREFIX_NOT_FOUND));
        phonePrefix.setStatus(statusUpdate.getStatus());
    }
}
