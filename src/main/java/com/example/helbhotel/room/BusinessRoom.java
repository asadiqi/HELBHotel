package com.example.helbhotel.room;

public class BusinessRoom extends Room {

    public BusinessRoom(int index, int floor) {
        super(generateRoomName(index, "B", "B"), "B", floor);
    }

    @Override
    public String getColor() {
        return "#BFDFFF"; // Bleu clair
    }
}
