package com.example.helbhotel.view;

import com.example.helbhotel.controller.HELBHotelController;
import com.example.helbhotel.model.Room;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.List;

public class HELBHotelView {



    private HELBHotelController controller;
    public Scene scene;
    private  VBox root;
    private  VBox mainWrapper;
    private  HBox mainContent;
    public  VBox leftPanel;
    private  VBox rightPanel;
    private VBox buttonPanel;
    private HBox topBox;
    private ComboBox<String> floorSelector;

    public HELBHotelView(HELBHotelController controller) {
        this.controller = controller;
        initiateViews();
        setupLegend();
        setupRooms("A");
        setupFloorSelector();
        this.scene = new Scene(this.root, HELBHotelViewStyle.WINDOW_WIDTH, HELBHotelViewStyle.WINDOW_HEIGHT);

    }

    private void initiateViews() {
        // initiate root
        root = new VBox();
        root = new VBox();
        root.setPadding(new Insets(HELBHotelViewStyle.PADDING_GENERAL));

        // Set up the main wrapper
        VBox wrapper = new VBox();
        wrapper.setPadding(new Insets(HELBHotelViewStyle.PADDING_GENERAL));
        wrapper.setSpacing(HELBHotelViewStyle.SPACING_MAIN_WRAPPER);
        wrapper.setStyle(String.format(
                "-fx-background-color: %s; -fx-border-color: %s; -fx-border-width: %d; -fx-border-radius: %d; -fx-background-radius: %d;",
                HELBHotelViewStyle.COLOR_BACKGROUND, HELBHotelViewStyle.COLOR_BORDER, HELBHotelViewStyle.BORDER_WIDTH,
                HELBHotelViewStyle.BORDER_RADIUS, HELBHotelViewStyle.BORDER_RADIUS));

        wrapper.setMaxWidth(Double.MAX_VALUE);
        wrapper.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(wrapper, Priority.ALWAYS);
        this.mainWrapper = wrapper;

        // Setup mainContent
        HBox content = new HBox();
        content.setSpacing(HELBHotelViewStyle.SPACING_MAIN_CONTENT);
        content.setPadding(new Insets(HELBHotelViewStyle.PADDING_MAIN_CONTENT));
        VBox.setVgrow(content, Priority.ALWAYS);
        this.mainContent = content;

        // Set up the left and right panels
        VBox panel = new VBox();
        panel.setStyle(String.format("-fx-background-color: white; -fx-border-color: %s; -fx-border-width: %d; -fx-border-radius: 15; -fx-background-radius: 15;", HELBHotelViewStyle.COLOR_BORDER, HELBHotelViewStyle.BORDER_WIDTH));
        panel.setMinWidth(HELBHotelViewStyle.PANEL_MIN_WIDTH);
        panel.setPrefHeight(HELBHotelViewStyle.PANEL_PREF_HEIGHT);
        panel.setAlignment(Pos.CENTER);

        // Set up the leftpanel
        this.leftPanel = new VBox();
        this.leftPanel.setStyle(String.format(
                "-fx-background-color: white; -fx-border-color: %s; -fx-border-width: %d; -fx-border-radius: 15; -fx-background-radius: 15;",
                HELBHotelViewStyle.COLOR_BORDER, HELBHotelViewStyle.BORDER_WIDTH));
        this.leftPanel.setMinWidth(HELBHotelViewStyle.PANEL_MIN_WIDTH);
        this.leftPanel.setPrefHeight(HELBHotelViewStyle.PANEL_PREF_HEIGHT);
        this.leftPanel.setAlignment(Pos.CENTER);
        StackPane gridWrapper = new StackPane();
        gridWrapper.setPrefSize(HELBHotelViewStyle.PANEL_MIN_WIDTH, HELBHotelViewStyle.PANEL_PREF_HEIGHT);
        gridWrapper.setPadding(new Insets(10));
        this.leftPanel.getChildren().add(gridWrapper);
        HBox.setHgrow(this.leftPanel, Priority.ALWAYS);

        // Set up the rightpanel
        this.rightPanel = new VBox();
        this.rightPanel.setStyle(String.format(
                "-fx-background-color: white; -fx-border-color: %s; -fx-border-width: %d; -fx-border-radius: 15; -fx-background-radius: 15;",
                HELBHotelViewStyle.COLOR_BORDER, HELBHotelViewStyle.BORDER_WIDTH));
        this.rightPanel.setMinWidth(HELBHotelViewStyle.PANEL_MIN_WIDTH);
        this.rightPanel.setPrefHeight(HELBHotelViewStyle.PANEL_PREF_HEIGHT);
        this.rightPanel.setAlignment(Pos.CENTER);
        this.buttonPanel = new VBox(HELBHotelViewStyle.SPACING_BUTTON_PANEL);
        this.buttonPanel.setAlignment(Pos.CENTER);
        ScrollPane rightScrollPane = new ScrollPane(buttonPanel);
        rightScrollPane.setFitToWidth(true);
        rightScrollPane.setPrefHeight(HELBHotelViewStyle.SCROLLPANE_PREF_HEIGHT);
        rightPanel.getChildren().add(rightScrollPane);

        // Add topBox
        this.topBox = new HBox(20);
        topBox.setPadding(new Insets(0, 0, 10, 0));
        topBox.setAlignment(Pos.CENTER_LEFT);

        // Assemble view together!
        mainContent.getChildren().addAll(this.leftPanel, this.rightPanel);
        mainWrapper.getChildren().addAll(this.topBox, this.mainContent);
        ScrollPane outerScroll = new ScrollPane(mainWrapper);
        outerScroll.setFitToWidth(true);
        outerScroll.setFitToHeight(true);
        root.getChildren().add(outerScroll);
    }

