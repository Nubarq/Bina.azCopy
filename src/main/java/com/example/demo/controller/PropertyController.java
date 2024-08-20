package com.example.demo.controller;

import com.example.demo.Service.PropertyService;
import com.example.demo.dto.property.PropertyRequestDto;
import com.example.demo.dto.property.PropertyResponseDto;
import com.example.demo.dto.property.UpdatePropertyRequestDto;
import com.example.demo.model.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/property")
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping("/createProperty")
    public String createProperty(@RequestBody PropertyRequestDto requestDto) {
        return propertyService.createProperty(requestDto);
    }
    @DeleteMapping("/deleteProperty")
    public String deleteProperty(@RequestParam Integer id) {
        return propertyService.deleteProperty(id);
    }

    @PutMapping("/updateProperty")
    public String updateProperty(@PathVariable Integer id, @RequestBody UpdatePropertyRequestDto updateRequestDto) {
        return propertyService.updateProperty(id,updateRequestDto);
    }

    @GetMapping("/getProperties")
    public Page<Property> getProperties(@RequestParam int page,@RequestParam int size) {
        return propertyService.getProperties(page,size);
    }

    }
