package com.app.service.user.external.services;

import com.app.service.user.entites.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("RATINGSERVICE")
public interface RatingService {
    @GetMapping("/rating/users/{userId}")
    List<Rating> getRating(@PathVariable String userId);
    @PostMapping("/rating")
    Rating addRating(@RequestBody Rating rate);
}
