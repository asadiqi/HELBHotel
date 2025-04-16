package com.example.helbhotel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class HELBHOTEL_View {

    private VBox root;
    private static HELBHOTEL_View instance;

    // === Définition des constantes ===
    private final String BACKGROUND_COLOR = "#F8F8F8";
    private final String BORDER_COLOR = "black";
    private final int BORDER_WIDTH = 2;
    private final int BORDER_RADIUS = 25;
    private final int LABEL_WIDTH = 100;
    private final int LABEL_HEIGHT = 35;
    private final int COLOR_BOX_WIDTH = 35;
    private final int COLOR_BOX_HEIGHT = 28;
    private final int TEXT_FONT_SIZE = 14;
    private final int BUTTON_FONT_SIZE = 14;
    private final int BUTTON_PADDING = 10;
    private final String BUTTON_BACKGROUND_COLOR = "#4CAF50";
    private final String BUTTON_TEXT_FILL = "white";
    private final String CSV_FILE_PATH = "C:\\Users\\sadiq\\Desktop\\Cours_Q4\\Java\\HELBHotel\\src\\main\\java\\com\\example\\helbhotel\\reservation.csv";
    private final String HCONFIG_FILE_PATH = "C:\\Users\\sadiq\\Desktop\\Cours_Q4\\Java\\HELBHotel\\src\\main\\java\\com\\example\\helbhotel\\hconfig";
    private final double BUTTON_PREF_WIDTH = 100;
    private final double BUTTON_PREF_HEIGHT = 40;
    private final double WINDOW_HEIGHT = 650;

    // Espacement entre les composants
    private final double MAIN_WRPPER_SPACING = 20;
    private final double MAIN_CONTENT_SPACING = 20;
    private final double LEGEND_BOX_SPACING = 30;
    private final double BUTTON_PANEL_SPACING = 10;

    // === Padding et dimensions supplémentaires ===
    private final double GENERAL_PADDING = 20;
    private final double LEGEND_PADDING = 10;
    private final double MAIN_CONTENT_PADDING = 10;
    private final double PANEL_MIN_WIDTH = 200;
    private final double PANEL_PREF_HEIGHT = 400;
    private final double SCROLLPANE_PREF_HEIGHT = 400;
    private final double HBOX_SPACING = 8;

    private HELBHOTEL_View(Stage stage) {
        root = new VBox();
        stage.setTitle("HELBHotel");
        root.setPadding(new Insets(GENERAL_PADDING));

        VBox mainWrapper = new VBox();
        mainWrapper.setPadding(new Insets(GENERAL_PADDING));
        mainWrapper.setSpacing(MAIN_WRPPER_SPACING);
        mainWrapper.setStyle(String.format("""
            -fx-background-color: %s;
            -fx-border-color: %s;
            -fx-border-width: %d;
            -fx-border-radius: %d;
            -fx-background-radius: %d;
        """, BACKGROUND_COLOR, BORDER_COLOR, BORDER_WIDTH, BORDER_RADIUS, BORDER_RADIUS));
        mainWrapper.setMaxWidth(Double.MAX_VALUE);
        mainWrapper.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(mainWrapper, Priority.ALWAYS);

        HBox legendBox = new HBox(LEGEND_BOX_SPACING);
        legendBox.setPadding(new Insets(LEGEND_PADDING));
        legendBox.setStyle("-fx-alignment: center-left;");

        HBox luxeLegend = createLegend("Luxe", "#D8C4EC");
        HBox businessLegend = createLegend("Business", "#BFDFFF");
        HBox ecoLegend = createLegend("Economique", "#FFE5B4");

        legendBox.getChildren().addAll(luxeLegend, businessLegend, ecoLegend);
        mainWrapper.getChildren().add(legendBox);

        HBox mainContent = new HBox();
        mainContent.setSpacing(MAIN_CONTENT_SPACING);
        mainContent.setPadding(new Insets(MAIN_CONTENT_PADDING));
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        // === Cadre gauche : plan des chambres ===
        VBox leftPanel = new VBox();
        leftPanel.setStyle(String.format("""
            -fx-background-color: white;
            -fx-border-color: %s;
            -fx-border-width: %d;
            -fx-border-radius: 15;
            -fx-background-radius: 15;
        """, BORDER_COLOR, BORDER_WIDTH));
        leftPanel.setMinWidth(PANEL_MIN_WIDTH);
        leftPanel.setPrefHeight(PANEL_PREF_HEIGHT);
        leftPanel.setAlignment(Pos.CENTER);

        HotelConfigParser configParser = new HotelConfigParser(HCONFIG_FILE_PATH);
        ArrayList<ArrayList<String>> config = configParser.getChambreConfig();

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));

        for (int row = 0; row < config.size(); row++) {
            ArrayList<String> ligne = config.get(row);
            for (int col = 0; col < ligne.size(); col++) {
                String type = ligne.get(col);
                Label chambreLabel = new Label(type);
                chambreLabel.setPrefSize(35, 35);
                chambreLabel.setAlignment(Pos.CENTER);

                String backgroundColor;
                switch (type) {
                    case "B" -> backgroundColor = "#BFDFFF";   // Business
                    case "E" -> backgroundColor = "#FFE5B4";   // Economique
                    case "L" -> backgroundColor = "#D8C4EC";   // Luxe
                    default -> backgroundColor = "white";      // Z ou autres
                }

                chambreLabel.setStyle(String.format("""
                    -fx-background-color: %s;
                    -fx-border-color: %s;
                    -fx-border-width: 1;
                    -fx-border-radius: 4;
                    -fx-background-radius: 4;
                    -fx-font-size: 14px;
                """, backgroundColor, BORDER_COLOR));

                grid.add(chambreLabel, col, row);
            }
        }

        leftPanel.getChildren().add(grid);

        // === Cadre droit : Réservations ===
        VBox rightPanel = new VBox();
        rightPanel.setStyle(String.format("""
            -fx-background-color: white;
            -fx-border-color: %s;
            -fx-border-width: %d;
            -fx-border-radius: 15;
            -fx-background-radius: 15;
        """, BORDER_COLOR, BORDER_WIDTH));
        rightPanel.setMinWidth(PANEL_MIN_WIDTH);
        rightPanel.setPrefHeight(PANEL_PREF_HEIGHT);
        HBox.setHgrow(leftPanel, Priority.ALWAYS);

        VBox buttonPanel = new VBox();
        buttonPanel.setSpacing(BUTTON_PANEL_SPACING);
        buttonPanel.setAlignment(Pos.CENTER);

        ReservationRequestParser parser = new ReservationRequestParser(CSV_FILE_PATH);

        while (parser.hasNextRequest()) {
            ReservationRequest request = parser.getNextReservationRequest();
            String displayText = String.format("%s.%s", request.prenom.charAt(0), request.nom);

            Button reservationButton = new Button(displayText);
            reservationButton.setStyle(String.format("""
                -fx-background-color: %s;
                -fx-text-fill: %s;
                -fx-font-size: %dpx;
                -fx-padding: %dpx;
                -fx-border-radius: 5;
            """, BUTTON_BACKGROUND_COLOR, BUTTON_TEXT_FILL, BUTTON_FONT_SIZE, BUTTON_PADDING));

            reservationButton.setPrefWidth(BUTTON_PREF_WIDTH);
            reservationButton.setPrefHeight(BUTTON_PREF_HEIGHT);

            reservationButton.setOnAction(event -> {
                showReservationPopup(request.prenom, request.nom);
            });

            buttonPanel.getChildren().add(reservationButton);
        }

        ScrollPane rightScrollPane = new ScrollPane(buttonPanel);
        rightScrollPane.setFitToWidth(true);
        rightScrollPane.setPrefHeight(SCROLLPANE_PREF_HEIGHT);
        rightPanel.getChildren().add(rightScrollPane);

        mainContent.getChildren().addAll(leftPanel, rightPanel);
        mainWrapper.getChildren().add(mainContent);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(mainWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        root.getChildren().add(scrollPane);

        double WINDOW_WIDTH = 900;
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private void showReservationPopup(String prenom, String nom) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informations de Réservation");
        alert.setHeaderText(null);
        alert.setContentText("Prénom: " + prenom + "\nNom: " + nom);
        alert.showAndWait();
    }

    private HBox createLegend(String text, String color) {
        HBox box = new HBox(HBOX_SPACING);
        box.setStyle("-fx-alignment: center;");

        Label label = new Label(text);
        label.setPrefWidth(LABEL_WIDTH);
        label.setMinHeight(LABEL_HEIGHT);
        label.setStyle(String.format("""
            -fx-font-size: %dpx;
            -fx-font-weight: bold;
            -fx-padding: 4 8;
            -fx-background-color: white;
            -fx-border-color: %s;
            -fx-border-width: 1;
            -fx-border-radius: 4;
            -fx-background-radius: 4;
            -fx-alignment: center;
        """, TEXT_FONT_SIZE, BORDER_COLOR));

        Region colorBox = new Region();
        colorBox.setPrefSize(COLOR_BOX_WIDTH, COLOR_BOX_HEIGHT);
        colorBox.setStyle(String.format("""
            -fx-background-color: %s;
            -fx-border-color: %s;
            -fx-border-width: 1;
            -fx-border-radius: 4;
            -fx-background-radius: 4;
        """, color, BORDER_COLOR));

        box.getChildren().addAll(label, colorBox);
        return box;
    }

    public static HELBHOTEL_View getInstance(Stage stage) {
        if (instance == null) {
            instance = new HELBHOTEL_View(stage);
        }
        return instance;
    }
}
