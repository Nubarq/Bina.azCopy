package com.example.demo.Dto;

import com.example.demo.Entity.BuildingType;
import com.example.demo.Entity.SaleType;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto {

    private long property_id;
    private Double price;
    private String address;
    private SaleType sale_type;
    private BuildingType building_type;
    private Integer room_count;
    private Integer floor_number;
    private Integer total_floors;
    private String image;
    private Integer construction_year;
    private Integer area;
    private LocalDate expiration_date;
    private LocalDate added_date;
    private boolean is_active;
}
