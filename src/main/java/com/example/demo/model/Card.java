package com.example.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Setter
@Getter
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_seq")
    @SequenceGenerator(name = "card_seq", allocationSize = 1)
    @Column(name = "id", unique = true)
    private Integer id;


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Column(unique = true)
    private String cardNumber;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    private boolean isActive;
    private double balance;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;



    public Card() {

        this.isActive = true;

    }

    public boolean checkCardIsActive() {
        if (isActive == true && expirationDate.after(new Date())) {
            return true;
        }
        return false;
    }


}
