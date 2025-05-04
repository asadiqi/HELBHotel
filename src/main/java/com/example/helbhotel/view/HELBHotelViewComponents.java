package com.example.helbhotel.view;

import com.example.helbhotel.model.Room;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

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


    public static final Button createRoomButton(Room room, EventHandler<ActionEvent> eventHandler) {
        Button btn = new Button(room.getName());
        btn.setPrefSize(70, 70);
        btn.setAlignment(Pos.CENTER);
        String bgColor = room.getColor();
        btn.setStyle(String.format(
                "-fx-background-color: %s; -fx-border-color: %s; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: 14px;",
                bgColor, HELBHotelViewStyle.COLOR_BORDER));
        btn.setOnAction(eventHandler);
        return btn;
    }

    public static final Button createButton(String text, String bgColor, boolean isBold) {
        String fontWeight = isBold ? "-fx-font-weight: bold;" : "";
        Button btn = new Button(text);
        btn.setStyle(String.format(
                "-fx-background-color: transparent; -fx-text-fill: %s; -fx-font-size: %dpx; %s -fx-padding: %dpx; -fx-border-radius: 5; -fx-border-color: black; -fx-border-width: 1;",
                HELBHotelViewStyle.BUTTON_TEXT_FILL,
                HELBHotelViewStyle.FONT_SIZE_BUTTON,
                fontWeight,
                HELBHotelViewStyle.BUTTON_PADDING
        ));
        return btn;
    }

    // Méthode pour créer un ComboBox pour trier les éléments
    public static final ComboBox<String> createSortComboBox() {
        ComboBox<String> sortComboBox = new ComboBox<>();
        sortComboBox.getItems().addAll("Name", "Room order");
        sortComboBox.getSelectionModel().selectFirst();
        sortComboBox.setPrefWidth(100);
        return sortComboBox;
    }
    public static final Button createVerifyButton() {
        Button verifyButton = createButton("Verify Code", "", true);
        verifyButton.setPrefSize(180, HELBHotelViewStyle.BUTTON_PREF_HEIGHT);
        return verifyButton;
    }

    public static final ComboBox<String> createModeSelector() {
        ComboBox<String> modeSelector = new ComboBox<>();
        modeSelector.getItems().addAll("Random Assignment", "Quiet Zone", "Stay Purpose", "Sequential Assignment");
        modeSelector.getSelectionModel().selectFirst();
        modeSelector.setStyle("-fx-font-size: 12px; -fx-background-radius: 5; -fx-padding: 4 8;");
        modeSelector.setPrefWidth(180);
        return modeSelector;
    }

    public static final HBox createSortContainer() {
        HBox sortContainer = new HBox(10);
        sortContainer.setAlignment(Pos.CENTER_LEFT);
        sortContainer.setPrefWidth(180);
        sortContainer.setMaxWidth(180);

        Label sortLabel = createLabel("Sort by:", 70, Pos.CENTER, true);
        sortLabel.setStyle(sortLabel.getStyle() +
                "-fx-font-size: 13px;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;" +
                "-fx-padding: 5 10;");

        ComboBox<String> sortComboBox = createSortComboBox();
        sortContainer.getChildren().addAll(sortLabel, sortComboBox);
        return sortContainer;
    }




}
