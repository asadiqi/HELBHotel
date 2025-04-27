package com.example.helbhotel.room;

public class EconomiqueRoom extends Room {

    public EconomiqueRoom(int index, int floor) {
        super(generateRoomName(index, "E", "E"), "E", floor);
    }

    @Override
    public String getColor() {
        return "#FFE5B4"; // Beige/orangé
    }
}
