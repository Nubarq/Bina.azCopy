package com.example.demo.Service;

import com.example.demo.Dto.PropertyDto;
import com.example.demo.Dto.SortingDto;
import com.example.demo.Entity.Property;
import com.example.demo.Entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import jakarta.persistence.criteria.Predicate;


public interface PropertyService {

    Property addProperty(PropertyDto request, String token);

    void deleteProperty(int property_id);

    Property updateProperty(int property_id, PropertyDto request);

    Page<Property> getPropertiesSorted(int page, int size, String field, String direction);
/*
    Page<Property> getPropertiesRoomCount(int page, int size, int room_count);

    Page<Property> getPropertiesFloorNumber(int page, int size, int floor_number);

    Page<Property> getPropertiesConstructionYear(int page, int size, int construction_year);

    Page<Property> getPropertiesPriceRange(int page, int size, int lower_price, int upper_price, String direction);

    Page<Property> getPropertiesArea(int page, int size, int lower_area, int higher_area, String direction);
 */

    Page<Property> getPropertiesSpecifSorted(int page, int size, SortingDto request);
}
