package com.powerreviews.project.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends CrudRepository<RatingEntity, Integer>{
    @Query("SELECT R FROM Rating R WHERE R.rating = :rating")
    RatingEntity findByRating(@Param("rating")Integer rating);
}
