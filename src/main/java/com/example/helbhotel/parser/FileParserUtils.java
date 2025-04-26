package com.example.helbhotel.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileParserUtils {

    // Lire toutes les lignes d'un fichier
    public static List<String> readLines(String filename, boolean skipFirstLine) throws IOException {
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

    // Spliter une ligne avec un délimiteur
    public static List<String> splitLine(String line, String delimiter) {
        List<String> partsList = new ArrayList<>();
        String[] parts = line.split(delimiter);
        for (String part : parts) {
            partsList.add(part.trim());
        }
        return partsList;
    }
}
