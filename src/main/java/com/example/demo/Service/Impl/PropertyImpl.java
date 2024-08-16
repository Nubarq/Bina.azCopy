package com.example.demo.Service.Impl;

import com.example.demo.Dto.PropertyDto;
import com.example.demo.Entity.Property;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Repository.PropertyRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.PropertyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PropertyImpl implements PropertyService {

    PropertyRepository propertyRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;
    private final View error;


    @Override
    public String addProperty(PropertyDto request, int user_id) {

        User propertUser = userRepository.findById(user_id)
                .orElse(null);

        if(propertUser != null){
            if (propertUser.getRole() == Role.VIP || (propertUser.getRole() == Role.USER && propertUser.getProperty_count() < 5)){
                LocalDate date = LocalDate.now();
                LocalDate expirationDate = date.plusDays(7);
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
                        .user(propertUser)
                        .build();

                propertyRepository.save(property);

                propertUser.setProperty_count(propertUser.getProperty_count() + 1);
                userRepository.save(propertUser);

                return "Property added successfully";
            }
            else{
                return "You are only allowed to add 5 property. You need a VIP account to do this if you want proceed";
            }
        }
        return "User not found!";
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
        Property property = propertyRepository.findByPropertyId(property_id)
                .orElse(null);

        if(property != null && property.is_active()){
            modelMapper.map(request, property);

            return propertyRepository.save(property);
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
