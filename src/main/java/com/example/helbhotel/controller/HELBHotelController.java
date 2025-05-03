package com.example.helbhotel.controller;

import com.example.helbhotel.model.Hotel;
import com.example.helbhotel.model.Room;

import java.util.ArrayList;
import java.util.List;

public class HELBHotelController {

    private Hotel hotel;

    public HELBHotelController() {
        this.hotel = new Hotel();
    }

    public List<String[]> getRoomsInformation() {

        return  hotel.getRoomsInformation();
    }

    public Room[][] getFloor(String floorLabel) {
        return hotel.getFloor(floorLabel);
    }


}