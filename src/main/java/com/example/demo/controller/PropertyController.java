package com.example.demo.controller;

import com.example.demo.Service.PropertyService;
import com.example.demo.dto.property.PropertyRequestDto;
import com.example.demo.dto.property.PropertyResponseDto;
import com.example.demo.dto.property.UpdatePropertyRequestDto;
import com.example.demo.model.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getProperties/ByRoomCount")
    public Page<Property> getPropertiesByRoomCount(@RequestParam int roomCount,
                                                   @RequestParam int page,
                                                   @RequestParam int size) {
        return propertyService.getPropertiesByRoomCount(roomCount,page,size);
    }

    @GetMapping("/getProperties/ByPriceRange")
    public Page<Property> getPropertiesByPriceRange(@RequestParam double minPrice,
                                                    @RequestParam double maxPrice,
                                                    @RequestParam int page,
                                                    @RequestParam int size) {
        return propertyService.getPropertiesByPriceRange(minPrice,maxPrice,page,size);
    }

//    @GetMapping("/ActiveProperties")
//    public List<Property> getActiveProperties() {
//        return propertyService.findActiveProperties();
//    }

    @GetMapping("/properties")
    public Page<Property> getProperties(
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer minRooms,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return propertyService.findProperties(minPrice, maxPrice, minRooms, page, size);
    }

    }
