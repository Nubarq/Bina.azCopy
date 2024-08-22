package com.example.demo.Controller;

import com.example.demo.Dto.SortingDto;
import com.example.demo.Entity.Property;
import com.example.demo.Repository.PropertyRepository;
import com.example.demo.Service.PropertyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/properties")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MainPageController {
    PropertyService propertyService;
    PropertyRepository propertyRepository;

   /* @GetMapping("")
    public List<Property> getProperties( @RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        return propertyService.getProperties(page, size).getContent();
    }

    */

    @GetMapping("")
    public List<Property> getProperties( @RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                         @RequestBody SortingDto request ){
                                         //@PathVariable("field") String field,
                                         //@RequestParam(value = "direction", defaultValue = "asc") String direction) {
        String field = request.getField();
        String direction = request.getDirection();
        return propertyService.getPropertiesSorted(page, size, field, direction).getContent();
    }
}
