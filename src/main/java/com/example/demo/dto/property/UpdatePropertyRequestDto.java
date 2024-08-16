package com.example.demo.dto.property;

import com.example.demo.model.PropertyType;
import com.example.demo.model.PurchaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePropertyRequestDto {
    private String adress;
    private Integer price;
    private PropertyType propertyType;
    private Integer area;
    private PurchaseType purchaseType;
    private Integer roomCount;
    private Integer floorNumber;
}
