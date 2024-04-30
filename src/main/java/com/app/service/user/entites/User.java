package com.app.service.user.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="micro_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {


    @Id
    @Column(name="user_id")
    @NotNull
    private String id;
    @Column(name="user_name",length = 25)
    @NotBlank
    private String name;
    @Column(name="user_email")
    @Email(message="email should not be null")
    private String email;
    @Column(name="user_about")
    private String about;
   @Transient
    private List<Rating> ratings = new ArrayList<>();



}
