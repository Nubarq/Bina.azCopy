package com.example.demo.Controller;

import com.example.demo.Entity.Property;
import com.example.demo.Repository.PropertyRepository;
import com.example.demo.Service.PropertyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MainPageController {
    PropertyService propertyService;

    @GetMapping("/properties")
    public List<Property> getProperties( @RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        return propertyService.getProperties(page, size).getContent();
    }
}
