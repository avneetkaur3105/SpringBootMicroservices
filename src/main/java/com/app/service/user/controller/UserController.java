package com.app.service.user.controller;

import com.app.service.user.entites.User;
import com.app.service.user.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    Logger logger= LoggerFactory.getLogger("UserController");
    @Autowired
    UserService userService;
    //create
    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user){
        User user1=userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
    @GetMapping("/{id}")
    @CircuitBreaker(name="ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable String id){
        Optional<User> user=userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<Optional<User>> ratingHotelFallback(String id,Exception e){
        logger.info("Error occured because some service failed : "+e.getMessage());
        Optional<User> user= Optional.ofNullable(User.builder().name("dummy").email("dummy@gmail.com")
                .about("Dummy is shown because some service has failed").build());

        return new ResponseEntity<>(user,HttpStatus.BAD_GATEWAY);
    }
int retryCount=1;
    @GetMapping
    @Retry(name="ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
    //@RateLimiter(name="serviceRateLimiter",fallbackMethod="ratingHotelFallback")
    public ResponseEntity<List<User>> getAllUser(){
        logger.info("retried - "+retryCount);
        retryCount++;
        List<User> user=userService.getAllUsers();
        return ResponseEntity.ok(user);
    }
    public ResponseEntity<List<User>> ratingHotelFallback(Exception e){
        logger.info("Error occured because some service failed : "+e.getMessage());
        User user1= User.builder().name("dummy").email("dummy@gmail.com")
                .about("Dummy is shown because some service has failed").build();
        List<User> user= new ArrayList<>();
        user.add(user1);

        return new ResponseEntity<>(user,HttpStatus.BAD_GATEWAY);
    }

}
