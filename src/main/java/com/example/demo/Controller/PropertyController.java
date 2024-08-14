package com.example.demo.Controller;

import com.example.demo.Dto.PropertyDto;
import com.example.demo.Entity.Property;
import com.example.demo.Entity.User;
import com.example.demo.Repository.PropertyRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.PropertyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/property")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PropertyController {

    PropertyService propertyService;
    UserRepository userRepository;
    PropertyRepository propertyRepository;


    @PostMapping("/{user_id}/add")
    public ResponseEntity<Property> addProperty(@PathVariable("user_id") int user_id, @RequestBody PropertyDto request){
        Property property = propertyService.addProperty(request);

        User user = userRepository.findById(user_id)
                .orElse(null);
        property.setUser(user);
        Property savedProperty = propertyRepository.save(property);

        return ResponseEntity.ok(savedProperty);
    }

}
