package com.powerreviews.project;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.powerreviews.project.persistence.RatingEntity;
import com.powerreviews.project.persistence.RatingRepository;
import com.powerreviews.project.persistence.RestaurantEntity;
import com.powerreviews.project.persistence.RestaurantRepository;
import com.powerreviews.project.persistence.ReviewEntity;
import com.powerreviews.project.persistence.ReviewRepository;
import com.powerreviews.project.persistence.UserEntity;
import com.powerreviews.project.persistence.UserRepository;

@SpringBootApplication()
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner(RestaurantRepository restaurantRepository, UserRepository userRepository,
                    RatingRepository ratingRepository, ReviewRepository reviewRepository) {
        return args -> {
            // read json and write to db
            ObjectMapper mapper = new ObjectMapper();

            TypeReference<List<RestaurantEntity>> restaurantTypeReference = new TypeReference<List<RestaurantEntity>>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/restaurants.json");
            try {
                List<RestaurantEntity> restaurants = mapper.readValue(inputStream, restaurantTypeReference);
                // save restaurants to the database
                restaurantRepository.saveAll(restaurants);
                inputStream.close();
            } catch (IOException e) {
                //Would add: Throw exception here
                System.out.println("Unable to save restaurants: " + e.getMessage());
            }

            TypeReference<List<UserEntity>> userTypeReference = new TypeReference<List<UserEntity>>() {};
            inputStream = TypeReference.class.getResourceAsStream("/json/users.json");
            try {
                List<UserEntity> users = mapper.readValue(inputStream, userTypeReference);
                // save users to the database
                userRepository.saveAll(users);
                inputStream.close();
            } catch (IOException e) {
                //Would add: Throw exception here
                System.out.println("Unable to save users: " + e.getMessage());
            }

            TypeReference<List<RatingEntity>> ratingTypeReference = new TypeReference<List<RatingEntity>>() {};
            inputStream = TypeReference.class.getResourceAsStream("/json/ratings.json");
            try {
                List<RatingEntity> ratings = mapper.readValue(inputStream, ratingTypeReference);
                // save ratings to the database
                ratingRepository.saveAll(ratings);
                inputStream.close();
            } catch (IOException e) {
                //Would add: Throw exception here
                System.out.println("Unable to save ratings: " + e.getMessage());
            }
            
        };
    }
    
}

