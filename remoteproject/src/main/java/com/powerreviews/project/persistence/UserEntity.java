package com.powerreviews.project.persistence;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "user")
public class UserEntity {
    private static final String[] BANNED_USER_LIST = {"Darth Vader", "AC Slater"};
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="user_name")
    private String name;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews;
    
    public UserEntity(){}

    public UserEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public boolean isBanned() {
        for (String bannedUser : BANNED_USER_LIST) {
            if (containsIgnoreCase(this.name, bannedUser))
                return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                "name='" + name + '\'' +
                '}';
    }
}
