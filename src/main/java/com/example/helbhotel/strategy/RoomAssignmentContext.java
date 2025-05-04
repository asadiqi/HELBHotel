package com.example.helbhotel.strategy;

import com.example.helbhotel.model.Hotel;
import com.example.helbhotel.model.Room;
import com.example.helbhotel.parser.Reservation;

import java.util.List;

public class RoomAssignmentContext {
    private RoomAssignmentStrategy strategy;

    public void setStrategy(RoomAssignmentStrategy strategy) {
        this.strategy = strategy;
    }

    public Room assignRoom(Hotel hotel, Reservation reservation) {
        if (strategy == null) throw new IllegalStateException("Strategy not set.");
        return strategy.assignRoom(hotel, reservation);
    }
}