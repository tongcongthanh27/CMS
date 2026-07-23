package com.example.demo.service;

import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.UserStatusUpdate;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.PageResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.enums.AccountStatus;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor // thay the cho autowired
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    AuthenticationService authenticationService;
    public UserResponse createUser(UserCreationRequest request){
        User user = userMapper.toUser(request);
        if (userRepository. existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()) );
        user.setStatus(AccountStatus.LOCKED);
        user.setFailedOtp(0);
        user.setFailedPassword(0);

        return userMapper.toUserResponse(userRepository.save(user));
    }
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse myInfo(){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public PageResponse<UserResponse> getUsersWithPagination(Pageable pageable){
        Page<User> page = userRepository.findAll(pageable);
        List<UserResponse> userResponses = page.getContent()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
        
        return PageResponse.<UserResponse>builder()
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .content(userResponses)
                .build();
    }

    public PageResponse<UserResponse> searchUsers(String keyword, Pageable pageable){
        Page<User> page = userRepository.searchUsers(keyword, pageable);
        List<UserResponse> userResponses = page.getContent()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
        
        return PageResponse.<UserResponse>builder()
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .content(userResponses)
                .build();
    }

    public PageResponse<UserResponse> searchUsersAdvanced(String username, String fullName, String email, Pageable pageable){
        Page<User> page = userRepository.searchUsersAdvanced(username, fullName, email, pageable);
        List<UserResponse> userResponses = page.getContent()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
        
        return PageResponse.<UserResponse>builder()
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .content(userResponses)
                .build();
    }

    public UserResponse getUserByID(String id){
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public void deleteUser(String id){
         userRepository.deleteById(id);
    }

    public UserResponse updateUser(String id, UserUpdateRequest request){
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void updateStatus(String id, UserStatusUpdate userStatusUpdate){
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setStatus(userStatusUpdate.getAccountStatus());
        userRepository.save(user);
    }
}

