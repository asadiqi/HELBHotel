package com.example.helbhotel;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HELBHOTEL_View {

    private VBox root;
    private static HELBHOTEL_View instance;

    // === Définition des constantes ===
    private static final String BACKGROUND_COLOR = "#F8F8F8";
    private static final String BORDER_COLOR = "black";
    private static final int BORDER_WIDTH = 2;
    private static final int BORDER_RADIUS = 25;
    private static final int LABEL_WIDTH = 100;
    private static final int LABEL_HEIGHT = 35;
    private static final int COLOR_BOX_WIDTH = 35;
    private static final int COLOR_BOX_HEIGHT = 28;
    private static final int TEXT_FONT_SIZE = 14;
    private static final int BUTTON_FONT_SIZE = 14;
    private static final int BUTTON_PADDING = 10;
    private static final String BUTTON_BACKGROUND_COLOR = "#4CAF50";
    private static final String BUTTON_TEXT_FILL = "white";
    private static final String CSV_FILE_PATH = "C:\\Users\\sadiq\\Desktop\\Cours_Q4\\Java\\HELBHotel\\src\\main\\java\\com\\example\\helbhotel\\reservation.csv";

    // Dimensions de la fenêtre
    private static final double WINDOW_WIDTH = 900;
    private static final double WINDOW_HEIGHT = 650;

    // Espacement entre les composants
    private static final double MAIN_WRAPPER_SPACING = 20;
    private static final double MAIN_CONTENT_SPACING = 20;
    private static final double LEGEND_BOX_SPACING = 30;
    private static final double BUTTON_PANEL_SPACING = 10;

    // === Padding et dimensions supplémentaires ===
    private static final double GENERAL_PADDING = 20;
    private static final double LEGEND_PADDING = 10;
    private static final double MAIN_CONTENT_PADDING = 10;
    private static final double PANEL_MIN_WIDTH = 200;
    private static final double PANEL_PREF_HEIGHT = 400;
    private static final double SCROLLPANE_PREF_HEIGHT = 400;
    private static final double HBOX_SPACING = 8;

    private HELBHOTEL_View(Stage stage) {
        root = new VBox();
        stage.setTitle("HELBHotel");
        root.setPadding(new Insets(GENERAL_PADDING));

        // === Grand cadre principal ===
        VBox mainWrapper = new VBox();
        mainWrapper.setPadding(new Insets(GENERAL_PADDING));
        mainWrapper.setSpacing(MAIN_WRAPPER_SPACING);
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

        // === Légendes ===
        HBox legendBox = new HBox(LEGEND_BOX_SPACING);
        legendBox.setPadding(new Insets(LEGEND_PADDING));
        legendBox.setStyle("-fx-alignment: center-left;");

        HBox luxeLegend = createLegend("Luxe", "#D8C4EC");
        HBox businessLegend = createLegend("Business", "#BFDFFF");
        HBox ecoLegend = createLegend("Economique", "#FFE5B4");

        legendBox.getChildren().addAll(luxeLegend, businessLegend, ecoLegend);
        mainWrapper.getChildren().add(legendBox);

        // === Section principale ===
        HBox mainContent = new HBox();
        mainContent.setSpacing(MAIN_CONTENT_SPACING);
        mainContent.setPadding(new Insets(MAIN_CONTENT_PADDING));
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        // === Cadre gauche ===
        Region leftPanel = new Region();
        leftPanel.setStyle(String.format("""
            -fx-background-color: white;
            -fx-border-color: %s;
            -fx-border-width: %d;
            -fx-border-radius: 15;
            -fx-background-radius: 15;
        """, BORDER_COLOR, BORDER_WIDTH));
        leftPanel.setMinWidth(PANEL_MIN_WIDTH);
        leftPanel.setPrefHeight(PANEL_PREF_HEIGHT);
        HBox.setHgrow(leftPanel, Priority.ALWAYS);

        // === Cadre droit ===
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
        HBox.setHgrow(rightPanel, Priority.ALWAYS);

        // === Liste des réservations ===
        VBox buttonPanel = new VBox();
        buttonPanel.setSpacing(BUTTON_PANEL_SPACING);

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

            reservationButton.setOnAction(event -> {
                showReservationPopup(request.prenom, request.nom);
            });

            buttonPanel.getChildren().add(reservationButton);
        }

        // === Scroll uniquement pour le cadre droit ===
        ScrollPane rightScrollPane = new ScrollPane(buttonPanel);
        rightScrollPane.setFitToWidth(true);
        rightScrollPane.setPrefHeight(SCROLLPANE_PREF_HEIGHT);
        rightPanel.getChildren().add(rightScrollPane);

        // === Assemblage ===
        mainContent.getChildren().addAll(leftPanel, rightPanel);
        mainWrapper.getChildren().add(mainContent);

        // === Scroll principal si besoin ===
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(mainWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        root.getChildren().add(scrollPane);

        // === Affichage ===
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
