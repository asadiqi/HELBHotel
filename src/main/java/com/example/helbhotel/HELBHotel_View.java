package com.example.helbhotel;

import com.example.helbhotel.parser.Reservation;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HELBHotel_View {
    private final double WINDOW_WIDTH = 900;
    private final double WINDOW_HEIGHT = 650;
    private final double PADDING_GENERAL = 20;
    private final double SPACING_MAIN_WRAPPER = 20;
    private final double SPACING_MAIN_CONTENT = 20;
    private final double SPACING_LEGEND_BOX = 30;
    private final double SPACING_BUTTON_PANEL = 10;
    private final double PADDING_MAIN_CONTENT = 10;
    private final double PADDING_LEGEND = 10;
    private final double PANEL_MIN_WIDTH = 200;
    private final double PANEL_PREF_HEIGHT = 400;
    private final double SCROLLPANE_PREF_HEIGHT = 400;
    private final double HBOX_SPACING = 8;
    private final String COLOR_BACKGROUND = "#F8F8F8";
    private final String COLOR_BORDER = "black";
    private final int BORDER_WIDTH = 2;
    private final int BORDER_RADIUS = 25;
    private final int LABEL_WIDTH = 110;
    private final int LABEL_HEIGHT = 35;
    private final int COLOR_BOX_WIDTH = 35;
    private final int COLOR_BOX_HEIGHT = 28;
    private final int FONT_SIZE_BUTTON = 14;
    private final int BUTTON_PADDING = 10;
    private final String BUTTON_BG_COLOR_DEFAULT = "D9E1E8";
    private final String BUTTON_TEXT_FILL = "black";
    private final double BUTTON_PREF_WIDTH = 100;
    private final double BUTTON_PREF_HEIGHT = 60;
    private final VBox root;
    private final VBox mainWrapper;
    private final HBox mainContent;
    public final VBox leftPanel;
    private final VBox rightPanel;
    private VBox buttonPanel;
    private final HELBHotel_Controller controller;
    private ComboBox<String> floorSelector;
    private ComboBox<String> modeSelector;

    public HELBHotel_View(Stage stage, HELBHotel_Controller controller) {
        this.controller = controller;
        root = new VBox();
        root.setPadding(new Insets(PADDING_GENERAL));
        stage.setTitle("HELBHotel");
        mainWrapper = mainWrapper();
        mainContent = mainContent();
        leftPanel = createPanel();
        rightPanel = createPanel();
        setupLeftPanel();
        setupRightPanel();
        mainContent.getChildren().addAll(leftPanel, rightPanel);
        HBox topBox = topBar();
        mainWrapper.getChildren().addAll(topBox, mainContent);
        ScrollPane outerScroll = new ScrollPane(mainWrapper);
        outerScroll.setFitToWidth(true);
        outerScroll.setFitToHeight(true);
        root.getChildren().add(outerScroll);
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();


    }


    public void reservationDetailView(String prenom, String nom) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Détail de la Réservation");

        // Cadre principal
        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(0)); // pas de padding

        // Cadre horizontal en haut
        HBox topFrame = new HBox();
        topFrame.setAlignment(Pos.CENTER);
        topFrame.setPadding(new Insets(10));
        topFrame.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        Label reservationLabel = new Label("Reservation Window");
        topFrame.getChildren().add(reservationLabel);
        BorderPane.setMargin(topFrame, new Insets(30, 20, 0, 20)); 

        // Deux cadres verticaux en bas
        HBox bottomFrames = new HBox();
        bottomFrames.setAlignment(Pos.CENTER);
        bottomFrames.setPadding(new Insets(0)); // Pas de padding pour le moment
        bottomFrames.setSpacing(0); // Pas d'espacement entre les cadres

        VBox leftFrame = new VBox();
        leftFrame.setAlignment(Pos.CENTER);
        leftFrame.setPadding(new Insets(0)); // Pas de padding pour le moment
        leftFrame.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        leftFrame.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(leftFrame, Priority.ALWAYS); // permet de grandir

        VBox rightFrame = new VBox();
        rightFrame.setAlignment(Pos.CENTER);
        rightFrame.setPadding(new Insets(0)); // Pas de padding pour le moment
        rightFrame.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        rightFrame.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(rightFrame, Priority.ALWAYS); // permet de grandir

        bottomFrames.getChildren().addAll(leftFrame, rightFrame);
        bottomFrames.setPadding(new Insets(0, 20, 20, 20)); // En haut, à droite, en bas, à gauche

        // Ajouter un espace en bas du BorderPane
        rootLayout.setBottom(new Region()); // Crée une région vide qui ajoutera un espace au bas

        // Organisation dans le BorderPane
        rootLayout.setTop(topFrame);
        rootLayout.setCenter(bottomFrames);

        // Création de la scène
        Scene popupScene = new Scene(rootLayout, 700, 500);
        popupStage.setScene(popupScene);
        popupStage.show();
    }



    public ComboBox<String> getModeSelector() {
        return modeSelector;
    }

    private VBox mainWrapper() {
        VBox wrapper = new VBox();
        wrapper.setPadding(new Insets(PADDING_GENERAL));
        wrapper.setSpacing(SPACING_MAIN_WRAPPER);
        wrapper.setStyle(String.format(
                "-fx-background-color: %s; -fx-border-color: %s; -fx-border-width: %d; -fx-border-radius: %d; -fx-background-radius: %d;",
                COLOR_BACKGROUND, COLOR_BORDER, BORDER_WIDTH, BORDER_RADIUS, BORDER_RADIUS
        ));
        wrapper.setMaxWidth(Double.MAX_VALUE);
        wrapper.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(wrapper, Priority.ALWAYS);
        return wrapper;
    }

    private HBox mainContent() {
        HBox content = new HBox();
        content.setSpacing(SPACING_MAIN_CONTENT);
        content.setPadding(new Insets(PADDING_MAIN_CONTENT));
        VBox.setVgrow(content, Priority.ALWAYS);
        return content;
    }

    private VBox createPanel() {
        VBox panel = new VBox();
        panel.setStyle(String.format(
                "-fx-background-color: white; -fx-border-color: %s; -fx-border-width: %d; -fx-border-radius: 15; -fx-background-radius: 15;",
                COLOR_BORDER, BORDER_WIDTH
        ));
        panel.setMinWidth(PANEL_MIN_WIDTH);
        panel.setPrefHeight(PANEL_PREF_HEIGHT);
        panel.setAlignment(Pos.CENTER);
        return panel;
    }

    private void setupLeftPanel() {
        StackPane gridWrapper = new StackPane();
        gridWrapper.setPrefSize(PANEL_MIN_WIDTH, PANEL_PREF_HEIGHT);
        gridWrapper.setPadding(new Insets(10));
        leftPanel.getChildren().add(gridWrapper);
        HBox.setHgrow(leftPanel, Priority.ALWAYS);
    }

    private void setupRightPanel() {
        buttonPanel = new VBox(SPACING_BUTTON_PANEL);
        buttonPanel.setAlignment(Pos.CENTER);
        ScrollPane rightScrollPane = new ScrollPane(buttonPanel);
        rightScrollPane.setFitToWidth(true);
        rightScrollPane.setPrefHeight(SCROLLPANE_PREF_HEIGHT);
        rightPanel.getChildren().add(rightScrollPane);
    }

    private HBox topBar() {
        HBox selectorBox = floorSelectorBox();
        HBox verifyButtonBox = verifyAndSortBox();
        HBox topBox = new HBox(20);
        topBox.setPadding(new Insets(0, 0, 10, 0));
        topBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(selectorBox, Priority.ALWAYS);
        selectorBox.setMaxWidth(Double.MAX_VALUE);
        verifyButtonBox.setAlignment(Pos.CENTER_RIGHT);
        topBox.getChildren().addAll(selectorBox, verifyButtonBox);
        return topBox;
    }

    private HBox floorSelectorBox() {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(10));
        Label floorLabel = createLabel("Floor :", LABEL_WIDTH, Pos.CENTER, true);
        floorSelector = new ComboBox<>();
        int nombreEtages = controller.getNombreEtages();
        for (int i = 0; i < nombreEtages; i++) {
            String label = (i < 26) ? (char) ('A' + i) + String.valueOf(i + 1) : HELBHotel_Controller.getFloorLabel(i);
            floorSelector.getItems().add(label);
        }
        floorSelector.getSelectionModel().selectFirst();
        box.getChildren().addAll(floorLabel, floorSelector);
        return box;
    }

    public void updateRoomGrid(List<List<String>> config, String floorPrefix) {
        setupRoomGrid(config, floorPrefix);
    }

    public ComboBox<String> getFloorSelector() {
        return floorSelector;
    }

    private HBox verifyAndSortBox() {
        VBox verifyButtonContainer = new VBox(5);
        verifyButtonContainer.setAlignment(Pos.CENTER_RIGHT);
        Button verifyButton = createButton("Verify Code", "", true);
        verifyButton.setPrefSize(180, BUTTON_PREF_HEIGHT);
        verifyButton.setOnAction(e -> showInfoAlert("Verification", "Button clicked"));
        modeSelector = new ComboBox<>();
        modeSelector.getItems().addAll("Random Assignment", "Quiet Zone", "Stay Purpose", "Sequential Assignment");
        modeSelector.getSelectionModel().selectFirst();
        modeSelector.setStyle("-fx-font-size: 12px; -fx-background-radius: 5; -fx-padding: 4 8;");
        modeSelector.setPrefWidth(180);
        verifyButtonContainer.getChildren().addAll(verifyButton, modeSelector);
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
                "-fx-padding: 5 10;"
        );
        ComboBox<String> sortComboBox = new ComboBox<>();
        sortComboBox.getItems().addAll("Name", "Room order");
        sortComboBox.getSelectionModel().selectFirst();
        sortComboBox.setPrefWidth(100);
        sortContainer.getChildren().addAll(sortLabel, sortComboBox);
        verifyButtonContainer.getChildren().add(sortContainer);
        HBox verifyButtonBox = new HBox();
        verifyButtonBox.setAlignment(Pos.CENTER_RIGHT);
        verifyButtonBox.setPadding(new Insets(0, 20, 0, 0));
        verifyButtonBox.getChildren().add(verifyButtonContainer);
        return verifyButtonBox;
    }

    private Label createLabel(String text, double width, Pos alignment, boolean bold) {
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

    private Button createButton(String text, String bgColor, boolean isBold) {
        String fontWeight = isBold ? "-fx-font-weight: bold;" : "";
        Button btn = new Button(text);
        btn.setStyle(String.format(
                "-fx-background-color: transparent; -fx-text-fill: %s; -fx-font-size: %dpx; %s -fx-padding: %dpx; -fx-border-radius: 5; -fx-border-color: black; -fx-border-width: 1;",
                BUTTON_TEXT_FILL,
                FONT_SIZE_BUTTON,
                fontWeight,
                BUTTON_PADDING
        ));
        return btn;
    }

    public void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setupLegend() {
        HBox legendBox = new HBox(SPACING_LEGEND_BOX);
        legendBox.setPadding(new Insets(PADDING_LEGEND));
        legendBox.setAlignment(Pos.CENTER_LEFT);
        legendBox.getChildren().addAll(
                createLegend("Luxe", "#D8C4EC"),
                createLegend("Business", "#BFDFFF"),
                createLegend("Economique", "#FFE5B4")
        );
        mainWrapper.getChildren().add(0, legendBox);
    }

    public void setupRoomGrid(List<List<String>> config) {
        setupRoomGrid(config, "");
    }

    public void setupRoomGrid(List<List<String>> config, String floorPrefix) {
        GridPane grid = new GridPane();
        grid.setHgap(18);
        grid.setVgap(20);
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        int counter = 1;
        for (int row = 0; row < config.size(); row++) {
            for (int col = 0; col < config.get(row).size(); col++) {
                String type = config.get(row).get(col);
                if ("Z".equals(type)) continue;
                String roomName = floorPrefix + counter++ + type;
                Button btn = createRoomButton(roomName, type);
                grid.add(btn, col, row);
            }
        }
        StackPane wrapper = (StackPane) leftPanel.getChildren().get(0);
        wrapper.getChildren().setAll(grid);
    }

    private Button createRoomButton(String roomName, String type) {
        Button btn = new Button(roomName);
        btn.setPrefSize(70, 70);
        btn.setAlignment(Pos.CENTER);
        String bgColor = switch (type) {
            case "B" -> "#BFDFFF";
            case "E" -> "#FFE5B4";
            case "L" -> "#D8C4EC";
            default -> "white";
        };
        btn.setStyle(String.format(
                "-fx-background-color: %s; -fx-border-color: %s; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: 14px;",
                bgColor, COLOR_BORDER
        ));
        btn.setOnAction(e -> controller.handleRoomClick(roomName));
        return btn;
    }

    public void setupReservations(List<Reservation> reservations) {
        buttonPanel.getChildren().clear();
        for (Reservation r : reservations) {
            buttonPanel.getChildren().add(createReservationButton(r));
        }
    }

    private Button createReservationButton(Reservation r) {
        String text = String.format("%s.%s", r.prenom.charAt(0), r.nom);
        Button btn = createButton(text, "", false);
        btn.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);
        btn.setOnAction(e -> controller.handleReservationSelection(r));
        return btn;
    }

    private HBox createLegend(String text, String color) {
        HBox box = new HBox(HBOX_SPACING);
        box.setAlignment(Pos.CENTER);
        Label lbl = createLabel(text, LABEL_WIDTH, Pos.CENTER, true);
        lbl.setMinHeight(LABEL_HEIGHT);
        Region colorBox = new Region();
        colorBox.setPrefSize(COLOR_BOX_WIDTH, COLOR_BOX_HEIGHT);
        colorBox.setStyle(String.format(
                "-fx-background-color: %s; -fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4;",
                color
        ));
        box.getChildren().addAll(lbl, colorBox);
        return box;
    }

    public void updateReservationButtonsRandomly(List<String> roomNames, String floorPrefix) {
        Collections.shuffle(roomNames);
        List<Reservation> allReservations = controller.getAllReservations();
        Platform.runLater(() -> {
            for (int i = 0; i < buttonPanel.getChildren().size(); i++) {
                Button reservationButton = (Button) buttonPanel.getChildren().get(i);
                Reservation reservation = allReservations.get(i);
                String assignedRoom = roomNames.get(i);
                char roomType = assignedRoom.charAt(assignedRoom.length() - 1);
                String bgColor;
                String textColor = "black";
                switch (roomType) {
                    case 'B': bgColor = "#BFDFFF"; break;
                    case 'E': bgColor = "#FFE5B4"; break;
                    case 'L': bgColor = "#D8C4EC"; break;
                    default: bgColor = "#BDC3C7"; break;
                }
                String floorPrefixe = "   " + floorPrefix;
                String newText = String.format("%s.%s\n%s%s", reservation.prenom.charAt(0), reservation.nom, floorPrefixe, assignedRoom);
                reservationButton.setText(newText);
                reservationButton.setStyle(String.format(
                        "-fx-background-color: %s; -fx-text-fill: %s; -fx-font-size: %dpx; -fx-padding: %dpx; -fx-border-radius: 5; -fx-border-color: black; -fx-border-width: 1;",
                        bgColor, textColor, FONT_SIZE_BUTTON, BUTTON_PADDING
                ));
            }
        });
    }
}
