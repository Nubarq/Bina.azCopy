package com.example.demo.repository;

import com.example.demo.model.Property;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property,Integer> {
    List<Property> findByIsActiveTrueAndExpirationDateAfter(Date currentDate);

}
