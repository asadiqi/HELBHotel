package com.example.helbhotel;

public class HELBHotel_Controller {
    private HELBHOTEL_View view;
    private static HELBHotel_Controller instance;

    private HELBHotel_Controller(HELBHOTEL_View view) {
        this.view = view;
    }

    public static HELBHotel_Controller getInstance(HELBHOTEL_View view) {
        if (instance == null) {
            instance = new HELBHotel_Controller(view);
        }
        return instance;
    }


}
