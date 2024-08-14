package com.example.demo.Service;

import com.example.demo.Dto.PropertyDto;
import com.example.demo.Entity.Property;

public interface PropertyService {

    Property addProperty(PropertyDto request);
}
