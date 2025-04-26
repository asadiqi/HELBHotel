package com.example.helbhotel.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReservationParser extends FileParser {

    private static final String DELIMITER = ",";  // Séparateur de colonnes dans le fichier CSV
    private static final int MAX_COLUMN_COUNT = 6;  // Nombre de colonnes attendues dans le fichier CSV
    private ArrayList<Reservation> reservationList = new ArrayList<>();
    private int currentIndex = 0;
    private int maxIndex = 0;

    public ReservationParser(String filename) {
        parse(filename);  // Appel au parse() de la classe mère
        maxIndex = reservationList.size();
    }

    public boolean hasNextRequest() {
        return (currentIndex < maxIndex);
    }

    public Reservation getNextReservationRequest() {
        if (hasNextRequest()) {
            return reservationList.get(currentIndex++);
        } else {
            throw new IndexOutOfBoundsException("No more requests available");
        }
    }

    private Reservation getReservationRequestFromLineString(String line) {
        List<String> parts = splitLine(line, DELIMITER); // Utilisation de splitLine

        // Validation du nombre de colonnes
        validateColumnCount(parts, MAX_COLUMN_COUNT);

        String nom = parts.get(0);
        String prenom = parts.get(1);
        int nombreDePersonnes = Integer.parseInt(parts.get(2));
        boolean fumeur = parts.get(3).equalsIgnoreCase("Fumeur");
        String motifSejour = parts.get(4);
        int nombreEnfants = Integer.parseInt(parts.get(5));

        return new Reservation(nom, prenom, nombreDePersonnes, fumeur, motifSejour, nombreEnfants);
    }

    @Override
    protected void parse(String filename) {
        try {
            List<String> lines = readLines(filename, true);  // Utilisation de la méthode héritée

            for (String line : lines) {
                Reservation request = getReservationRequestFromLineString(line);
                reservationList.add(request);
            }

        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
    }
}
