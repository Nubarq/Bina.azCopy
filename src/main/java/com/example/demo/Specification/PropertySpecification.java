package com.example.demo.Specification;

import com.example.demo.Dto.SortingDto;
import com.example.demo.Entity.Property;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class PropertySpecification implements Specification<Property> {

    private final SortingDto sortingDto;

    public PropertySpecification(SortingDto sortingDto) {
        this.sortingDto = sortingDto;
    }

    public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        int roomCount = sortingDto.getRoomCount();
        int floorNumber = sortingDto.getFloorNumber();
        int constructionYear = sortingDto.getConstructionYear();
        int lower_price = sortingDto.getLower_price();
        int upper_price = sortingDto.getUpper_price();
        int lower_area = sortingDto.getLower_area();
        int higher_area = sortingDto.getHigher_area();

        if(roomCount != 0){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("roomCount"), roomCount));
        }
        if(floorNumber != 0) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("floorNumber"), floorNumber));
        }
        if(constructionYear != 0){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("constructionYear"), constructionYear));
        }
        if(lower_price != 0 && upper_price != 0){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("price"), lower_price, upper_price));
        }
        if(lower_area != 0 && higher_area != 0){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("area"), lower_area, higher_area));
        }
        return predicate;
    }
}
