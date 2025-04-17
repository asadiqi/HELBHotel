package com.example.helbhotel.Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class HConfigParser {

    private int nombreEtages;
    private ArrayList<ArrayList<String>> chambreConfig = new ArrayList<>();

    public HConfigParser(String filename) {
        parse(filename);
    }

    public int getNombreEtages() {
        return nombreEtages;
    }

    public List<List<String>> getChambreConfig() {
        List<List<String>> config = new ArrayList<>();
        for (ArrayList<String> ligne : chambreConfig) {
            config.add(new ArrayList<>(ligne)); // chaque ligne est convertie en List<String>
        }
        return config;
    }


    private void parse(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            if (scanner.hasNextLine()) {
                nombreEtages = Integer.parseInt(scanner.nextLine().trim()); // lire 1e ligne : nb étages
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    ArrayList<String> ligne = new ArrayList<>();
                    for (String part : parts) {
                        ligne.add(part.trim());
                    }
                    chambreConfig.add(ligne);
                }
            }

        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier hconfig : " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Format incorrect pour le nombre d'étages.");
        }
    }
}