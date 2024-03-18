package com.project.user.service.service;

import com.project.user.service.entities.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {

    @Transactional
    User saveUser(User user);

    List<User> getAllUser();
    
    User getUserById(String userId);

    @Transactional
    void deleteUser(String userId);

    @Transactional
    User updateUser(User user);

}
