package com.project.HotelService.services.impl;

import com.project.HotelService.entities.Hotel;
import com.project.HotelService.exceptions.ResourceNotFoundException;
import com.project.HotelService.respositories.HotelRepository;
import com.project.HotelService.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelRepository hotelRepository;


    @Override
    public Hotel create(Hotel hotel) {
        String id = UUID.randomUUID().toString();
        hotel.setId(id);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotel() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel Id Not Found !!"));
    }
}
