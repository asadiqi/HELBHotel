package com.example.helbhotel.room;

public class Room {
    private String name;
    private String type; // L, B, E
    private int floor;
    private boolean isOccupied;

    public Room(String name, String type, int floor) {
        this.name = name;
        this.type = type;
        this.floor = floor;
        this.isOccupied = false;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getFloor() {
        return floor;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public String getColor() {
        return switch (type) {
            case "B" -> "#BFDFFF";
            case "E" -> "#FFE5B4";
            case "L" -> "#D8C4EC";
            default -> "white";
        };
    }

    public static String generateRoomName(int index, String type, String floorPrefix) {
        return floorPrefix + index + type;
    }
}
