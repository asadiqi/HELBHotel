package com.example.helbhotel.strategy;

import com.example.helbhotel.model.Room;
import com.example.helbhotel.parser.Reservation;

import java.util.List;

public interface RoomAssignmentStrategy {
    Room assignRoom(List<Room> availableRooms, Reservation reservation);
}