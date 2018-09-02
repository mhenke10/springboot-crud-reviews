package com.powerreviews.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.powerreviews.project.persistence.UserEntity;
import com.powerreviews.project.persistence.UserRepository;

@RestController
public class UserController {
    private final UserRepository userRepository;
    
    public UserController(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @ResponseBody
    @RequestMapping(value="/user/{id}", method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntity> get(@PathVariable Integer id){
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        return new ResponseEntity<>(userEntity, new HttpHeaders(), userEntity == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    
}
