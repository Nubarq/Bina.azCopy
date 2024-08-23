package com.example.demo.Repository;

import com.example.demo.Entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Integer>, JpaSpecificationExecutor<Property> {

    Optional<Property> findByPropertyId(int propertyId);

    List<Property> findByExpirationDateBefore(LocalDate date);

    /*
    Page<Property> findByRoomCount(int roomCount, Pageable pageable);

    Page<Property> findByFloorNumber(int floorNumber, Pageable pageable);

    Page<Property> findByConstructionYear(int constructionYear, Pageable pageable);

    Page<Property> findByPriceBetween(int lower, int higher, Pageable pageable);

    Page<Property> findByAreaBetween(int lower_area, int higher_area, Pageable pageable);

     */
}
