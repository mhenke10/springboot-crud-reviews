package com.powerreviews.project.persistence;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity(name = "Review")
public class ReviewEntity {
    private static final int MAX_REVIEW_TEXT_SIZE = 200;
    private static final String[] ILLEGAL_WORDS = {"lit", "hella", "chill", "bro"};

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @Size(max = MAX_REVIEW_TEXT_SIZE)
    private String review;

    @Column
    private Date reviewDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restauraunt_id")
    private RestaurantEntity restaurant;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="rating_id")
    private RatingEntity rating;

    public ReviewEntity() {
    }

    public ReviewEntity(Integer id, String review, Date reviewDate) {
        this.id = id;
        this.review = review;
        this.reviewDate = reviewDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC-6:00"));
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
            sdf.setLenient(false);
            this.reviewDate = sdf.parse(reviewDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public RatingEntity getRating() {
        return rating;
    }

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }
    
    public boolean doesContainIllegalReviewWords() {
        for (String illegalWord : ILLEGAL_WORDS) {
            if (containsIgnoreCase(this.review, illegalWord))
                return true;
        }
        return false;
    }

}
