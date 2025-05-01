package com.example.helbhotel.controller;

import com.example.helbhotel.model.Hotel;

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

}