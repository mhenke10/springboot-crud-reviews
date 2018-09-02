package com.powerreviews.project.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.powerreviews.project.exception.NotFoundException;
import com.powerreviews.project.persistence.RatingEntity;
import com.powerreviews.project.persistence.RatingRepository;
import com.powerreviews.project.persistence.RestaurantEntity;
import com.powerreviews.project.persistence.RestaurantRepository;
import com.powerreviews.project.persistence.ReviewEntity;
import com.powerreviews.project.persistence.ReviewRepository;
import com.powerreviews.project.persistence.UserEntity;
import com.powerreviews.project.persistence.UserRepository;

@RestController
public class ReviewController {
    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final RatingRepository ratingRepository;

    public ReviewController(@Autowired ReviewRepository reviewRepository, @Autowired UserRepository userRepository,
                    @Autowired RestaurantRepository restaurantRepository, @Autowired RatingRepository ratingRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.ratingRepository = ratingRepository;
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/reviews", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewEntity> postReview(@PathVariable(value = "restaurantId") Integer restaurantId,
                    @RequestParam(value = "userId", required = true) Integer userId, @RequestParam(value="ratingValue", required=true) Integer ratingValue, @RequestBody ReviewEntity review) throws NotFoundException{
        UserEntity user = userRepository.findById(userId).orElse(null);
        if(review.doesContainIllegalReviewWords() || user.isBanned()) {
            throw new IllegalArgumentException("Illegal argument, review will not be posted");
        }
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        RatingEntity rating = ratingRepository.findByRating(ratingValue);
        if (restaurant == null || user == null || rating == null) {
            throw new NotFoundException();
        }
        
        //tried to update average rating here but had trouble and ran short on time. See comments in method.
        //restaurant.updateAverageRating(ratingValue);
        
        review.setRestaurant(restaurant);
        review.setUser(user);
        review.setRating(rating);
        
        reviewRepository.save(review);
        return new ResponseEntity<>(review, new HttpHeaders(), HttpStatus.CREATED);
    }
    
    @ResponseBody
    @RequestMapping(value="/restaurant/{restaurantId}/reviews", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReviewEntity>> getReviewsSortedByDate(@PathVariable(value="restaurantId") Integer restaurantId) {
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if(restaurant == null) {
            //throw 404 exception: unable to find restaurant 
        }
        List<ReviewEntity> reviews = reviewRepository.findAllReviewsSortedByDateForRestaurant(restaurantId);
        return new ResponseEntity<List<ReviewEntity>>(reviews, new HttpHeaders(), HttpStatus.OK);   
    }
}
