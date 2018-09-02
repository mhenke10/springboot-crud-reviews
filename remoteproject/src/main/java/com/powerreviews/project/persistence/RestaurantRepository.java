package com.powerreviews.project.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends CrudRepository<RestaurantEntity, Integer>{
    @Query("SELECT R FROM restaurant R where R.type = :type")
    List<RestaurantEntity> findByType(@Param("type") String type);
}
