package com.example.helbhotel.room;



public class LuxeRoom extends Room {

    public LuxeRoom(int index, int floor) {
        super(generateRoomName(index, "L", "LUXE"), "L", floor);
    }

    @Override
    public String getColor() {
        return "#D8C4EC"; // Violet pâle
    }
}
