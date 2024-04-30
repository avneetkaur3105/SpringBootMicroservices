package com.app.service.user.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Rating {

 private String ratingId;
 private String userId;
 private String hotelId;
 private int rating;
 private String feedback;
 private Hotel hotel;




}
