package com.example.demo.Service;

import com.example.demo.Dto.PropertyDto;
import com.example.demo.Entity.Property;
import com.example.demo.Entity.User;
import org.springframework.data.domain.Page;

public interface PropertyService {

    Property addProperty(PropertyDto request, String token);

    void deleteProperty(int property_id);

    Property updateProperty(int property_id, PropertyDto request);

    Page<Property> getProperties(int page, int size);
}
