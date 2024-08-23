package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SortingDto {
    private String field;
    private String direction;
    private int roomCount;
    private int floorNumber;
    private int constructionYear;
    private int lower_price;
    private int upper_price;
    private int lower_area;
    private int higher_area;
}
