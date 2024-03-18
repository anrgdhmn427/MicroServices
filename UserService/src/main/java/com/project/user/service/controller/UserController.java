package com.project.user.service.controller;

import com.project.user.service.entities.User;
import com.project.user.service.service.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);


    public void authenticationUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {

            logger.info("Authentication details are : {}", authentication);
        }

    }


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {

        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    // @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    // @Retry(name = "ratingHotelRetry", fallbackMethod = "ratingHotelFallback") Need to fix Retry
    @GetMapping("/{userId}")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback") //need to fix
    public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
        User userById = userService.getUserById(userId);
        return ResponseEntity.ok(userById);
    }

    //Creating fallback method for the circuit Breaker

    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        logger.info("fall back method called is called ", ex.getMessage());
        authenticationUserDetails();
        ex.printStackTrace();
        User build = User.builder().email("DummyEmail").name("Dummy").about("This is a Dummy User").build();
        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
        //http://localhost:8083/ratings/users/92348d62-ccdc-45d1-bbce-55a3e3e90d8d
    }
}


