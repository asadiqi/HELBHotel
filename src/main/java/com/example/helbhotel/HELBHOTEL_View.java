package com.example.helbhotel;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HELBHOTEL_View {

    private VBox root;

    public HELBHOTEL_View(Stage stage) {
        root = new VBox();

        // Example UI element - a Button
        Button button = new Button("Click Me");
        button.setOnAction(event -> {
            System.out.println("Button clicked!");
        });

        // Add the button to the root layout
        root.getChildren().add(button);

        // Create the Scene
        Scene scene = new Scene(root, 320, 240);

        // Set the Scene on the Stage and display the Stage
        stage.setScene(scene);
        stage.show();
    }
}
