package com.driver.controllers;

import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

    HotelRepo hotelRepo = new HotelRepo();

    public String addHotel(Hotel hotel) {
        return hotelRepo.addHotel(hotel);
    }

    public Integer addUser(User user) {
        return hotelRepo.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        return hotelRepo.getHotelWithMostFacilities();
    }
}
