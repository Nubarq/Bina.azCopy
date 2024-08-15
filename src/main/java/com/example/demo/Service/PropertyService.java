package com.example.demo.Service;

import com.example.demo.Dto.PropertyDto;
import com.example.demo.Entity.Property;
import org.springframework.data.domain.Page;

public interface PropertyService {

    String addProperty(PropertyDto request, int user_id);

    void deleteProperty(int property_id, int user_id);

    Property updateProperty(int property_id, PropertyDto request);

    Page<Property> getProperties(int page, int size);
}
