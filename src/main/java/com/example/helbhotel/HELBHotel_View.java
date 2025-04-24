package com.example.helbhotel;

import com.example.helbhotel.parser.Reservation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class HELBHotel_View {

    // Constantes pour la configuration et styles
    private interface StyleConstants {
        double WINDOW_WIDTH = 900;
        double WINDOW_HEIGHT = 650;
        double PADDING_GENERAL = 20;
        double SPACING_MAIN_WRAPPER = 20;
        double SPACING_MAIN_CONTENT = 20;
        double SPACING_LEGEND_BOX = 30;
        double SPACING_BUTTON_PANEL = 10;
        double PADDING_MAIN_CONTENT = 10;
        double PADDING_LEGEND = 10;
        double PANEL_MIN_WIDTH = 200;
        double PANEL_PREF_HEIGHT = 400;
        double SCROLLPANE_PREF_HEIGHT = 400;
        double HBOX_SPACING = 8;

        String COLOR_BACKGROUND = "#F8F8F8";
        String COLOR_BORDER = "black";

        int BORDER_WIDTH = 2;
        int BORDER_RADIUS = 25;

        int LABEL_WIDTH = 110;
        int LABEL_HEIGHT = 35;
        int COLOR_BOX_WIDTH = 35;
        int COLOR_BOX_HEIGHT = 28;

        int FONT_SIZE_TEXT = 14;
        int FONT_SIZE_BUTTON = 14;

        int BUTTON_PADDING = 10;

        String BUTTON_BG_COLOR_RESERVATION = "#4CAF50";
        String BUTTON_BG_COLOR_DEFAULT = "#9E9E9E";
        String BUTTON_TEXT_FILL = "white";

        double BUTTON_PREF_WIDTH = 100;
        double BUTTON_PREF_HEIGHT = 40;
    }

    private final VBox root;
    private final VBox mainWrapper;
    private final HBox mainContent;
    public final VBox leftPanel;
    private final VBox rightPanel;
    private  VBox buttonPanel;
    private final HELBHotel_Controller controller;
    private ComboBox<String> floorSelector;
    private ComboBox<String> modeSelector;

    public HELBHotel_View(Stage stage, HELBHotel_Controller controller) {
        this.controller = controller;

        // Init root container and stage title
        root = new VBox();
        root.setPadding(new Insets(StyleConstants.PADDING_GENERAL));
        stage.setTitle("HELBHotel");

        mainWrapper = createMainWrapper();
        mainContent = createMainContent();

        leftPanel = createPanel();
        rightPanel = createPanel();

        setupLeftPanel();
        setupRightPanel();

        mainContent.getChildren().addAll(leftPanel, rightPanel);

        HBox topBox = createTopBar();

        mainWrapper.getChildren().addAll(topBox, mainContent);

        ScrollPane outerScroll = new ScrollPane(mainWrapper);
        outerScroll.setFitToWidth(true);
        outerScroll.setFitToHeight(true);

        root.getChildren().add(outerScroll);

        Scene scene = new Scene(root, StyleConstants.WINDOW_WIDTH, StyleConstants.WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public ComboBox<String> getModeSelector() {
        return modeSelector;
    }


    private VBox createMainWrapper() {
        VBox wrapper = new VBox();
        wrapper.setPadding(new Insets(StyleConstants.PADDING_GENERAL));
        wrapper.setSpacing(StyleConstants.SPACING_MAIN_WRAPPER);
        wrapper.setStyle(String.format(
                "-fx-background-color: %s; -fx-border-color: %s; -fx-border-width: %d; -fx-border-radius: %d; -fx-background-radius: %d;",
                StyleConstants.COLOR_BACKGROUND,
                StyleConstants.COLOR_BORDER,
                StyleConstants.BORDER_WIDTH,
                StyleConstants.BORDER_RADIUS,
                StyleConstants.BORDER_RADIUS
        ));
        wrapper.setMaxWidth(Double.MAX_VALUE);
        wrapper.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(wrapper, Priority.ALWAYS);
        return wrapper;
    }

    private HBox createMainContent() {
        HBox content = new HBox();
        content.setSpacing(StyleConstants.SPACING_MAIN_CONTENT);
        content.setPadding(new Insets(StyleConstants.PADDING_MAIN_CONTENT));
        VBox.setVgrow(content, Priority.ALWAYS);
        return content;
    }

    private VBox createPanel() {
        VBox panel = new VBox();
        panel.setStyle(String.format(
                "-fx-background-color: white; -fx-border-color: %s; -fx-border-width: %d; -fx-border-radius: 15; -fx-background-radius: 15;",
                StyleConstants.COLOR_BORDER,
                StyleConstants.BORDER_WIDTH
        ));
        panel.setMinWidth(StyleConstants.PANEL_MIN_WIDTH);
        panel.setPrefHeight(StyleConstants.PANEL_PREF_HEIGHT);
        panel.setAlignment(Pos.CENTER);
        return panel;
    }

    private void setupLeftPanel() {
        StackPane gridWrapper = new StackPane();
        gridWrapper.setPrefSize(StyleConstants.PANEL_MIN_WIDTH, StyleConstants.PANEL_PREF_HEIGHT);
        gridWrapper.setPadding(new Insets(10));
        leftPanel.getChildren().add(gridWrapper);

        HBox.setHgrow(leftPanel, Priority.ALWAYS);
    }

    private void setupRightPanel() {
        buttonPanel = new VBox(StyleConstants.SPACING_BUTTON_PANEL);
        buttonPanel.setAlignment(Pos.CENTER);

        ScrollPane rightScrollPane = new ScrollPane(buttonPanel);
        rightScrollPane.setFitToWidth(true);
        rightScrollPane.setPrefHeight(StyleConstants.SCROLLPANE_PREF_HEIGHT);

        rightPanel.getChildren().add(rightScrollPane);
    }

    private HBox createTopBar() {
        HBox selectorBox = createSelectorBox();
        HBox verifyButtonBox = createVerifyAndSortBox();

        HBox topBox = new HBox(20);
        topBox.setPadding(new Insets(0, 0, 10, 0));
        topBox.setAlignment(Pos.CENTER_LEFT);

        HBox.setHgrow(selectorBox, Priority.ALWAYS);
        selectorBox.setMaxWidth(Double.MAX_VALUE);

        verifyButtonBox.setAlignment(Pos.CENTER_RIGHT);

        topBox.getChildren().addAll(selectorBox, verifyButtonBox);
        return topBox;
    }

    private HBox createSelectorBox() {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(10));

        Label floorLabel = createStyledLabel("Floor :", StyleConstants.LABEL_WIDTH, Pos.CENTER, true);

        floorSelector = new ComboBox<>();
        int nombreEtages = controller.getNombreEtages();

        for (int i = 0; i < nombreEtages; i++) {
            String label = (i < 26) ? (char) ('A' + i) + String.valueOf(i + 1) : HELBHotel_Controller.getFloorLabel(i);
            floorSelector.getItems().add(label);
        }
        floorSelector.getSelectionModel().selectFirst();

        floorSelector.setOnAction(e -> {
            // Ne rien faire à la sélection d'un étage pour le moment

        });

        box.getChildren().addAll(floorLabel, floorSelector);
        return box;
    }

    private HBox createVerifyAndSortBox() {
        VBox verifyButtonContainer = new VBox(5);
        verifyButtonContainer.setAlignment(Pos.CENTER_RIGHT);

        Button verifyButton = createStyledButton("Verify Code", StyleConstants.BUTTON_BG_COLOR_DEFAULT);
        verifyButton.setPrefSize(180, StyleConstants.BUTTON_PREF_HEIGHT);
        verifyButton.setOnAction(e -> showInfoAlert("Verification", "Button clicked"));

        modeSelector = new ComboBox<>();
        modeSelector.getItems().addAll(
                "Random Assignment",
                "Quiet Zone",
                "Stay Purpose",
                "Sequential Assignment"
        );
        modeSelector.getSelectionModel().selectFirst();
        modeSelector.setStyle("-fx-font-size: 12px; -fx-background-radius: 5; -fx-padding: 4 8;");
        modeSelector.setPrefWidth(180);


        verifyButtonContainer.getChildren().addAll(verifyButton, modeSelector);

        // Sort container with label + comboBox
        HBox sortContainer = new HBox(10);
        sortContainer.setAlignment(Pos.CENTER_LEFT);
        sortContainer.setPrefWidth(180);
        sortContainer.setMaxWidth(180);

        Label sortLabel = createStyledLabel("Sort by:", 70, Pos.CENTER, true);
        sortLabel.setStyle(sortLabel.getStyle() +
                "-fx-font-size: 13px;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;" +
                "-fx-padding: 5 10;"
        );
        sortLabel.setAlignment(Pos.CENTER);

        ComboBox<String> sortComboBox = new ComboBox<>();
        sortComboBox.getItems().addAll("Name", "Room order");
        sortComboBox.getSelectionModel().selectFirst();
        sortComboBox.setPrefWidth(100);
        sortComboBox.setOnAction(e -> {
        });

        sortContainer.getChildren().addAll(sortLabel, sortComboBox);
        verifyButtonContainer.getChildren().add(sortContainer);

        HBox verifyButtonBox = new HBox();
        verifyButtonBox.setAlignment(Pos.CENTER_RIGHT);
        verifyButtonBox.setPadding(new Insets(0, 20, 0, 0));
        verifyButtonBox.getChildren().add(verifyButtonContainer);

        return verifyButtonBox;
    }

    private Label createStyledLabel(String text, double width, Pos alignment, boolean bold) {
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

    private Button createStyledButton(String text, String bgColor) {
        Button btn = new Button(text);
        btn.setStyle(String.format(
                "-fx-background-color: %s; -fx-text-fill: %s; -fx-font-size: %dpx; -fx-padding: %dpx; -fx-border-radius: 5;",
                bgColor,
                StyleConstants.BUTTON_TEXT_FILL,
                StyleConstants.FONT_SIZE_BUTTON,
                StyleConstants.BUTTON_PADDING
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

    // === Méthodes publiques pour mise à jour UI ===

    public void setupLegend() {
        HBox legendBox = new HBox(StyleConstants.SPACING_LEGEND_BOX);
        legendBox.setPadding(new Insets(StyleConstants.PADDING_LEGEND));
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

                String labelText = floorPrefix + counter++ + type;

                Label lbl = createRoomLabel(labelText, type);
                grid.add(lbl, col, row);
            }
        }

        StackPane wrapper = (StackPane) leftPanel.getChildren().get(0);
        wrapper.getChildren().setAll(grid);
    }

    private Label createRoomLabel(String text, String type) {
        Label lbl = new Label(text);
        lbl.setPrefSize(70, 70);
        lbl.setAlignment(Pos.CENTER);

        String bgColor = switch (type) {
            case "B" -> "#BFDFFF";
            case "E" -> "#FFE5B4";
            case "L" -> "#D8C4EC";
            default -> "white";
        };

        lbl.setStyle(String.format(
                "-fx-background-color: %s; -fx-border-color: %s; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: 14px;",
                bgColor, StyleConstants.COLOR_BORDER
        ));
        return lbl;
    }

    public void setupReservations(List<Reservation> reservations) {
        buttonPanel.getChildren().clear();
        for (Reservation r : reservations) {
            buttonPanel.getChildren().add(createReservationButton(r));
        }
    }

    private Button createReservationButton(Reservation r) {
        String text = String.format("%s.%s", r.prenom.charAt(0), r.nom);
        Button btn = createStyledButton(text, StyleConstants.BUTTON_BG_COLOR_RESERVATION);
        btn.setPrefSize(StyleConstants.BUTTON_PREF_WIDTH, StyleConstants.BUTTON_PREF_HEIGHT);
        btn.setOnAction(e -> controller.handleReservationSelection(r));
        return btn;
    }

    public void showReservationPopup(String prenom, String nom) {
        showInfoAlert("Informations de Réservation", "Prénom: " + prenom + "\nNom: " + nom);
    }

    private HBox createLegend(String text, String color) {
        HBox box = new HBox(StyleConstants.HBOX_SPACING);
        box.setAlignment(Pos.CENTER);

        Label lbl = createStyledLabel(text, StyleConstants.LABEL_WIDTH, Pos.CENTER, true);
        lbl.setMinHeight(StyleConstants.LABEL_HEIGHT);

        Region colorBox = new Region();
        colorBox.setPrefSize(StyleConstants.COLOR_BOX_WIDTH, StyleConstants.COLOR_BOX_HEIGHT);
        colorBox.setStyle(String.format(
                "-fx-background-color: %s; -fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4;",
                color
        ));

        box.getChildren().addAll(lbl, colorBox);
        return box;
    }

    // Accesseur public au floorSelector si besoin
    public ComboBox<String> getFloorSelector() {
        return floorSelector;
    }
}
