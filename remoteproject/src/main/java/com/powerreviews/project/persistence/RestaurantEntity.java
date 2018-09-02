package com.powerreviews.project.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity(name = "restaurant")
public class RestaurantEntity {
    private static final Logger log = LoggerFactory.getLogger(RestaurantEntity.class);
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "restaurant_name", nullable = false)
    private String name;

    @Column(name = "restaurant_type")
    private String type;

    @Column(name = "lat_coord")
    private String latitude;

    @Column(name = "long_coord")
    private String longitude;

    @Column(name = "average_rating")
    private Double averageRating;
   
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews;
    
    @Transient
    private List<Double> listOfRatings = new ArrayList<Double>();

    public RestaurantEntity() {
    }

    public RestaurantEntity(Integer id, String name, String type, String latitude, String longitude,
                    Double averageRating) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.averageRating = averageRating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
    
    public void updateAverageRating(Integer ratingValue) {
        //this can't be done because each restaurant entity contains it's own unique list. must persist list across all restaurants...
        listOfRatings.add(ratingValue.doubleValue());
        setAverageRating(listOfRatings.stream().mapToDouble(val -> val).average().orElse(0.0));
    }

    @Override
    public String toString() {
        return "RestaurantEntity [id=" + id + ", name=" + name + ", type=" + type + ", latitude=" + latitude
                        + ", longitude=" + longitude + ", averageRating=" + averageRating + ", listOfRatings="
                        + listOfRatings + ", reviews=" + reviews + "]";
    }

}
