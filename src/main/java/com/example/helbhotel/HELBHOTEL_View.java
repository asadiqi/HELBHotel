package com.example.helbhotel;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HELBHOTEL_View {

    private VBox root;
    private static HELBHOTEL_View instance;

    private HELBHOTEL_View(Stage stage) {
        root = new VBox();
        stage.setTitle("HELBHotel");

        // === Grand cadre principal avec bord noir ===
        VBox mainWrapper = new VBox();
        mainWrapper.setPadding(new Insets(20));
        mainWrapper.setSpacing(20);
        mainWrapper.setStyle("""
            -fx-background-color: #F8F8F8;
            -fx-border-color: black;
            -fx-border-width: 2;
            -fx-border-radius: 25;
            -fx-background-radius: 25;
        """);

        // === Légendes ===
        HBox legendBox = new HBox(30);
        legendBox.setPadding(new Insets(10));
        legendBox.setStyle("-fx-alignment: center-left;");

        // Luxe
        HBox luxeLegend = createLegend("Luxe", "#D8C4EC");
        HBox businessLegend = createLegend("Business", "#BFDFFF");
        HBox ecoLegend = createLegend("Economique", "#FFE5B4");

        legendBox.getChildren().addAll(luxeLegend, businessLegend, ecoLegend);
        mainWrapper.getChildren().add(legendBox);

        // === Section principale avec les deux cadres gauche/droite ===
        HBox mainContent = new HBox();
        mainContent.setSpacing(20);
        mainContent.setPadding(new Insets(10));
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        // === Cadre gauche ===
        Region leftPanel = new Region();
        leftPanel.setStyle("""
            -fx-background-color: #FFFFFF;
            -fx-border-color: black;
            -fx-border-width: 2;
            -fx-border-radius: 15;
            -fx-background-radius: 15;
        """);
        leftPanel.setMinWidth(300);
        HBox.setHgrow(leftPanel, Priority.ALWAYS);

        // === Cadre droit ===
        VBox rightPanel = new VBox();
        rightPanel.setStyle("""
            -fx-background-color: #FFFFFF;
            -fx-border-color: black;
            -fx-border-width: 2;
            -fx-border-radius: 15;
            -fx-background-radius: 15;
        """);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);
        VBox.setVgrow(rightPanel, Priority.ALWAYS);

        // === Liste des réservations ===
        VBox buttonPanel = new VBox();
        buttonPanel.setSpacing(10);
        VBox.setVgrow(buttonPanel, Priority.ALWAYS);

        // Charger les réservations
        String csvFilePath = "C:\\Users\\sadiq\\Desktop\\Cours_Q4\\Java\\HELBHotel\\src\\main\\java\\com\\example\\helbhotel\\reservation.csv";
        ReservationRequestParser parser = new ReservationRequestParser(csvFilePath);

        while (parser.hasNextRequest()) {
            ReservationRequest request = parser.getNextReservationRequest();
            // Affichage avec l'initiale du prénom et le nom de famille
            String displayText = String.format("%s.%s", request.prenom.charAt(0), request.nom);

            // Création d'un bouton pour chaque réservation
            Button reservationButton = new Button(displayText);
            reservationButton.setStyle("""
                -fx-background-color: #4CAF50;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-padding: 10px;
                -fx-border-radius: 5;
            """);

            // Ajout de l'événement de clic pour afficher le popup
            reservationButton.setOnAction(event -> {
                // Affichage du prénom et nom dans un popup
                showReservationPopup(request.prenom, request.nom);
            });

            buttonPanel.getChildren().add(reservationButton);
        }

        rightPanel.getChildren().add(buttonPanel);
        mainContent.getChildren().addAll(leftPanel, rightPanel);

        // Ajouter tout au wrapper
        mainWrapper.getChildren().add(mainContent);

        // Ajouter le wrapper au root
        root.getChildren().add(mainWrapper);

        // Scene
        Scene scene = new Scene(root, 900, 650);
        stage.setScene(scene);
        stage.show();
    }

    // Méthode pour afficher le popup avec le prénom et le nom
    private void showReservationPopup(String prenom, String nom) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informations de Réservation");
        alert.setHeaderText(null);
        alert.setContentText("Prénom: " + prenom + "\nNom: " + nom);
        alert.showAndWait();
    }

    // Méthode pour créer une légende : texte + carré coloré
    private HBox createLegend(String text, String color) {
        HBox box = new HBox(8);
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Region colorBox = new Region();
        colorBox.setPrefSize(20, 20);
        colorBox.setStyle(String.format("""
            -fx-background-color: %s;
            -fx-border-color: black;
            -fx-border-width: 1;
            -fx-border-radius: 4;
            -fx-background-radius: 4;
        """, color));

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
