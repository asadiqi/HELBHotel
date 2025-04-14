package com.example.helbhotel;

import com.example.helbhotel.ReservationRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class ReservationRequestParser {

    private ArrayList<ReservationRequest> reservationRequestList = new ArrayList<>();
    private int currentIndex = 0;
    private int maxIndex = 0;

    public ReservationRequestParser(String filename) {
        parse(filename);
        maxIndex = reservationRequestList.size();
    }

    public boolean hasNextRequest() {
        return (currentIndex < maxIndex);
    }

    public ReservationRequest getNextReservationRequest() {
        if (hasNextRequest()) {
            return reservationRequestList.get(currentIndex++);
        } else {
            throw new IndexOutOfBoundsException("No more requests available");
        }
    }

    private ReservationRequest getReservationRequestFromLineString(String line) {
        String[] parts = line.split(",");

        String nom = parts[0].trim();
        String prenom = parts[1].trim();
        int nombreDePersonnes = Integer.parseInt(parts[2].trim());
        boolean fumeur = parts[3].trim().equalsIgnoreCase("Fumeur");
        String motifSejour = parts[4].trim();
        int nombreEnfants = Integer.parseInt(parts[5].trim());

        return new ReservationRequest(nom, prenom, nombreDePersonnes, fumeur, motifSejour, nombreEnfants);
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
                    ReservationRequest request = getReservationRequestFromLineString(line);
                    reservationRequestList.add(request);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
    }
}