    public void setupLegend() {
        HBox legendBox = new HBox(HELBHotelViewStyle.SPACING_LEGEND_BOX);
        legendBox.setPadding(new Insets(HELBHotelViewStyle.PADDING_LEGEND));
        legendBox.setAlignment(Pos.CENTER_LEFT);

        for (int i = 0; i < controller.getRoomsInformation().size(); i++) {
           String[] info = controller.getRoomsInformation().get(i);
           legendBox.getChildren().add(HELBHotelViewComponents.createLegend(info[0], info[1]));
        }

        mainWrapper.getChildren().add(0, legendBox);
    }

    public void setupFloorSelector() {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(10));
        Label floorLabel = HELBHotelViewComponents.createLabel("Floor :", HELBHotelViewStyle.LABEL_WIDTH, Pos.CENTER,
                true);
        this.floorSelector = new ComboBox<>();
        floorSelector.setOnAction(e -> {
            String fullLabel = floorSelector.getValue(); // Ex: A1
            String actualLabel = fullLabel.substring(0, 1); // "A"
            setupRooms(actualLabel);
        });
        List<String> floorLabels = controller.getFloorNames();

        for (int i = 0; i < floorLabels.size(); i++) {
            String label = floorLabels.get(i);
            String displayLabel = label + (i + 1); // A1, B2, ...
            floorSelector.getItems().add(displayLabel);
        }


        floorSelector.getSelectionModel().selectFirst();
        box.getChildren().addAll(floorLabel, floorSelector);
        HBox.setHgrow(box, Priority.ALWAYS);
        box.setMaxWidth(Double.MAX_VALUE);
        topBox.getChildren().add(box);
    }

    public void setupRooms(String floorLabel) {
        GridPane grid = new GridPane();
        grid.setHgap(18);
        grid.setVgap(20);
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);

        Room[][] floor = controller.getFloor(floorLabel);

        for (int row = 0; row < floor.length; row++) {
            for (int col = 0; col < floor[row].length; col++) {
                Room room = floor[row][col];

                if (room == null)
                {
                    continue;
                };

                Button btn = HELBHotelViewComponents.createRoomButton(room, e -> controller.getClass());
                grid.add(btn, col, row);
            }
        }
        StackPane wrapper = (StackPane) leftPanel.getChildren().get(0);
        wrapper.getChildren().setAll(grid);
    }


}
