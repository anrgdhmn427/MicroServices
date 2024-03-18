package com.project.ratingService.services;

import com.project.ratingService.entities.Rating;

import java.util.List;

public interface RatingService {
    Rating createRating(Rating rating);

    List<Rating> getRatings();

    List<Rating> getRatingByUserId(String userID);

    List<Rating> getRatingByHotelId(String hotelId);
}
