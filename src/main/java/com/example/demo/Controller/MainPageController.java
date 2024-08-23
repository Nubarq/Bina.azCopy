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
    public List<Property> getProperties(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                         @RequestBody SortingDto request){
        String field = request.getField();
        String direction = request.getDirection();
        return propertyService.getPropertiesSorted(page, size, field, direction).getContent();
    }
    /*

    @GetMapping("/roomcount")
    public List<Property> getPropertiesRoomCount (@RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                                  @RequestBody SortingDto request){
        int roomCount = request.getRoomCount();
        return propertyService.getPropertiesRoomCount(page, size, roomCount).getContent();
    }

    @GetMapping("/floornumber")
    public List<Property> getPropertiesFloorNumber (@RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                                  @RequestBody SortingDto request){
        int floorNumber = request.getFloorNumber();
        return propertyService.getPropertiesFloorNumber(page, size, floorNumber).getContent();
    }

    @GetMapping("/constructionyear")
    public List<Property> getPropertiesConstrucYear (@RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                                  @RequestBody SortingDto request){
        int constructionYear = request.getConstructionYear();
        return propertyService.getPropertiesConstructionYear(page, size, constructionYear).getContent();
    }

    @GetMapping("/price")
    public List<Property> getPropertiesPriceRange (@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size,
                                                     @RequestBody SortingDto request){
        int lower_price = request.getLower_price();
        int upper_price = request.getUpper_price();
        String direction = request.getDirection();
        return propertyService.getPropertiesPriceRange(page, size, lower_price, upper_price, direction).getContent();
    }

    @GetMapping("/area")
    public List<Property> getPropertiesArea (@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                   @RequestBody SortingDto request){
        int lower_area = request.getLower_area();
        int higher_area = request.getHigher_area();
        String direction = request.getDirection();
        return propertyService.getPropertiesArea(page, size, lower_area, higher_area, direction).getContent();
    }
     */

    @GetMapping("/sorted")
    public List<Property> getPropertiesSorted(@RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size,
                                              @RequestBody SortingDto request){

       return propertyService.getPropertiesSpecifSorted(page, size, request).getContent();
    }

}
