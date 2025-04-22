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
        view.setupFloorSelector(configParser.getNombreEtages());
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
    public void handleEtageSelection(int index) {
        // Exemple : chaque étage est une portion consécutive de la config
        List<List<String>> fullConfig = configParser.getChambreConfig();

        int etageHeight = fullConfig.size() / configParser.getNombreEtages();
        int start = index * etageHeight;
        int end = start + etageHeight;
        List<List<String>> etageConfig = fullConfig.subList(start, end);

        view.setupRoomGrid(etageConfig);


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

    public void handleSortSelection(String sortType) {
        List<Reservation> sortedList = new ArrayList<>(allReservations); // copie

        if (sortType.equals("Name")) {
            // trie par chambre (à la place de nom)
            sortedList.sort((a, b) -> {
                String roomA = getAssignedRoom(a);
                String roomB = getAssignedRoom(b);

                int floorA = extractFloor(roomA);
                int floorB = extractFloor(roomB);

                if (floorA != floorB) {
                    return Integer.compare(floorA, floorB);
                }
                return roomA.compareTo(roomB);
            });
        } else if (sortType.equals("Room (order by floor)")) {
            // trie par nom (à la place de chambre)
            sortedList.sort((a, b) -> a.nom.compareToIgnoreCase(b.nom));
        }

        view.refreshReservations(sortedList);
    }

    private String getAssignedRoom(Reservation r) {
        // TODO: Retourne la chambre assignée à la réservation r (ex: "101L", "305B", ...)
        // Cela dépend de ta logique ou structure actuelle
        return ""; // placeholder
    }

    private int extractFloor(String room) {
        // Extraire les chiffres en début de string et prendre les premiers digits comme étage
        // Exemple: "101L" -> étage 1, "305B" -> étage 3
        StringBuilder digits = new StringBuilder();
        for (char c : room.toCharArray()) {
            if (Character.isDigit(c)) digits.append(c);
            else break;
        }
        if (digits.length() == 0) return 0;
        // Par exemple 101 -> étage 1 (le premier chiffre)
        // Ou si étage = premier chiffre(s), adapter selon ta logique
        return Integer.parseInt(digits.toString()) / 100; // Si chambre 101, étage = 1
    }


}