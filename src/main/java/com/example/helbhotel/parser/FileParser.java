package com.example.helbhotel.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class FileParser {

    // Méthode pour lire les lignes d'un fichier, avec ou sans sauter la première ligne
    protected List<String> readLines(String filename, boolean skipFirstLine) throws IOException {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            boolean isFirstLine = skipFirstLine;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    // Méthode pour spliter une ligne en une liste de chaînes
    protected List<String> splitLine(String line, String delimiter) {
        List<String> partsList = new ArrayList<>();
        String[] parts = line.split(delimiter);
        for (String part : parts) {
            partsList.add(part.trim());
        }
        return partsList;
    }

    // Méthode pour valider le nombre de colonnes
    protected void validateColumnCount(List<String> parts, int expectedColumnCount) {
        if (parts.size() != expectedColumnCount) {
            throw new IllegalArgumentException("Invalid line format, expected " + expectedColumnCount + " columns");
        }
    }

    // Méthode abstraite que chaque parser devra implémenter pour gérer son propre contenu
    protected abstract void parse(String filename);
}


//Mais, à l'heure actuelle, il n'y a pas de duplication de code entre HConfigParser et ReservationParser ; elles réutilisent simplement des méthodes communes via leur classe parente FileParser.