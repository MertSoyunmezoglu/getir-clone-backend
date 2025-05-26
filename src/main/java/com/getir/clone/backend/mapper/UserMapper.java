package com.getir.clone.backend.mapper;

import com.getir.clone.backend.dto.request.UpdateUserRequest;
import com.getir.clone.backend.dto.response.UserDTO;
import com.getir.clone.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    void updateUserFromDto(UpdateUserRequest request, @MappingTarget User user);

}
