package com.example.helbhotel.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReservationParser {

    private static final String DELIMITER = ",";  // Séparateur de colonnes dans le fichier CSV
    private static final String FUMEUR = "Fumeur";  // Valeur pour identifier un fumeur
    private static final int MAX_COLUMN_COUNT = 6;  // Nombre de colonnes attendues dans le fichier CSV
    private static final int INDEX_NOM = 0;
    private static final int INDEX_PRENOM = 1;
    private static final int INDEX_NOMBRE_DE_PERSONNES = 2;
    private static final int INDEX_FUMEUR = 3;
    private static final int INDEX_MOTIF_SEJOUR = 4;
    private static final int INDEX_NOMBRE_ENFANTS = 5;

    private ArrayList<Reservation> reservationList = new ArrayList<>();
    private int currentIndex = 0;
    private int maxIndex = 0;

    public ReservationParser(String filename) {
        parse(filename);
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
        String[] parts = line.split(DELIMITER);

        if (parts.length != MAX_COLUMN_COUNT) {
            throw new IllegalArgumentException("Invalid line format, expected " + MAX_COLUMN_COUNT + " columns");
        }

        String nom = parts[INDEX_NOM].trim();
        String prenom = parts[INDEX_PRENOM].trim();
        int nombreDePersonnes = Integer.parseInt(parts[INDEX_NOMBRE_DE_PERSONNES].trim());
        boolean fumeur = parts[INDEX_FUMEUR].trim().equalsIgnoreCase(FUMEUR);
        String motifSejour = parts[INDEX_MOTIF_SEJOUR].trim();
        int nombreEnfants = Integer.parseInt(parts[INDEX_NOMBRE_ENFANTS].trim());

        return new Reservation(nom, prenom, nombreDePersonnes, fumeur, motifSejour, nombreEnfants);
    }

    private boolean isLineCorrect(String line) {
        return !line.trim().isEmpty(); // Tu peux raffiner ça selon tes besoins
    }

    private void parse(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            boolean isFirstLine = true;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (isFirstLine) {
                    isFirstLine = false; // ignore header
                    continue;
                }

                if (isLineCorrect(line)) {
                    Reservation request = getReservationRequestFromLineString(line);
                    reservationList.add(request);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
    }
}
