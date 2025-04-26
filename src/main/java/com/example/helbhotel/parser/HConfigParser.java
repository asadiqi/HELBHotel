package com.example.helbhotel.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HConfigParser extends FileParser {

    private int nombreEtages;
    private ArrayList<ArrayList<String>> chambreConfig = new ArrayList<>();

    public HConfigParser(String filename) {
        parse(filename);  // Appel au parse() de la classe mère
    }

    public int getNombreEtages() {
        return nombreEtages;
    }

    public List<List<String>> getChambreConfig() {
        List<List<String>> config = new ArrayList<>();
        for (ArrayList<String> ligne : chambreConfig) {
            config.add(new ArrayList<>(ligne)); // Chaque ligne est convertie en List<String>
        }
        return config;
    }

    @Override
    protected void parse(String filename) {
        try {
            List<String> lines = readLines(filename, false);  // Utilisation de la méthode héritée

            if (!lines.isEmpty()) {
                nombreEtages = Integer.parseInt(lines.get(0));
                lines.remove(0); // Retirer la première ligne, elle est traitée comme le nombre d'étages
            }

            for (String line : lines) {
                List<String> parts = splitLine(line, ",");  // Utilisation de splitLine
                chambreConfig.add(new ArrayList<>(parts)); // Ajouter la ligne traitée
            }

        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier hconfig : " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Format incorrect pour le nombre d'étages.");
        }
    }
}
