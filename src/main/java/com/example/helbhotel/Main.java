package com.example.helbhotel;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Pass the stage to the view for it to handle the scene and stage display
        HELBHOTEL_View view = new HELBHOTEL_View(stage);
        new HELBHotel_Controller(view); // Assumed controller initialization
    }

    public static void main(String[] args) {
        launch();
    }
}
