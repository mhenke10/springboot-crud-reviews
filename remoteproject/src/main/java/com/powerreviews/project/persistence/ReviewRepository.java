package com.powerreviews.project.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends CrudRepository<ReviewEntity, Integer>{
    @Query("SELECT R FROM Review R WHERE R.restaurant.id = :restaurantId ORDER BY R.reviewDate DESC")
    List<ReviewEntity> findAllReviewsSortedByDateForRestaurant(@Param("restaurantId") Integer restaurantId);
}
