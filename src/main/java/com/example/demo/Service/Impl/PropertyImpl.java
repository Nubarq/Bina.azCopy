package com.example.demo.Service.Impl;

import com.example.demo.Dto.PropertyDto;
import com.example.demo.Dto.SortingDto;
import com.example.demo.Entity.Property;
import com.example.demo.Entity.User;
import com.example.demo.Repository.PropertyRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.JWTService;
import com.example.demo.Service.PropertyService;
import com.example.demo.Specification.PropertySpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import jakarta.persistence.criteria.Predicate;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PropertyImpl implements PropertyService {

    PropertyRepository propertyRepository;
    UserRepository userRepository;
    JWTService jwtService;
    ModelMapper modelMapper;

    @Override
    public Property addProperty(PropertyDto request, String token) {
        LocalDate date = LocalDate.now();
        LocalDate expirationDate = date.plusDays(7);

        String email = jwtService.extractUserEmail(token);
        var user = userRepository.findByEmail(email)
                .orElseThrow();

        var property = Property.builder()
                .is_active(true)
                .price(request.getPrice())
                .address(request.getAddress())
                .addedDate(date)
                .expirationDate(expirationDate)
                .buildingType(request.getBuilding_type())
                .floorNumber(request.getFloor_number())
                .totalFloors(request.getTotal_floors())
                .roomCount(request.getRoom_count())
                .area(request.getArea())
                .saleType(request.getSale_type())
                .constructionYear(request.getConstruction_year())
                .image(request.getImage())
                .user(user)
                .build();
        propertyRepository.save(property);
        user.setProperty_count(user.getProperty_count() + 1);
        userRepository.save(user);
        return property;
    }

    @Override
    public void deleteProperty(int property_id) {
        Property property = propertyRepository.findById(property_id)
                .orElse(null);
        if(property != null){
            User propertUser = property.getUser();
            if(propertUser != null && propertUser.getProperty_count() > 0){
                int newCount = propertUser.getProperty_count() - 1;
                propertUser.setProperty_count(newCount);
                userRepository.save(propertUser);
                propertyRepository.deleteById(property_id);
            }
        }

    }

    @Override
    public Property updateProperty(int property_id, PropertyDto request) {
        Property existingProperty = propertyRepository.findById(property_id)
                .orElseThrow(null);

        if(existingProperty != null){
            // Map non-null fields from PropertyDto to existing Property
            modelMapper.map(request, existingProperty);

            LocalDate newDate = LocalDate.now();
            LocalDate newExpirationDate = newDate.plusDays(7);

            existingProperty.setAddedDate(newDate);
            existingProperty.setExpirationDate(newExpirationDate);
            existingProperty.set_active(true);

            return propertyRepository.save(existingProperty);
        }
        return null;
    }

    /*
    @Override
    public Page<Property> getProperties(int page, int size) {
        int maxSize = 20;
        if(size > maxSize)
            size = maxSize;
        Pageable pageable = PageRequest.of(page, size);
        return propertyRepository.findAll(pageable);
    }
     */
    @Override
    public Page<Property> getPropertiesSorted(int page, int size, String field, String direction) {
        int maxSize = 20;
        if(size > maxSize)
            size = maxSize;
        Pageable pageable;
        if(direction.equalsIgnoreCase("asc")){
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field));
            return propertyRepository.findAll(pageable);
        }
        pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field));
        return propertyRepository.findAll(pageable);
    }

    @Override
    public Page<Property> getPropertiesSpecifSorted(int page, int size, SortingDto request){
        Pageable pageable =PageRequest.of(page, size);
        PropertySpecification specification = new PropertySpecification(request);
        return propertyRepository.findAll(specification, pageable);
    }


/*
    @Override
    public Page<Property> getPropertiesRoomCount(int page, int size, int room_count){
        Pageable pageable = PageRequest.of(page, size);
        return propertyRepository.findByRoomCount(room_count, pageable);
    }

    @Override
    public Page<Property> getPropertiesFloorNumber(int page, int size, int floor_number){
        Pageable pageable = PageRequest.of(page, size);
        return propertyRepository.findByFloorNumber(floor_number, pageable);
    }

    @Override
    public Page<Property> getPropertiesConstructionYear(int page, int size, int construction_year){
        Pageable pageable = PageRequest.of(page, size);
        return propertyRepository.findByConstructionYear(construction_year, pageable);
    }

    @Override
    public Page<Property> getPropertiesPriceRange(int page, int size, int lower_price, int upper_price, String direction){
        Pageable pageable;
        if(direction.equalsIgnoreCase("asc")){
            Sort sort = Sort.by(Sort.Direction.ASC, "price");
            pageable = PageRequest.of(page, size, sort);
            return propertyRepository.findByPriceBetween(lower_price, upper_price, pageable);
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "price");
        pageable = PageRequest.of(page, size, sort);
        return propertyRepository.findByPriceBetween(lower_price, upper_price, pageable);
    }

    @Override
    public Page<Property> getPropertiesArea(int page, int size, int lower_area, int higher_area,String direction){

        Pageable pageable;
        if(direction.equalsIgnoreCase("asc")){
            Sort sort = Sort.by(Sort.Direction.ASC, "area");
            pageable = PageRequest.of(page, size, sort);
            return propertyRepository.findByAreaBetween(lower_area, higher_area, pageable);
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "area");
        pageable = PageRequest.of(page, size, sort);
        return propertyRepository.findByAreaBetween(lower_area, higher_area, pageable);
    }

 */

}
