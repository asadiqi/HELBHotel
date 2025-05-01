package com.example.helbhotel;

import com.example.helbhotel.controller.HELBHotelController;
import com.example.helbhotel.view.HELBHotelView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
     //   new HELBHotel_Controller(stage);
        HELBHotelController controller = new HELBHotelController();
        HELBHotelView hotelView = new HELBHotelView(controller);
        stage.setScene(hotelView.scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
