package com.example.demo.Dto;

import com.example.demo.Entity.BuildingType;
import com.example.demo.Entity.SaleType;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto {

    private long property_id;
    private double price;
    private String address;
    private SaleType sale_type;
    private BuildingType building_type;
    private int room_count;
    private int floor_number;
    private int total_floors;
    private String image;
    private int construction_year;
    private int area;
}
