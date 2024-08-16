package com.example.demo.Mapper;

import com.example.demo.Dto.UserDto;
import com.example.demo.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface  UserMapper {
    User mapUserDtoToUserEntity(UserDto userDto);
    UserDto mapUserEntityToUserDto(User user);
}
