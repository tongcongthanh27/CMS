package com.example.demo.service;

import com.example.demo.dto.request.role.AssignRoleRequest;
import com.example.demo.dto.request.role.RoleRequest;
import com.example.demo.dto.request.role.RoleUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.dto.response.role.RoleResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@RequiredArgsConstructor // thay the cho autowired
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service

public class RoleService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;
    UserMapper userMapper;
    public RoleResponse createRole(RoleRequest request){
        Role role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }
    public List<RoleResponse> getAllRoles(){
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }
    public void delete(String role) {
        roleRepository.deleteById(role);
    }

    public RoleResponse updateRole(RoleUpdateRequest request){
        var role = roleRepository.findById(request.getName())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        var permissions = permissionRepository.findAllById(request.getPermissions());
        if(permissions.size() != request.getPermissions().size())
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        role.setPermissions(new HashSet<>(permissions));
        roleMapper.updateRole(role, request);
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public UserResponse assignRole(String id, AssignRoleRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}
