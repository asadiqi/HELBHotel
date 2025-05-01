package com.example.helbhotel.model;

public abstract class Room {

    private String floor;
    private int roomNumber;
    protected char roomType; // L B E
    protected String roomTypeFullName;
    protected String roomColor;

    public Room(String floor, int roomNumber) {
        this.floor = floor;
        this.roomNumber = roomNumber;

    }

}
