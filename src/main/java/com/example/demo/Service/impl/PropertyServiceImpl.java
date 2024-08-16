package com.example.demo.Service.impl;

import com.example.demo.Exeption.CustomException;
import com.example.demo.Service.PropertyService;
import com.example.demo.dto.property.PropertyRequestDto;
import com.example.demo.dto.property.PropertyResponseDto;
import com.example.demo.dto.property.UpdatePropertyRequestDto;
import com.example.demo.mapper.PropertyMapper;
import com.example.demo.model.Property;
import com.example.demo.model.User;
import com.example.demo.model.UserType;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private  PropertyMapper propertyMapper;
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public String createProperty(PropertyRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> new CustomException("not found "+requestDto.getUserId()+" property"));
        if (user.getPropertyList().size()> 3){
            if(user.getUserType()== UserType.VIP){
                 Property property = propertyMapper.mapPropertyRequestDtoToEntity(requestDto);
                 property.setUser(user);
                 Property savedProperty = propertyRepository.save(property);
                  propertyMapper.mapEntityToPropertyResponsetDto(savedProperty);
                  return "Property is created succesfuly";
            }else {
                return "You need to be VIP to create 4th property";
            }
        }else {
            Property property = propertyMapper.mapPropertyRequestDtoToEntity(requestDto);
            property.setUser(user);
            Property savedProperty = propertyRepository.save(property);
            propertyMapper.mapEntityToPropertyResponsetDto(savedProperty);
            return "Property is created succesfuly";
        }
    }

    @Override
    public String deleteProperty(Integer id) {
        Property property=propertyRepository.findById(id).orElseThrow(() -> new CustomException("not found "+id+" property"));

        if (property.checkPropertyIsActive()){
            property.setActive(false);
            return "This property is not active anymore";
        }else {
            return "This property is already deleted";
        }
    }

    @Override
    public PropertyResponseDto updateProperty(Integer id,UpdatePropertyRequestDto updateRequestDto) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new CustomException("not found "+id+" property"));
        propertyMapper.mapUpdatePropertyRequestDtoToEntity(updateRequestDto,property);
        Property saved = propertyRepository.save(property);

        return propertyMapper.mapEntityToUpdatePropertyResponseDto(saved,new PropertyResponseDto());
    }

    @Override
    public Page<Property> getProperties(int page, int size) {
        int maxSize = 20;
        if(size > maxSize)
            size = maxSize;
        Pageable pageable = PageRequest.of(page, size);
        return propertyRepository.findAll(pageable);
    }


}
