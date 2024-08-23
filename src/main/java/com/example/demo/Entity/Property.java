package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private int propertyId;

    @Column(name = "price")
    private double price;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    private SaleType saleType;

    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;

    @Column(name = "room_count")
    private int roomCount;

    @Column(name = "image")
    private String image;

    @Column(name = "floor_number")
    private int floorNumber;

    @Column(name = "total_floors")
    private int totalFloors;

    @Column(name = "added_date")
    private LocalDate addedDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "construction_year")
    private int constructionYear;

    @Column(name = "area")
    private int area;

    @Column(name = "isActive")
    private boolean is_active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
