package com.example.demo.Service;

import com.example.demo.Dto.PropertyDto;
import com.example.demo.Entity.Property;
import com.example.demo.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface PropertyService {

    Property addProperty(PropertyDto request, String token);

    void deleteProperty(int property_id);

    Property updateProperty(int property_id, PropertyDto request);

    Page<Property> getProperties(int page, int size);

    Page<Property> getPropertiesSorted(int page, int size, String field, String direction);
}
