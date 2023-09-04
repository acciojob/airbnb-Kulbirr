package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelRepo {
    HashMap<String, Hotel> hotelDb = new HashMap<>();
    HashMap<Integer, User> userDb = new HashMap<>();
    HashMap<String, Booking> bookingDb = new HashMap<>();
    HashMap<Integer, List<Booking>> UserBookingDb = new HashMap<>();


    public String addHotel(Hotel hotel) {
        if(hotel.getHotelName() == null || hotel == null){
            return "FAILURE";
        }
        if(hotelDb.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }
        hotelDb.put(hotel.getHotelName(), hotel);
        return "SUCCESS";
    }

    public int addUser(User user) {
        userDb.put(user.getaadharCardNo(), user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        int mostFacilities = 0;
        String hotelWithMostFacilities = "";

        for(String s : hotelDb.keySet()){
            Hotel hotel = hotelDb.get(s);
            List<Facility> hotels = new ArrayList<>();
            hotels = hotel.getFacilities();

            if(hotels.size() > mostFacilities){
                mostFacilities = hotels.size();
                hotelWithMostFacilities = s;
            }

            else if(hotels.size() == mostFacilities && !hotels.isEmpty()){
                int lexicographicallySmaller = s.compareTo(hotelWithMostFacilities);
                if(lexicographicallySmaller < 0) hotelWithMostFacilities = s;
            }
        }
        return hotelWithMostFacilities;
    }


    public int bookARoom(Booking booking) {
        Hotel hotel = hotelDb.get(booking.getHotelName());
        if(hotel.getAvailableRooms() < booking.getNoOfRooms()){
            return -1;
        }
        String bookingId =  String.valueOf(UUID.randomUUID());
        booking.setBookingId(bookingId);
        booking.setAmountToBePaid(booking.getNoOfRooms() * hotel.getPricePerNight());
        bookingDb.put(bookingId, booking);
        hotel.setAvailableRooms(hotel.getAvailableRooms() - booking.getNoOfRooms());

        if(!UserBookingDb.containsKey(booking.getBookingAadharCard())){
            UserBookingDb.put(booking.getBookingAadharCard(), new ArrayList<>());
        }
        UserBookingDb.get(booking.getBookingAadharCard()).add(booking);
        return booking.getAmountToBePaid();
    }


    public int getBookings(Integer aadharCard) {
        return UserBookingDb.get(aadharCard).size();
    }
}
