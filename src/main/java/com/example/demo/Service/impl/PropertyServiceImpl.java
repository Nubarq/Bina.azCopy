package com.example.demo.Service.impl;

import com.example.demo.Exeption.CustomException;
import com.example.demo.Service.PropertyService;
import com.example.demo.Spesification.PropertySpecification;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new CustomException("not found "+requestDto.getUserId()+" property"));
        if (user.getProperty_count()> 3){
            if(user.getUserType()== UserType.VIP){
                 Property property = propertyMapper.mapPropertyRequestDtoToEntity(requestDto);
                 property.setUser(user);
                 Property savedProperty = propertyRepository.save(property);
                  propertyMapper.mapEntityToPropertyResponsetDto(savedProperty);
                  user.setProperty_count(user.getProperty_count()+1);
                  userRepository.save(user);
                  return "Property is created succesfuly";
            }else {
                return "You need to be VIP to create 4th property";
            }
        }else {
            Property property = propertyMapper.mapPropertyRequestDtoToEntity(requestDto);
            property.setUser(user);
            Property savedProperty = propertyRepository.save(property);
            propertyMapper.mapEntityToPropertyResponsetDto(savedProperty);
            user.setProperty_count(user.getProperty_count()+1);
            userRepository.save(user);
            return "Property is created succesfuly";
        }
    }

    @Override
    public String deleteProperty(Integer id) {
        Property property=propertyRepository.findById(id)
                .orElseThrow(() -> new CustomException("not found "+id+" property"));
        User user = property.getUser();
        if (property.checkPropertyIsActive()){
            property.setActive(false);
            user.setProperty_count(user.getProperty_count()-1);
            userRepository.save(user);
            return "This property is not active anymore";
        }else {
            return "This property is already deleted";
        }
    }

    @Override
    public String updateProperty(Integer id,UpdatePropertyRequestDto updateRequestDto) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new CustomException("not found "+id+" property"));
        User user = property.getUser();
        if (user.getProperty_count()>3){
            if(user.getUserType()== UserType.VIP){
                property.setActive(true);
                property.setCreatedAt(new Date());
                propertyMapper.mapUpdatePropertyRequestDtoToEntity(updateRequestDto,property);
                Property saved = propertyRepository.save(property);
                user.setProperty_count(user.getProperty_count()+1);
                userRepository.save(user);
                PropertyResponseDto responseDto=propertyMapper
                        .mapEntityToUpdatePropertyResponseDto(saved,new PropertyResponseDto());
                return "Updated the property: " + id +" TO ->  " + responseDto;
            }else {
                return "the property: "+ id+" can not be updated as it is your 4th active property";
            }
        }else {
            property.setActive(true);
            property.setCreatedAt(new Date());
            propertyMapper.mapUpdatePropertyRequestDtoToEntity(updateRequestDto,property);
            Property saved = propertyRepository.save(property);
            user.setProperty_count(user.getProperty_count()+1);
            userRepository.save(user);
            PropertyResponseDto responseDto=propertyMapper
                    .mapEntityToUpdatePropertyResponseDto(saved,new PropertyResponseDto());
            return "Updated the property: " + id +" TO ->  " + responseDto;
        }
    }

    @Override
    public Page<Property> getProperties(int page, int size) {
        int maxSize = 10;
        if(size > maxSize)
            size = maxSize;
        Sort sort = Sort.by(Sort.Direction.ASC, "price");
//        Pageable pageable = PageRequest.of(page, size);
        Pageable pageable = PageRequest.of(page,size,sort);
        return propertyRepository.findAll(pageable);

    }

    public Page<Property> getPropertiesByRoomCount(int roomCount, int page, int size) {
        int maxSize = 10;
        if (size > maxSize)
            size = maxSize;
        Pageable pageable = PageRequest.of(page, size);
        return propertyRepository.findByRoomCount(roomCount, pageable);
    }

    public Page<Property> getPropertiesByPriceRange(double minPrice, double maxPrice, int page, int size) {
        int maxSize = 10;
        if (size > maxSize)
            size = maxSize;
        Pageable pageable = PageRequest.of(page, size);
        return propertyRepository.findByPriceBetween(minPrice, maxPrice, pageable);
    }


    public List<Property> findProperties(Integer minPrice, Integer maxPrice, Integer minRooms) {
        PropertySpecification spec = new PropertySpecification(minPrice, maxPrice, minRooms);
        return propertyRepository.findAll(spec);
    }

    }
