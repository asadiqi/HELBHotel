package com.example.helbhotel.model;

public class EconomicRoom extends Room {

    public EconomicRoom(String floor, int roomNumber) {
        super(floor, roomNumber);
        this.roomColor="#FFE5B4";
        this.roomTypeFullName = "Economic";
        this.roomType='E';

    }
}
