package com.powerreviews.project.persistence;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="Rating")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @Column(nullable=false)
    private Integer rating;
    
    @Column(name="rate_desc", nullable=false)
    private String ratingDescription;

    @OneToMany(mappedBy="rating", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews;
    
    public RatingEntity() {}
    
    public RatingEntity(Integer id, Integer rating, String ratingDescription) {
        super();
        this.id = id;
        this.rating = rating;
        this.ratingDescription = ratingDescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getRatingDescription() {
        return ratingDescription;
    }

    public void setRatingDescription(String ratingDescription) {
        this.ratingDescription = ratingDescription;
    }

    @Override
    public String toString() {
        return "RatingEntity [id=" + id + ", rating=" + rating + ", ratingDescription=" + ratingDescription + "]";
    }
 
}
