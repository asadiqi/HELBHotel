package com.example.helbhotel.controller;

import com.example.helbhotel.model.Hotel;
import com.example.helbhotel.model.Room;
import com.example.helbhotel.parser.Reservation;
import com.example.helbhotel.parser.ReservationParser;
import com.example.helbhotel.view.HELBReservationDetailView;

import java.util.ArrayList;
import java.util.List;

public class HELBHotelController {

    private Hotel hotel;
    private HELBReservationDetailView reservationDetailView;
    private ReservationParser requestParser;
    private List<Reservation> allReservations;


    public HELBHotelController() {
        this.hotel = new Hotel();

    }

    public List<String[]> getRoomsInformation() {

        return  hotel.getRoomsInformation();
    }

    public Room[][] getFloor(String floorLabel) {
        return hotel.getFloor(floorLabel);
    }

    public List<String> getFloorNames() {
        int amountOfFloors = hotel.getAmountOfFloors();
        List<String> floorLabels = new ArrayList<>();
        for (int i = 0; i < amountOfFloors; i++) {
            String floorLabel = hotel.getFloorLabel(i);
            floorLabels.add(floorLabel);
        }
        return floorLabels;
    }

    public void handleReservationSelection(Reservation req) {
        reservationDetailView.openView();
    }

    private List<Reservation> fetchAllRequests() {
        List<Reservation> list = new ArrayList<>();
        while (requestParser.hasNextRequest()) {
            list.add(requestParser.getNextReservationRequest());
        }
        return list;
    }

}