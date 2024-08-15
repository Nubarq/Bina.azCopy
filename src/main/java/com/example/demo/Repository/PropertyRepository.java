package com.example.demo.Repository;

import com.example.demo.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Integer> {

    Optional<Property> findByPropertyId(int propertyId);

    List<Property> findByExpirationDateBefore(LocalDate date);
}
