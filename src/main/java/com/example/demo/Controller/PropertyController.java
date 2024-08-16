package com.example.demo.Controller;

import com.example.demo.Dto.PropertyDto;
import com.example.demo.Entity.Property;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.PropertyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/user")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PropertyController {

    PropertyService propertyService;
    UserRepository userRepository;

    @PostMapping("/{user_id}/add/properties")
    public ResponseEntity<String> addProperty(@PathVariable("user_id") int user_id, @RequestBody PropertyDto request){
        User user = userRepository.findById(user_id)
                .orElse(null);
        if(user != null) {
            if (user.getRole() == Role.VIP || (user.getRole() == Role.USER && user.getProperty_count() < 5)) {
                Property property = propertyService.addProperty(request, user);
                return ResponseEntity.ok("Property added successfully with the ID: {} " + property.getPropertyId());
            }
            else{
                return ResponseEntity.badRequest().body("You are only allowed to add 5 property. You need a VIP account to do this if you want proceed");
            }
        }
        return ResponseEntity.badRequest().body("User not found!");
    }

    @DeleteMapping("/delete/properties/{property_id}")
    public ResponseEntity<String> deleteProperty(@PathVariable("property_id") int property_id, @PathVariable("user_id") int user_id){
        propertyService.deleteProperty(property_id);

        return ResponseEntity.ok("Propert with the ID: {} was deleted." + property_id);
    }

    @PostMapping("/update/properties/{property_id}")
    public ResponseEntity<String> updateProperty(@PathVariable("property_id") int property_id, @RequestBody PropertyDto request){
        if(request == null){
            return ResponseEntity.badRequest().body("No changes made");
        }

        var property = propertyService.updateProperty(property_id, request);
        User user= property.getUser();
        if(property != null && user.getProperty_count() <= 5){
            return ResponseEntity.ok("Propert with the ID: {} was updated." + property_id );
        }
        else if(user.getProperty_count() > 5){
            return ResponseEntity.badRequest().body("You are only allowed to add 5 property. You need a VIP account to do this if you want proceed");
        }
        return ResponseEntity.badRequest().body("Propert with the ID: {} was not found" + property_id);

    }

}
