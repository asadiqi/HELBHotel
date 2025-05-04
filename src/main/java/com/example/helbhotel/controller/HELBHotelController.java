package com.example.helbhotel.controller;

import com.example.helbhotel.model.Hotel;
import com.example.helbhotel.model.Room;
import com.example.helbhotel.parser.HConfigParser;
import com.example.helbhotel.parser.Reservation;
import com.example.helbhotel.parser.ReservationParser;
import com.example.helbhotel.strategy.*;
import com.example.helbhotel.view.HELBHotelView;
import com.example.helbhotel.view.HELBReservationDetailView;

import java.util.ArrayList;
import java.util.List;

public class HELBHotelController {

    private Hotel hotel;
    private HELBHotelView view;

    private HELBReservationDetailView reservationDetailView;
    private RoomAssignmentContext context;
    private HConfigParser configParser;
    private ReservationParser requestParser;
    private static final String CSV_FILE_PATH = "src/main/java/com/example/helbhotel/Parser/reservation.csv";
    private static final String HCONFIG_FILE_PATH = "src/main/java/com/example/helbhotel/Parser/hconfig";

    private List<Reservation> allReservations;



    public HELBHotelController() {
        this.hotel = new Hotel();
        configParser  = new HConfigParser(HCONFIG_FILE_PATH);
        requestParser = new ReservationParser(CSV_FILE_PATH);
        allReservations = fetchAllRequests();

    }

    public void setView(HELBHotelView view) {
        this.view = view;
        view.setupReservations(allReservations);
        this.reservationDetailView = new HELBReservationDetailView();
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

    public void setAssignmentStrategy(String mode) {
        switch (mode) {
            case "Random":
                context.setStrategy(new RandomAssignmentStrategy());
                break;
            case "Quiet":
                context.setStrategy(new QuietZoneAssignmentStrategy());
                break;
            case "Stay Purpose":
                context.setStrategy(new StayPurposeAssignmentStrategy());
                break;
            case "Sequential":
                context.setStrategy(new SequentialAssignmentStrategy());
                break;
        }
    }


}