package com.example.helbhotel;

import com.example.helbhotel.room.Room;
import com.example.helbhotel.parser.HConfigParser;
import com.example.helbhotel.parser.Reservation;
import com.example.helbhotel.parser.ReservationParser;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HELBHotel_Controller {
    private HELBHotel_View view;
    private HConfigParser configParser;
    private ReservationParser requestParser;

    private static final String CSV_FILE_PATH = "src/main/java/com/example/helbhotel/Parser/reservation.csv";
    private static final String HCONFIG_FILE_PATH = "src/main/java/com/example/helbhotel/Parser/hconfig";
    private List<Reservation> allReservations;
    private VBox buttonPanel;

    public HELBHotel_Controller(Stage stage) {
        configParser = new HConfigParser(HCONFIG_FILE_PATH);
        requestParser = new ReservationParser(CSV_FILE_PATH);
        allReservations = fetchAllRequests();

        view = new HELBHotel_View(stage, this);
        view.setupLegend();
        view.setupRoomGrid(configParser.getChambreConfig());

        // Configure floor selector
        view.getFloorSelector().setOnAction(e -> updateRoomGridForSelectedFloor());

        // Configure mode selector
        view.getModeSelector().setOnAction(e -> handleModeSelection());

        // Set up reservations
        view.setupReservations(allReservations);


        handleModeSelection();

        view.getModeSelector().setOnAction(e -> {
            String selected = view.getModeSelector().getSelectionModel().getSelectedItem();
            if ("Random Assignment".equals(selected)) {
                List<String> roomNames = getAllRoomNames(); // Récupérer les noms des chambres
                Collections.shuffle(roomNames); // Mélanger la liste des chambres

                updateReservationButtons(roomNames); // Mettre à jour les boutons de réservation
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
        view.reservationDetailView(req.prenom, req.nom);
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


    public List<String> fetchAllRoomNamesWithFloors() {
        List<String> allRooms = new ArrayList<>();

        int nombreEtages = getNombreEtages(); // nombre total d'étages
        for (int i = 0; i < nombreEtages; i++) {
            String floorPrefix = (i < 26) ? String.valueOf((char) ('A' + i)) : getFloorLabel(i);

            int counter = 1;
            for (List<String> row : configParser.getChambreConfig()) {
                for (String roomType : row) {
                    if (!"Z".equals(roomType)) { // Z = pas de chambre
                        String roomName = floorPrefix + counter + roomType;
                        allRooms.add(roomName);
                        counter++;
                    }
                }
            }
        }

        return allRooms;
    }

    public List<Reservation> getAllReservations() {
        return this.allReservations;
    }

    // Centralized method to update the room grid based on the selected floor prefix
    private void updateRoomGridForSelectedFloor() {
        String selectedFloor = view.getFloorSelector().getSelectionModel().getSelectedItem();
        if (selectedFloor != null && !selectedFloor.isEmpty()) {
            String floorPrefix = selectedFloor.replaceAll("[^A-Za-z]", "");
            view.updateRoomGrid(configParser.getChambreConfig(), floorPrefix);
        }
    }

    // Handle "Random Assignment" mode selection
    private void handleModeSelection() {
        String selected = view.getModeSelector().getSelectionModel().getSelectedItem();
        if ("Random Assignment".equals(selected)) {
            List<String> roomNames = fetchAllRoomNamesWithFloors();
            Collections.shuffle(roomNames);
            view.updateReservationButtonsRandomly(roomNames, "");

            private List<String> getAllRoomNames () {
                List<String> roomNames = new ArrayList<>();
                // Accédez à la configuration des chambres (peut-être dans `configParser`)
                for (List<String> row : configParser.getChambreConfig()) {
                    for (String roomType : row) {
                        if (!"Z".equals(roomType)) { // Ignorer les chambres vides ou non configurées
                            String roomName = generateRoomName(row.indexOf(roomType), roomType); // Générez un nom
                            roomNames.add(roomName);
                        }
                    }
                }
                return roomNames;
            }

            private void updateReservationButtons (List < String > roomNames) {
                view.getButtonPanel().getChildren().clear(); // Vider les boutons existants
                for (String roomName : roomNames) {
                    Button btn = createReservationButton(roomName);
                    view.getButtonPanel().getChildren().add(btn); // Ajouter les nouveaux boutons

                }
            }


        }


    }
}
