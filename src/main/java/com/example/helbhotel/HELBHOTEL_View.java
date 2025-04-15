package com.example.helbhotel;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HELBHOTEL_View {

    private VBox root;
    private static HELBHOTEL_View instance;

    private HELBHOTEL_View(Stage stage) {
        root = new VBox();
        stage.setTitle("HELBHotel");

        // Espace du haut (pour les boutons à venir)
        Region topSpace = new Region();
        topSpace.setPrefHeight(80);
        root.getChildren().add(topSpace);

        // Section principale
        HBox mainContent = new HBox();
        mainContent.setSpacing(20);
        mainContent.setPadding(new Insets(20));
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        // === Cadre 1 ===
        Region leftPanel = new Region();
        leftPanel.setStyle("""
                -fx-background-color: #FFFFFF;
                -fx-border-color: #CCCCCC;
                -fx-border-radius: 15;
                -fx-background-radius: 15;
                """);
        leftPanel.setMinWidth(300);
        HBox.setHgrow(leftPanel, Priority.ALWAYS);

        // === Cadre 2 ===
        VBox rightPanel = new VBox();
        rightPanel.setStyle("""
                -fx-background-color: #FFFFFF;
                -fx-border-color: #CCCCCC;
                -fx-border-radius: 15;
                -fx-background-radius: 15;
                """);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);
        VBox.setVgrow(rightPanel, Priority.ALWAYS);

        // === ListView ===
        ListView<String> listView = new ListView<>();
        listView.setStyle("""
                -fx-background-color: #FFFFFF;
                -fx-border-radius: 15;
                -fx-background-radius: 15;
                """);
        VBox.setVgrow(listView, Priority.ALWAYS);

        // Charger les réservations
        String csvFilePath = "C:\\Users\\sadiq\\Desktop\\Cours_Q4\\Java\\HELBHotel\\src\\main\\java\\com\\example\\helbhotel\\reservation.csv";
        ReservationRequestParser parser = new ReservationRequestParser(csvFilePath);

        while (parser.hasNextRequest()) {
            ReservationRequest request = parser.getNextReservationRequest();
            String displayText = String.format("%s %s, %d personnes, %s, %s, %d enfants",
                    request.nom, request.prenom, request.nombreDePersonnes,
                    request.fumeur ? "Fumeur" : "Non-fumeur", request.motifSejour, request.nombreEnfants);


            listView.getItems().add(displayText);
        }

        rightPanel.getChildren().add(listView);
        mainContent.getChildren().addAll(leftPanel, rightPanel);
        root.getChildren().add(mainContent);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static HELBHOTEL_View getInstance(Stage stage) {
        if (instance == null) {
            instance = new HELBHOTEL_View(stage);
        }
        return instance;
    }
}
