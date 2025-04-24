package com.example.helbhotel.room;



public abstract class Room {
    protected String name;
    protected String type; // L, B, E
    protected int floor;
    protected boolean isOccupied;

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

    public abstract String getColor(); // ⬅️ à redéfinir dans chaque enfant

    public static String generateRoomName(int index, String type, String floorPrefix) {
        return floorPrefix + index + type;
    }
}
