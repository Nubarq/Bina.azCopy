package com.example.demo.Repository;

import com.example.demo.Entity.CreditCard;
import com.example.demo.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property,Long> {

    Optional<Property> findByPropertyId(long propertyId);
}
