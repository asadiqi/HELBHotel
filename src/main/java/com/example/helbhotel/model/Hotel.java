package com.example.helbhotel.model;

import com.example.helbhotel.parser.HConfigParser;

import java.util.List;

public class Hotel {

    private int amountOfFloors;
    private Room [][] floor = new Room[3][3];
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
                System.out.println(floor.get(i).get(j));
                this.floor[i][j] = new RoomFactory().createRoom(roomChar.charAt(0),"A",roomNumber);
                roomNumber++;
            }
        }

    }


}