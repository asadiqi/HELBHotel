package com.example.helbhotel.strategy;

import com.example.helbhotel.model.Room;
import com.example.helbhotel.parser.Reservation;

import java.util.List;
import java.util.Random;

public class RandomAssignmentStrategy implements RoomAssignmentStrategy {
    @Override
    public Room assignRoom(List<Room> availableRooms, Reservation reservation) {
        if (availableRooms.isEmpty()) return null;
        return availableRooms.get(new Random().nextInt(availableRooms.size()));
    }
}