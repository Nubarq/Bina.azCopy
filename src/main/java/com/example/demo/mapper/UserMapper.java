package com.example.demo.mapper;

import com.example.demo.dto.user.request.CreateUserRequestDto;
import com.example.demo.dto.user.response.CreateUserResponseDto;
import com.example.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User mapCreateUserRequestDtoToUserEntity(CreateUserRequestDto createUserRequestDto);
    CreateUserResponseDto mapUserEntityToUserResponseDto(User userEntity);

}
