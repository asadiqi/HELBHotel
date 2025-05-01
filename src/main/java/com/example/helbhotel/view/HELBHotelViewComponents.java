package com.example.helbhotel.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class HELBHotelViewComponents {

    public static final Label createLabel(String text, double width, Pos alignment, boolean bold) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setAlignment(alignment);
        String style = "-fx-font-size: 14px;" +
                (bold ? "-fx-font-weight: bold;" : "") +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 4;" +
                "-fx-background-radius: 4;" +
                "-fx-padding: 5 10 5 10;";
        label.setStyle(style);
        return label;
    }


    public static  final HBox createLegend(String text, String color) {
        HBox box = new HBox(HELBHotelViewStyle.HBOX_SPACING);
        box.setAlignment(Pos.CENTER);
        Label lbl = createLabel(text, HELBHotelViewStyle.LABEL_WIDTH, Pos.CENTER, true);
        lbl.setMinHeight(HELBHotelViewStyle.LABEL_HEIGHT);
        Region colorBox = new Region();
        colorBox.setPrefSize(HELBHotelViewStyle.COLOR_BOX_WIDTH, HELBHotelViewStyle.COLOR_BOX_HEIGHT);
        colorBox.setStyle(String.format(
                "-fx-background-color: %s; -fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4;",
                color
        ));
        box.getChildren().addAll(lbl, colorBox);
        return box;
    }
}
