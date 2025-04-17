package com.example.helbhotel;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HELBHotel_Controller {
    private HELBHotel_View view;
    private HotelConfigParser configParser;
    private ReservationRequestParser requestParser;

    private static final String CSV_FILE_PATH = "C:\\Users\\sadiq\\Desktop\\Cours_Q4\\Java\\HELBHotel\\src\\main\\java\\com\\example\\helbhotel\\reservation.csv";
    private static final String HCONFIG_FILE_PATH = "C:\\Users\\sadiq\\Desktop\\Cours_Q4\\Java\\HELBHotel\\src\\main\\java\\com\\example\\helbhotel\\hconfig";

    public HELBHotel_Controller(Stage stage) {
        // 1) Initialisation du "modèle"
        configParser  = new HotelConfigParser(HCONFIG_FILE_PATH);
        requestParser = new ReservationRequestParser(CSV_FILE_PATH);

        // 2) Création de la vue
        view = new HELBHotel_View(stage, this);

        // 3) Injection des données dans la vue
        view.setupLegend();
        view.setupRoomGrid( configParser.getChambreConfig() );
        view.setupReservations( fetchAllRequests() );
    }

    // Récupère toutes les demandes de réservation
    private List<ReservationRequest> fetchAllRequests() {
        List<ReservationRequest> list = new ArrayList<>();
        while (requestParser.hasNextRequest()) {
            list.add(requestParser.getNextReservationRequest());
        }
        return list;
    }

    // Méthode appelée par la vue quand on clique sur un bouton
    public void handleReservationSelection(ReservationRequest req) {
        view.showReservationPopup(req.prenom, req.nom);
    }
}