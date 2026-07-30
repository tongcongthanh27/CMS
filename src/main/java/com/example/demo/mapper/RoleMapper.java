package com.example.demo.mapper;

import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.request.role.RoleRequest;
import com.example.demo.dto.request.role.RoleUpdateRequest;
import com.example.demo.dto.response.role.RoleResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring") // bao de gen ra dung cho spring
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "permissions", ignore = true)
    void updateRole(@MappingTarget Role role, RoleUpdateRequest request);
}