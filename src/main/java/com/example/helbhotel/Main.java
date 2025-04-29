package com.example.helbhotel;

import com.example.helbhotel.model.Hotel;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        new HELBHotel_Controller(stage);
    }

    public static void main(String[] args)
    {
        launch();
    }
}
