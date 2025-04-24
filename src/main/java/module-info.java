module com.example.helbhotel {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.helbhotel to javafx.fxml;
    exports com.example.helbhotel;
    exports com.example.helbhotel.parser;
    opens com.example.helbhotel.parser to javafx.fxml;
}