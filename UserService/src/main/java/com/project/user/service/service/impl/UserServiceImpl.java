package com.project.user.service.service.impl;

import com.project.user.service.entities.Hotel;
import com.project.user.service.entities.Rating;
import com.project.user.service.entities.User;
import com.project.user.service.external.services.HotelService;
import com.project.user.service.repositories.UserRepository;
import com.project.user.service.service.UserService;
import com.project.user.service.service.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!!"));
        Rating[] forObject = restTemplate.getForObject(String.format("http://RATING-SERVICE/ratings/users/%s", userId), Rating[].class);
        List<Rating> ratingsOfUser = Arrays.stream(forObject).toList();

        logger.info("{}-->", ratingsOfUser);
        if (ratingsOfUser != null && !ratingsOfUser.isEmpty()) {
            ratingsOfUser.stream().map(rating -> {
                //      ResponseEntity<Hotel> forEntity = restTemplate.getForEntity(String.format("http://HOTEL-SERVICE/hotels/%s", rating.getHotelId()), Hotel.class);
                //      Hotel hotel = forEntity.getBody();
                //      logger.info("Response status Code: {}", forEntity.getStatusCode());
                Hotel hotel = hotelService.getHotel(rating.getHotelId());
                rating.setHotel(hotel);
                return rating;
            }).collect(Collectors.toList());
        }
        user.setRatings(ratingsOfUser);
        //http://localhost:8083/ratings/users/92348d62-ccdc-45d1-bbce-55a3e3e90d8d
        return user;
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
