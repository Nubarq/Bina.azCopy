package com.example.demo.Controller;

import com.example.demo.Dto.PropertyDto;
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


    @PostMapping("/{user_id}/add/properties")
    public ResponseEntity<String> addProperty(@PathVariable("user_id") int user_id, @RequestBody PropertyDto request){
       return ResponseEntity.ok(propertyService.addProperty(request, user_id));
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
        if(property != null){
            return ResponseEntity.ok("Propert with the ID: {} was updated." + property_id );
        }
        return ResponseEntity.badRequest().body("Propert with the ID: {} was not found" + property_id);

    }

}
