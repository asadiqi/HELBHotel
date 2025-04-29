package com.example.helbhotel.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HELBReservationDetailView {

    private Stage popupStage;

    public HELBReservationDetailView () {
        createScene();
    }

    private void createScene(){
        this.popupStage = new Stage();
        this.popupStage.setTitle("Détail de la Réservation");

        // Cadre principal
        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(0)); // pas de padding

        // Cadre horizontal en haut
        HBox topBoxe = new HBox();
        topBoxe.setAlignment(Pos.CENTER);
        topBoxe.setPadding(new Insets(10));
        topBoxe.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        Label reservationLabel = new Label("Reservation Window");
        topBoxe.getChildren().add(reservationLabel);
        BorderPane.setMargin(topBoxe, new Insets(30, 20, 0, 20));

        // Deux cadres verticaux en bas
        HBox bottomBoxes = new HBox();
        bottomBoxes.setAlignment(Pos.CENTER);
        bottomBoxes.setPadding(new Insets(0)); // Pas de padding pour le moment
        bottomBoxes.setSpacing(0); // Pas d'espacement entre les cadres

        VBox leftBoxe = new VBox();
        leftBoxe.setAlignment(Pos.CENTER);
        leftBoxe.setPadding(new Insets(0)); // Pas de padding pour le moment
        leftBoxe.setStyle("-fx-border-color: black; -fx-border-width: 0px 0px 2px 2px;");
        leftBoxe.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(leftBoxe, Priority.ALWAYS); // permet de grandir

        VBox rightBoxe = new VBox();
        rightBoxe.setAlignment(Pos.CENTER);
        rightBoxe.setPadding(new Insets(0)); // Pas de padding pour le moment
        rightBoxe.setStyle("-fx-border-color: black; -fx-border-width: 0px 2px 2px 2px;");
        rightBoxe.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(rightBoxe, Priority.ALWAYS); // permet de grandir

        bottomBoxes.getChildren().addAll(leftBoxe, rightBoxe);
        bottomBoxes.setPadding(new Insets(0, 20, 20, 20)); // En haut, à droite, en bas, à gauche

        // Ajouter un espace en bas du BorderPane
        rootLayout.setBottom(new Region()); // Crée une région vide qui ajoutera un espace au bas

        // Organisation dans le BorderPane
        rootLayout.setTop(topBoxe);
        rootLayout.setCenter(bottomBoxes);

        // Création de la scène
        Scene popupScene = new Scene(rootLayout, 700, 500);
        this.popupStage.setScene(popupScene);
    }

    public void openView(){
        this.popupStage.show();
    }

}
