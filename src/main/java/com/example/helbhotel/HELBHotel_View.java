// HELBHOTEL_View.java
package com.example.helbhotel;

import com.example.helbhotel.Parser.Reservation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class HELBHotel_View {

    private final double WINDOW_WIDTH            = 900;
    private final double WINDOW_HEIGHT           = 650;
    private final double GENERAL_PADDING         = 20;
    private final double MAIN_WRPPER_SPACING     = 20;
    private final double MAIN_CONTENT_SPACING    = 20;
    private final double LEGEND_BOX_SPACING      = 30;
    private final double BUTTON_PANEL_SPACING    = 10;
    private final double MAIN_CONTENT_PADDING    = 10;
    private final double LEGEND_PADDING          = 10;
    private final double PANEL_MIN_WIDTH         = 200;
    private final double PANEL_PREF_HEIGHT       = 400;
    private final double SCROLLPANE_PREF_HEIGHT  = 400;
    private final double HBOX_SPACING            = 8;

    private final String BACKGROUND_COLOR        = "#F8F8F8";
    private final String BORDER_COLOR            = "black";
    private final int    BORDER_WIDTH            = 2;
    private final int    BORDER_RADIUS           = 25;
    private final int    LABEL_WIDTH             = 100;
    private final int    LABEL_HEIGHT            = 35;
    private final int    COLOR_BOX_WIDTH         = 35;
    private final int    COLOR_BOX_HEIGHT        = 28;
    private final int    TEXT_FONT_SIZE          = 14;
    private final int    BUTTON_FONT_SIZE        = 14;
    private final int    BUTTON_PADDING          = 10;
    private final String BUTTON_BACKGROUND_COLOR = "#4CAF50";
    private final String BUTTON_TEXT_FILL        = "white";
    private final double BUTTON_PREF_WIDTH       = 100;
    private final double BUTTON_PREF_HEIGHT      = 40;

    private VBox      root;
    private VBox      mainWrapper;
    private HBox      mainContent;
    public VBox      leftPanel;
    private VBox      rightPanel;
    private VBox      buttonPanel;
    private HELBHotel_Controller controller;
    private ComboBox<String> floorSelector;

    public HELBHotel_View(Stage stage, HELBHotel_Controller controller) {
        this.controller = controller;

        // Racine et fenêtre
        root = new VBox();
        root.setPadding(new Insets(GENERAL_PADDING));
        stage.setTitle("HELBHotel");

        // Conteneur principal
        mainWrapper = new VBox();
        mainWrapper.setPadding(new Insets(GENERAL_PADDING));
        mainWrapper.setSpacing(MAIN_WRPPER_SPACING);
        mainWrapper.setStyle(String.format(
                """
                -fx-background-color: %s;
                -fx-border-color: %s;
                -fx-border-width: %d;
                -fx-border-radius: %d;
                -fx-background-radius: %d;
                """,
                BACKGROUND_COLOR, BORDER_COLOR, BORDER_WIDTH, BORDER_RADIUS, BORDER_RADIUS
        ));
        mainWrapper.setMaxWidth(Double.MAX_VALUE);
        mainWrapper.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(mainWrapper, Priority.ALWAYS);

        // Contenu central (gauche : plan, droite : réservations)
        mainContent = new HBox();
        mainContent.setSpacing(MAIN_CONTENT_SPACING);
        mainContent.setPadding(new Insets(MAIN_CONTENT_PADDING));
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        // --- Left panel : plan des chambres ---
        leftPanel = new VBox();
        leftPanel.setStyle(String.format(
                """
                -fx-background-color: white;
                -fx-border-color: %s;
                -fx-border-width: %d;
                -fx-border-radius: 15;
                -fx-background-radius: 15;
                """, BORDER_COLOR, BORDER_WIDTH
        ));
        leftPanel.setMinWidth(PANEL_MIN_WIDTH);
        leftPanel.setPrefHeight(PANEL_PREF_HEIGHT);
        leftPanel.setAlignment(Pos.CENTER);
        // Wrapper pour le GridPane
        StackPane gridWrapper = new StackPane();
        gridWrapper.setPrefWidth(PANEL_MIN_WIDTH);
        gridWrapper.setPrefHeight(PANEL_PREF_HEIGHT);
        gridWrapper.setPadding(new Insets(10));
        leftPanel.getChildren().add(gridWrapper);

        // --- Right panel : réservations ---
        rightPanel = new VBox();
        rightPanel.setStyle(String.format(
                """
                -fx-background-color: white;
                -fx-border-color: %s;
                -fx-border-width: %d;
                -fx-border-radius: 15;
                -fx-background-radius: 15;
                """, BORDER_COLOR, BORDER_WIDTH
        ));
        rightPanel.setMinWidth(PANEL_MIN_WIDTH);
        rightPanel.setPrefHeight(PANEL_PREF_HEIGHT);

        buttonPanel = new VBox();
        buttonPanel.setSpacing(BUTTON_PANEL_SPACING);
        buttonPanel.setAlignment(Pos.CENTER);
        ScrollPane rightScrollPane = new ScrollPane(buttonPanel);
        rightScrollPane.setFitToWidth(true);
        rightScrollPane.setPrefHeight(SCROLLPANE_PREF_HEIGHT);
        rightPanel.getChildren().add(rightScrollPane);

        HBox.setHgrow(leftPanel, Priority.ALWAYS);
        mainContent.getChildren().addAll(leftPanel, rightPanel);

        // --- Selector Box ---
        HBox selectorBox = new HBox(10);
        selectorBox.setAlignment(Pos.CENTER_LEFT);
        selectorBox.setPadding(new Insets(10));

        Label floorLabel = new Label("Floor :");
        floorLabel.setPrefWidth(100);  // largeur fixe
        floorLabel.setAlignment(Pos.CENTER); // aligne le texte à gauche verticalement centré
        floorLabel.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-border-color: black;" +      // couleur de la bordure
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 4;" +
                        "-fx-background-radius: 4;" +
                        "-fx-padding: 5 10 5 10;"         // padding interne pour le texte
        );
        floorSelector = new ComboBox<>();

        // Par exemple 3 étages pour test, tu peux changer
        for (int i = 0; i < 3; i++) {
            String label;
            if (i < 26) {
                char letter = (char) ('A' + i);
                label = letter + String.valueOf(i + 1);
            } else {
                label = HELBHotel_Controller.getFloorLabel(i);
            }
            floorSelector.getItems().add(label);
        }

        floorSelector.getSelectionModel().selectFirst();

        floorSelector.setOnAction(e -> {
            int selectedIndex = floorSelector.getSelectionModel().getSelectedIndex();
            controller.handleFloorSelection(selectedIndex);
        });

        selectorBox.getChildren().addAll(floorLabel, floorSelector);

        // --- Verify Button Box ---
        HBox verifyButtonBox = new HBox();
        verifyButtonBox.setAlignment(Pos.CENTER_RIGHT); // aligné à droite
        verifyButtonBox.setPadding(new Insets(0, 10, 0, 0)); // padding droite
        verifyButtonBox.setSpacing(0);

        Button verifyButton = new Button("Verify Code");
        verifyButton.setStyle(String.format(
                """
                -fx-background-color: %s;
                -fx-text-fill: %s;
                -fx-font-size: %dpx;
                -fx-padding: %dpx;
                -fx-border-radius: 5;
                """, BUTTON_BACKGROUND_COLOR, BUTTON_TEXT_FILL, BUTTON_FONT_SIZE, BUTTON_PADDING
        ));
        verifyButton.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);

        verifyButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Verification");
            alert.setHeaderText(null);
            alert.setContentText("Button clicked");
            alert.showAndWait();
        });

        verifyButtonBox.getChildren().add(verifyButton);

        // --- Top box contenant selectorBox à gauche et verifyButtonBox à droite ---
        HBox topBox = new HBox();
        topBox.setSpacing(20);
        topBox.setPadding(new Insets(0, 0, 10, 0));  // padding bottom
        topBox.setMaxWidth(Double.MAX_VALUE);
        topBox.setAlignment(Pos.CENTER_LEFT);

        // selectorBox va grandir, verifyButtonBox reste à droite
        HBox.setHgrow(selectorBox, Priority.ALWAYS);
        selectorBox.setMaxWidth(Double.MAX_VALUE);

        verifyButtonBox.setAlignment(Pos.CENTER_RIGHT);

        topBox.getChildren().addAll(selectorBox, verifyButtonBox);

        // Ajouter topBox et mainContent dans mainWrapper
        mainWrapper.getChildren().addAll(topBox, mainContent);

        // Scroll global
        ScrollPane outerScroll = new ScrollPane(mainWrapper);
        outerScroll.setFitToWidth(true);
        outerScroll.setFitToHeight(true);
        root.getChildren().add(outerScroll);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();

    }

    // ... (le reste de ta classe sans modification)

    /** Ajoute la légende en haut du mainWrapper */
    public void setupLegend() {
        HBox legendBox = new HBox(LEGEND_BOX_SPACING);
        legendBox.setPadding(new Insets(LEGEND_PADDING));
        legendBox.setStyle("-fx-alignment: center-left;");
        legendBox.getChildren().addAll(
                createLegend("Luxe",       "#D8C4EC"),
                createLegend("Business",   "#BFDFFF"),
                createLegend("Economique", "#FFE5B4")
        );
        mainWrapper.getChildren().add(0, legendBox);
    }

    /** Construit la grille des chambres à partir de la configuration */
    public void setupRoomGrid(List<List<String>> config) {
        GridPane grid = new GridPane();
        grid.setHgap(18);
        grid.setVgap(20);
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);

        int compteur = 1;
        for (int row = 0; row < config.size(); row++) {
            for (int col = 0; col < config.get(row).size(); col++) {
                String type = config.get(row).get(col);
                if ("Z".equals(type)) continue;
                String labelText = compteur++ + type;
                Label lbl = new Label(labelText);
                lbl.setPrefSize(70, 70);
                lbl.setAlignment(Pos.CENTER);
                String bgColor = switch (type) {
                    case "B" -> "#BFDFFF";
                    case "E" -> "#FFE5B4";
                    case "L" -> "#D8C4EC";
                    default -> "white";
                };
                lbl.setStyle(String.format(
                        """
                        -fx-background-color: %s;
                        -fx-border-color: %s;
                        -fx-border-width: 1;
                        -fx-border-radius: 4;
                        -fx-background-radius: 4;
                        -fx-font-size: 14px;
                        """, bgColor, BORDER_COLOR
                ));
                grid.add(lbl, col, row);

            }
        }
        ((StackPane)leftPanel.getChildren().get(0)).getChildren().add(grid);
    }

    /** Construit la liste de boutons de réservation */
    public void setupReservations(List<Reservation> reqs) {
        for (Reservation r : reqs) {
            String text = String.format("%s.%s", r.prenom.charAt(0), r.nom);
            Button btn = new Button(text);
            btn.setStyle(String.format(
                    """
                    -fx-background-color: %s;
                    -fx-text-fill: %s;
                    -fx-font-size: %dpx;
                    -fx-padding: %dpx;
                    -fx-border-radius: 5;
                    """, BUTTON_BACKGROUND_COLOR, BUTTON_TEXT_FILL, BUTTON_FONT_SIZE, BUTTON_PADDING
            ));
            btn.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);
            btn.setOnAction(e -> controller.handleReservationSelection(r));
            buttonPanel.getChildren().add(btn);
        }
    }

    /** Popup d’information sur la réservation */
    public void showReservationPopup(String prenom, String nom) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informations de Réservation");
        alert.setHeaderText(null);
        alert.setContentText("Prénom: " + prenom + "\nNom: " + nom);
        alert.showAndWait();
    }

    /** Crée un item de légende */
    private HBox createLegend(String text, String color) {
        HBox box = new HBox(HBOX_SPACING);
        box.setStyle("-fx-alignment: center;");

        Label lbl = new Label(text);
        lbl.setPrefWidth(LABEL_WIDTH);
        lbl.setMinHeight(LABEL_HEIGHT);
        lbl.setStyle(String.format(
                """
                -fx-font-size: %dpx;
                -fx-font-weight: bold;
                -fx-padding: 4 8;
                -fx-background-color: white;
                -fx-border-color: %s;
                -fx-border-width: 1;
                -fx-border-radius: 4;
                -fx-background-radius: 4;
                -fx-alignment: center;
                """, TEXT_FONT_SIZE, BORDER_COLOR
        )) ;

        Region colorBox = new Region();
        colorBox.setPrefSize(COLOR_BOX_WIDTH, COLOR_BOX_HEIGHT);
        colorBox.setStyle(String.format(
                """
                -fx-background-color: %s;
                -fx-border-color: %s;
                -fx-border-width: 1;
                -fx-border-radius: 4;
                -fx-background-radius: 4;
                """, color, BORDER_COLOR
        ));

        box.getChildren().addAll(lbl, colorBox);
        return box;
    }

    public void setupFloorSelector(int nombreEtages) {
        // Cette méthode n'est plus utilisée dans ce code, car floorSelector est intégré dans le constructeur.
    }

    public void setupRoomGrid(List<List<String>> config, String floorPrefix) {
        GridPane grid = new GridPane();
        grid.setHgap(18);
        grid.setVgap(20);
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);

        int compteur = 1;
        for (int row = 0; row < config.size(); row++) {
            for (int col = 0; col < config.get(row).size(); col++) {
                String type = config.get(row).get(col);
                if ("Z".equals(type)) continue;
                String labelText = floorPrefix + compteur++ + type;

                Label lbl = new Label(labelText);
                lbl.setPrefSize(70, 70);
                lbl.setAlignment(Pos.CENTER);
                String bgColor = switch (type) {
                    case "B" -> "#BFDFFF";
                    case "E" -> "#FFE5B4";
                    case "L" -> "#D8C4EC";
                    default -> "white";
                };
                lbl.setStyle(String.format(
                        """
                        -fx-background-color: %s;
                        -fx-border-color: %s;
                        -fx-border-width: 1;
                        -fx-border-radius: 4;
                        -fx-background-radius: 4;
                        -fx-font-size: 14px;
                        """, bgColor, BORDER_COLOR
                ));
                grid.add(lbl, col, row);
            }
        }

        StackPane wrapper = (StackPane) leftPanel.getChildren().get(0);
        wrapper.getChildren().clear();
        wrapper.getChildren().add(grid);
    }

}
