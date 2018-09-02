package com.powerreviews.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.powerreviews.project.persistence.RestaurantEntity;
import com.powerreviews.project.persistence.RestaurantRepository;

@RestController
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;

    public RestaurantController(@Autowired RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantEntity> get(@PathVariable Integer id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id).orElse(null);
        return new ResponseEntity<>(restaurant, new HttpHeaders(), restaurant == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    
    @ResponseBody
    @RequestMapping(value="/restaurant", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RestaurantEntity>> getRestaurantsByFood(@RequestParam(value="type", required=true)String type){
        List<RestaurantEntity> restaurants = restaurantRepository.findByType(type);
        if(restaurants == null) {
            //no restaurants found with that type of food
        }
        return new ResponseEntity<List<RestaurantEntity>>(restaurants, new HttpHeaders(), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantEntity> post(@RequestBody RestaurantEntity restaurant) {
        restaurantRepository.save(restaurant);
        return new ResponseEntity<>(restaurant, new HttpHeaders(), HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantEntity> put(@RequestBody RestaurantEntity restaurant) {
        // What kind of validation can be put here and how?
        // Could create a validator class and add @Validated annotation. Same applies for all classes
        RestaurantEntity updated = restaurantRepository.findById(restaurant.getId()).orElse(null);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updated.setLatitude(restaurant.getLatitude());
        updated.setLongitude(restaurant.getLongitude());
        updated.setName(restaurant.getName());
        updated.setType(restaurant.getType());
        restaurantRepository.save(updated);
        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantEntity> delete(@PathVariable Integer id) {
        restaurantRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
