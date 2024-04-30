package com.app.service.user.services;

import com.app.service.user.entites.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(String id);
    void updateUserDetails(User user,String id);
}
