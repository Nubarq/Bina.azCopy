package com.example.demo.Service.Impl;

import com.example.demo.Dto.PropertyDto;
import com.example.demo.Entity.Property;
import com.example.demo.Entity.User;
import com.example.demo.Repository.PropertyRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.JWTService;
import com.example.demo.Service.PropertyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

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
                .added_date(date)
                .expirationDate(expirationDate)
                .buildingType(request.getBuilding_type())
                .floor_number(request.getFloor_number())
                .total_floors(request.getTotal_floors())
                .room_count(request.getRoom_count())
                .area(request.getArea())
                .sale_type(request.getSale_type())
                .construction_year(request.getConstruction_year())
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

            existingProperty.setAdded_date(newDate);
            existingProperty.setExpirationDate(newExpirationDate);
            existingProperty.set_active(true);

            return propertyRepository.save(existingProperty);
        }
        return null;
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
