package com.example.demo.mapper;

import com.example.demo.dto.property.PropertyRequestDto;
import com.example.demo.dto.property.PropertyResponseDto;
import com.example.demo.dto.property.UpdatePropertyRequestDto;
import com.example.demo.model.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PropertyMapper {
    Property mapPropertyRequestDtoToEntity(PropertyRequestDto requestDto);
    @Mapping(target="userId", source="user.id")
    PropertyResponseDto mapEntityToPropertyResponsetDto(Property property);

//    @Mapping(target="user.id", source="userId")
    Property mapUpdatePropertyRequestDtoToEntity
    (UpdatePropertyRequestDto updateRequestDtorequestDto, @MappingTarget Property property);

    @Mapping(target="userId", source="user.id")
    PropertyResponseDto mapEntityToUpdatePropertyResponseDto
            (Property property, @MappingTarget PropertyResponseDto responseDto);
}
