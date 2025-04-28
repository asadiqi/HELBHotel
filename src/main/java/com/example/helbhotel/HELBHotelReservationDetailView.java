package com.example.helbhotel;

import com.example.helbhotel.parser.Reservation;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HELBHotelReservationDetailView {

    public static void show(Reservation reservation) {
        Stage stage = new Stage();
        stage.setTitle("Détails de Réservation");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label prenomLabel = new Label("Prénom : " + reservation.prenom);
        Label nomLabel = new Label("Nom : " + reservation.nom);

        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(e -> stage.close());

        layout.getChildren().addAll(prenomLabel, nomLabel, closeButton);

        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); // bloque la fenêtre principale
        stage.showAndWait();
    }
}
