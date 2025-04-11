package com.example.helbhotel;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        HELBHOTEL_View view = HELBHOTEL_View.getInstance(stage);
        HELBHotel_Controller controller = HELBHotel_Controller.getInstance(view);
    }

    public static void main(String[] args) {
        launch();
    }
}
