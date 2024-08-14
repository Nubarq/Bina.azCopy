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
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PropertyImpl implements PropertyService {

    PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Override
    public String addProperty(PropertyDto request, int user_id) {

        User propertUser = userRepository.findById(user_id)
                .orElse(null);

        if(propertUser != null){
            if (propertUser.getRole() == Role.VIP || (propertUser.getRole() == Role.USER && propertUser.getProperty_count() < 5)){
                LocalDate date = LocalDate.now();

                var property = Property.builder()
                        .is_active(true)
                        .price(request.getPrice())
                        .address(request.getAddress())
                        .added_date(date)
                        .expiration_date(request.getExpiration_date())
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

                var savedProperty = propertyRepository.save(property);

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
        propertyRepository.deleteById(property_id);
    }
}
