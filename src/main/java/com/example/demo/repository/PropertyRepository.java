package com.example.demo.repository;

import com.example.demo.model.Property;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property,Integer>, JpaSpecificationExecutor<Property> {
    List<Property> findByIsActiveTrueAndExpirationDateAfter(Date currentDate);

    Page<Property> findByRoomCount(int roomCount, Pageable pageable);

    Page<Property> findByPriceBetween(double minPrice, double maxPrice, Pageable pageable);


}
