package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue
    @Column(name = "property_id")
    private int propertyId;

    @Column(name = "price")
    private double price;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    private SaleType sale_type;

    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;

    @Column(name = "room_count")
    private int room_count;

    @Column(name = "image")
    private String image;

    @Column(name = "floor_number")
    private int floor_number;

    @Column(name = "total_floors")
    private int total_floors;

    @Column(name = "added_date")
    private LocalDate added_date;

    @Column(name = "expiration_date")
    private LocalDate expiration_date;

    @Column(name = "construction_year")
    private int construction_year;

    @Column(name = "area")
    private int area;

    @Column(name = "isActive")
    private boolean is_active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
