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

    private static final String CSV_FILE_PATH = "C:\\Users\\sadiq\\Desktop\\Cours_Q4\\Java\\HELBHotel\\src\\main\\java\\com\\example\\helbhotel\\Parser\\reservation.csv";
    private static final String HCONFIG_FILE_PATH = "C:\\Users\\sadiq\\Desktop\\Cours_Q4\\Java\\HELBHotel\\src\\main\\java\\com\\example\\helbhotel\\Parser\\hconfig";

    public HELBHotel_Controller(Stage stage) {
        // 1) Initialisation du "modèle"
        configParser  = new HConfigParser(HCONFIG_FILE_PATH);
        requestParser = new ReservationParser(CSV_FILE_PATH);

        // 2) Création de la vue
        view = new HELBHotel_View(stage, this);

        // 3) Injection des données dans la vue
        view.setupLegend();
        view.setupRoomGrid( configParser.getChambreConfig() );
        view.setupReservations( fetchAllRequests() );
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
}