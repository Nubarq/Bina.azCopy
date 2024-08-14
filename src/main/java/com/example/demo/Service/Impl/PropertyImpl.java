package com.example.demo.Service.Impl;

import com.example.demo.Dto.PropertyDto;
import com.example.demo.Entity.Property;
import com.example.demo.Repository.PropertyRepository;
import com.example.demo.Service.PropertyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PropertyImpl implements PropertyService {

    PropertyRepository propertyRepository;

    @Override
    public Property addProperty(PropertyDto request) {

        LocalDate date = LocalDate.now();

        var property = Property.builder()
                .price(request.getPrice())
                .address(request.getAddress())
                .added_date(date)
                .expiration_date(request.getExpiration_date())
                .buildingType(request.getBuilding_type())
                .floor_number(request.getFloor_number())
                .total_floors(request.getTotal_floors())
                .room_count(request.getRoom_count())
                .square_meter_area(request.getSquare_meter_area())
                .sale_type(request.getSale_type())
                .construction_year(request.getConstruction_year())
                .image(request.getImage())
                .build();

        var savedProperty = propertyRepository.save(property);
        return savedProperty;
    }
}
