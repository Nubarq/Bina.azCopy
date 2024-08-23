package com.example.demo.Spesification;

import com.example.demo.model.Property;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class PropertySpecification implements Specification<Property> {

//    @Override
//    public Specification<Property> and(Specification<Property> other) {
//        return Specification.super.and(other);
//    }
//
//    @Override
//    public Specification<Property> or(Specification<Property> other) {
//        return Specification.super.or(other);
//    }

//    @Override
//    public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//        return criteriaBuilder.isTrue(root.get("isActive"));
//    }

    private final Integer minPrice;
    private final Integer maxPrice;
    private final Integer minRooms;

    public PropertySpecification(Integer minPrice, Integer maxPrice, Integer minRooms) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minRooms = minRooms;
    }

    @Override
    public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (minPrice != null && maxPrice != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("price"), minPrice, maxPrice));
        }

        if (minRooms != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("roomCount"), minRooms));
        }

        return predicate;
    }
}
