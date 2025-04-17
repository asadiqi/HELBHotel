package com.example.helbhotel;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        HELBHOTEL_View view = new HELBHOTEL_View(stage);
        HELBHotel_Controller controller = new HELBHotel_Controller(view);
    }

    public static void main(String[] args) {
        launch();
    }
}
