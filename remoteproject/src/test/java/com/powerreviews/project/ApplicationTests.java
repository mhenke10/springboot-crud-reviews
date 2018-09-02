package com.powerreviews.project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.powerreviews.project.controller.RestaurantController;
import com.powerreviews.project.controller.ReviewController;
import com.powerreviews.project.controller.UserController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    RestaurantController restController;
    
    @Autowired
    ReviewController reviewController;
    
    @Autowired
    UserController userController;
    
    @Test
	public void contextLoads() throws Exception {
        assertThat(restController).isNotNull();
        assertThat(reviewController).isNotNull();
        assertThat(userController).isNotNull();
	}

}
