package com.example.helbhotel;

import com.example.helbhotel.room.Room;
import com.example.helbhotel.parser.HConfigParser;
import com.example.helbhotel.parser.Reservation;
import com.example.helbhotel.parser.ReservationParser;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HELBHotel_Controller {
    private HELBHotel_View view;
    private HConfigParser configParser;
    private ReservationParser requestParser;

    private static final String CSV_FILE_PATH = "src/main/java/com/example/helbhotel/Parser/reservation.csv";
    private static final String HCONFIG_FILE_PATH = "src/main/java/com/example/helbhotel/Parser/hconfig";
    private List<Reservation> allReservations;

    public HELBHotel_Controller(Stage stage) {
        configParser  = new HConfigParser(HCONFIG_FILE_PATH);
        requestParser = new ReservationParser(CSV_FILE_PATH);

        allReservations = fetchAllRequests();

        view = new HELBHotel_View(stage, this);

        view.setupLegend();
        view.setupRoomGrid(configParser.getChambreConfig());
        // Obtenir l'étage sélectionné par défaut
        String defaultFloor = view.getFloorSelector().getSelectionModel().getSelectedItem();
        if (defaultFloor != null && !defaultFloor.isEmpty()) {
            String floorPrefix = defaultFloor.replaceAll("[^A-Za-z]", "");
            view.updateRoomGrid(configParser.getChambreConfig(), floorPrefix);
        }

        view.setupReservations(allReservations);

        view.getModeSelector().setOnAction(e -> {
            String selected = view.getModeSelector().getSelectionModel().getSelectedItem();
            if ("Random Assignment".equals(selected)) {
                List<String> roomNames = fetchAllRoomNames();

                String selectedFloor = view.getFloorSelector().getSelectionModel().getSelectedItem();
                String floorPrefix = "";
                if (selectedFloor != null && !selectedFloor.isEmpty()) {
                    floorPrefix = selectedFloor.replaceAll("[^A-Za-z]", ""); // par exemple "A" pour "A1"
                }

                view.updateReservationButtonsRandomly(roomNames, floorPrefix);
            }
        });




        view.getFloorSelector().setOnAction(e -> {
            String selectedFloor = view.getFloorSelector().getSelectionModel().getSelectedItem();
            if (selectedFloor != null && !selectedFloor.isEmpty()) {
                // Extraire uniquement les lettres
                String floorPrefix = selectedFloor.replaceAll("[^A-Za-z]", "");

                // Mettre à jour la grille avec le préfixe de lettres
                view.updateRoomGrid(configParser.getChambreConfig(), floorPrefix);

            }
        });


    }

    private List<Reservation> fetchAllRequests() {
        List<Reservation> list = new ArrayList<>();
        while (requestParser.hasNextRequest()) {
            list.add(requestParser.getNextReservationRequest());
        }
        return list;
    }

    public void handleReservationSelection(Reservation req) {
        view.showReservationPopup(req.prenom, req.nom);
    }

    public static String getFloorLabel(int n) {
        StringBuilder sb = new StringBuilder();
        n++;
        while (n > 0) {
            n--;
            sb.insert(0, (char) ('A' + (n % 26)));
            n /= 26;
        }
        return sb.toString();
    }

    public int getNombreEtages() {
        return configParser.getNombreEtages();
    }

    public void handleRoomClick(String roomName) {
        view.showInfoAlert("Informations sur la chambre", "Chambre: " + roomName);
    }

    public List<String> fetchAllRoomNames() {
        List<String> roomNames = new ArrayList<>();
        // Parcours de la configuration des chambres
        for (List<String> row : configParser.getChambreConfig()) {
            for (String roomType : row) {
                if (!"Z".equals(roomType)) { // Ignore les chambres non disponibles
                    String roomName = roomNames.size() + roomType; // Modifier selon ton modèle de nommage des chambres
                    roomNames.add(roomName);
                }
            }
        }
        return roomNames;
    }
    public List<Reservation> getAllReservations() {
        return this.allReservations;
    }

}
