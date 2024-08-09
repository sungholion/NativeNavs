package com.nativenavs.tour.repository;

import com.nativenavs.tour.entity.TourCategoryEntity;
import com.nativenavs.tour.entity.TourEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class TourSpecification{

    public static Specification<TourEntity> hasLocationContaining(String location) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("location"), "%" + location + "%");
    }

    public static Specification<TourEntity> isDateInRange(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(criteriaBuilder.literal(date),
                        root.get("startDate"),
                        root.get("endDate"));
    }

    public static Specification<TourEntity> hasCategory(List<Integer> categoryIds) {
        return (root, query, criteriaBuilder) -> {
            // Join with tourCategories to filter by category
            Join<TourEntity, TourCategoryEntity> tourCategories = root.join("tourCategories");
            return tourCategories.get("category").get("id").in(categoryIds);
        };
    }
}