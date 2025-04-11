package com.example.helbhotel;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HELBHOTEL_View {

    private VBox root;
    private static HELBHOTEL_View instance;

    private HELBHOTEL_View(Stage stage) {
        root = new VBox();

        stage.setTitle("HELBHotel");

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
