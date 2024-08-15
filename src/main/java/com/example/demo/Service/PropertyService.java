package com.example.demo.Service;

import com.example.demo.Dto.PropertyDto;
import com.example.demo.Entity.Property;

public interface PropertyService {

    String addProperty(PropertyDto request, int user_id);

    void deleteProperty(int property_id, int user_id);

    Property updateProperty(int property_id, PropertyDto request);
}
