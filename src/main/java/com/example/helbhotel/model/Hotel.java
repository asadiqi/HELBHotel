package com.example.helbhotel.model;

import com.example.helbhotel.parser.HConfigParser;

import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private int amountOfFloors;
    private List <Room [][]> building = new ArrayList<Room [][]>();
    private Room [][] floor = new Room[4][4];
    private HConfigParser configParser;
    private static final String HCONFIG_FILE_PATH = "src/main/java/com/example/helbhotel/Parser/hconfig";

    public Hotel() {
        configParser  = new HConfigParser(HCONFIG_FILE_PATH);
        amountOfFloors = configParser.getNombreEtages();
        List<List<String>> floor = configParser.getChambreConfig();

        int roomNumber = 1;
        for (int i = 0; i < floor.size(); i++) {
            for (int j = 0; j < floor.get(i).size(); j++) {
               String roomChar = floor.get(i).get(j);
               this.floor[i][j] = new RoomFactory().createRoom(roomChar.charAt(0),"A",roomNumber);
                roomNumber++;
            }
        }
        for (int i = 0; i < amountOfFloors; i++) {
            building.add(this.floor);
        }
    }

    public List<String[]> getRoomsInformation() {
        List<String[]> roomInformation = new ArrayList<>();

        // Create some string arrays with 2 places each
        String[] luxeRoom = new String[2];
        luxeRoom[0] = "Luxe";
        luxeRoom[1] = "#D8C4EC";

        String[] economicRoom = new String[2];
        economicRoom[0] = "Economic";
        economicRoom[1] = "#FFE5B4";

        String[] businessRoom = new String[2];
        businessRoom[0] = "Business";
        businessRoom[1] = "#BFDFFF";

        // Add the arrays to the list
        roomInformation.add(luxeRoom);
        roomInformation.add(economicRoom);
        roomInformation.add(businessRoom);

        return roomInformation;
    }


}