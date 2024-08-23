package com.example.demo.Service;

import com.example.demo.dto.property.PropertyRequestDto;
import com.example.demo.dto.property.PropertyResponseDto;
import com.example.demo.dto.property.UpdatePropertyRequestDto;
import com.example.demo.model.Property;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PropertyService {
    String createProperty(PropertyRequestDto requestDto);
    String deleteProperty(Integer id);

    String updateProperty(Integer id, UpdatePropertyRequestDto updateRequestDto);

    Page<Property> getProperties(int page, int size);

    Page<Property> getPropertiesByRoomCount(int roomCount, int page, int size);
    Page<Property> getPropertiesByPriceRange(double minPrice, double maxPrice, int page, int size);

//    List<Property> findActiveProperties();

    List<Property> findProperties(Integer minPrice, Integer maxPrice, Integer minRooms);

    }
