package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "property_seq")
    @SequenceGenerator(name = "property_seq", allocationSize = 1)
    @Column(name = "id", unique = true)
    private Integer id;

    private String adress;
    private Integer price;

    @Column(name = "property_type")
    private PropertyType propertyType;

    @Column(name = "area")
    private Integer area;

    @Column(name = "purchase_type")
    private PurchaseType purchaseType;

    @Column(name = "room_count")
    private Integer roomCount;

    @Column(name = "floor_number")
    private Integer floorNumber;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    private boolean isActive;

    public Property() {
        isActive = true;
        this.createdAt = new Date();
        this.expirationDate = calculateExpirationDate();
    }

    private Date calculateExpirationDate() {
        // Implement your logic to calculate the expiration date, for example adding 3 years to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        return calendar.getTime();
    }

    public boolean checkPropertyIsActive() {
        if (isActive == true && expirationDate.after(new Date())) {
            return true;
        }
        return false;
    }
}
