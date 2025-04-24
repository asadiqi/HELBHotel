package com.example.helbhotel;

import com.example.helbhotel.Parser.HConfigParser;
import com.example.helbhotel.Parser.Reservation;
import com.example.helbhotel.Parser.ReservationParser;
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


        // 1) Initialisation du "modèle"
        configParser  = new HConfigParser(HCONFIG_FILE_PATH);
        requestParser = new ReservationParser(CSV_FILE_PATH);

        // 2) Création de la vue
        allReservations = fetchAllRequests();

        view = new HELBHotel_View(stage, this);

        // 3) Injection des données dans la vue
        view.setupLegend();
        view.setupRoomGrid( configParser.getChambreConfig() );
        view.setupReservations(allReservations);


    }

    // Récupère toutes les demandes de réservation
    private List<Reservation> fetchAllRequests() {
        List<Reservation> list = new ArrayList<>();
        while (requestParser.hasNextRequest()) {
            list.add(requestParser.getNextReservationRequest());
        }
        return list;
    }

    // Méthode appelée par la vue quand on clique sur un bouton
    public void handleReservationSelection(Reservation req) {
        view.showReservationPopup(req.prenom, req.nom);
    }

    public void handleFloorSelection(int index) {
        List<List<String>> fullConfig = configParser.getChambreConfig();
        int etageHeight = fullConfig.size() / configParser.getNombreEtages();
        int start = index * etageHeight;
        int end = start + etageHeight;
        List<List<String>> etageConfig = fullConfig.subList(start, end);

        String floorLabel = HELBHotel_Controller.getFloorLabel(index);
        view.setupRoomGrid(etageConfig, floorLabel);
    }


    public static String getFloorLabel(int n) {
        StringBuilder sb = new StringBuilder();
        n++; // Passage de 0-based à 1-based
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


}