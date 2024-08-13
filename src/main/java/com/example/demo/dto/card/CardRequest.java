package com.example.demo.dto.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {
    private String cardNumber;
    private Date expirationDate;


}
