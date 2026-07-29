package com.example.demo.mapper;

import com.example.demo.dto.request.role.PermissionRequest;
import com.example.demo.dto.response.role.PermissionResponse;
import com.example.demo.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // bao de gen ra dung cho spring
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}