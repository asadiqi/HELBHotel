package com.example.helbhotel.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class FileParser {

    // Method to read lines from a file, optionally skipping the first line
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

    // Method to split a line into a list of strings
    protected List<String> splitLine(String line, String delimiter) {
        List<String> partsList = new ArrayList<>();
        String[] parts = line.split(delimiter);
        for (String part : parts) {
            partsList.add(part.trim());
        }
        return partsList;
    }

    // Method to validate the number of columns
    protected void validateColumnCount(List<String> parts, int expectedColumnCount) {
        if (parts.size() != expectedColumnCount) {
            throw new IllegalArgumentException("Invalid line format, expected " + expectedColumnCount + " columns.");
        }
    }

    // Method to validate the room types
    protected void validateRoomConfig(List<String> parts) {
        for (String part : parts) {
            if (!isValidRoomType(part)) {
                throw new IllegalArgumentException("Invalid room type: " + part + ". Valid types are B (Business), E (Economy), L (Luxury), Z (empty space).");
            }
        }
    }

    // Method to check if the room type is valid
    private boolean isValidRoomType(String part) {
        return part.equals("B") || part.equals("E") || part.equals("L") || part.equals("Z");
    }

    // Abstract method that each parser must implement to handle its own content
    protected abstract void parse(String filename);
}
