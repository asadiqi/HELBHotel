module com.example.helbhotel {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.helbhotel to javafx.fxml;
    exports com.example.helbhotel;
}