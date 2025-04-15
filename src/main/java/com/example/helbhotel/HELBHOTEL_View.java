package com.example.helbhotel;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class HELBHOTEL_View {

    private VBox root;
    private static HELBHOTEL_View instance;

    private HELBHOTEL_View(Stage stage) {
        root = new VBox();
        stage.setTitle("HELBHotel");

        // Create a ListView to display the reservation requests
        ListView<String> listView = new ListView<>();

        // Path to your CSV file
        String csvFilePath = "C:\\Users\\sadiq\\Desktop\\Cours_Q4\\Java\\HELBHotel\\src\\main\\java\\com\\example\\helbhotel\\reservation.csv";

        // Initialize the ReservationRequestParser and parse the CSV file
        ReservationRequestParser parser = new ReservationRequestParser(csvFilePath);

        // Loop through the parsed data and populate the ListView
        while (parser.hasNextRequest()) {
            ReservationRequest request = parser.getNextReservationRequest();
            String displayText = String.format("%s %s, %d people, %s, %s, %d children",
                    request.nom, request.prenom, request.nombreDePersonnes,
                    request.fumeur ? "Smoker" : "Non-smoker", request.motifSejour, request.nombreEnfants);
            listView.getItems().add(displayText);
        }

        // Add the ListView to the root layout
        root.getChildren().add(listView);

        // Set the scene and show the stage
        Scene scene = new Scene(root, 750, 550);
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
