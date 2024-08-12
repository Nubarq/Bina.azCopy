package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestVIPDto {

    private String user_name;
    private String email;
    private String password;
    private String card_number;
    private Date expirationDate;
}
