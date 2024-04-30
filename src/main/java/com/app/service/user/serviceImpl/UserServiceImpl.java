package com.app.service.user.serviceImpl;

import com.app.service.user.customExceptions.ResourceNotFoundException;
import com.app.service.user.entites.Hotel;
import com.app.service.user.entites.Rating;
import com.app.service.user.entites.User;
import com.app.service.user.external.services.HotelService;
import com.app.service.user.external.services.RatingService;
import com.app.service.user.repository.UserRepository;
import com.app.service.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    HotelService hotelService;
    @Autowired
    RatingService ratingService;
    @Override
    public User saveUser(User user) {
        String randomId=UUID.randomUUID().toString();
        user.setId(randomId);
        return userRepo.save(user);

    }

    @Override
    public List<User> getAllUsers() {
        List<User> user=userRepo.findAll();
        for(User u : user) {
            Rating[] ratingsforUser = restTemplate.getForObject("http://RATINGSERVICE/rating/users/" + u.getId(),Rating[].class);
            List<Rating> ratings=Arrays.stream(ratingsforUser).toList();
            List<Rating> ratingList=ratings.stream().map(rating -> {
                ResponseEntity<Hotel> hotels=restTemplate.getForEntity("http://HOTELSERVICE/hotel/"+rating.getHotelId(),Hotel.class);
                Hotel hotel=hotels.getBody();
                rating.setHotel(hotel);
                return rating;
            }).collect(Collectors.toList());
            u.setRatings(ratingList);
        }
        return user;
    }

    @Override
    public Optional<User> getUserById(String id) {
        User user=userRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
        //Rating[] ratingsOfUser=restTemplate.getForObject("http://RATINGSERVICE/rating/users/"+user.getId(), Rating[].class);
        List<Rating> ratingsOfUser= ratingService.getRating(user.getId());
      // List<Rating> ratings=Arrays.asList(ratingsOfUser);
       List<Rating> ratingList = ratingsOfUser.stream().map(rating ->{
          // ResponseEntity<Hotel> hotels=restTemplate.getForEntity("http://localhost:8082/hotel/"+rating.getHotelId(),Hotel.class);

          Hotel hotel=hotelService.hotel(rating.getHotelId());
          rating.setHotel(hotel);
           return rating;
       }).collect(Collectors.toList());
        user.setRatings(ratingList);
        return Optional.ofNullable(user);
    }

    @Override
    public void updateUserDetails(User user, String id) {
        user.setId(id);
        userRepo.save(user);
    }
}
