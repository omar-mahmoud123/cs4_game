package game.gui.view;

import game.engine.Role;
import game.gui.controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;

public class StartMenu {
    private VBox root;
    private GameController controller;
    private Role selectedRole = Role.SCARER;

    public StartMenu(GameController controller) {
        this.controller = controller;
        root = new VBox(40);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));

        VBox glassContainer = new VBox(30);
        glassContainer.setAlignment(Pos.CENTER);
        glassContainer.getStyleClass().add("glass-panel");
        glassContainer.setMaxWidth(600);
        glassContainer.setPadding(new Insets(40));

        Label title = new Label("DOORDASH");
        title.getStyleClass().add("text-header");
        title.setStyle("-fx-font-size: 60px; -fx-text-fill: linear-gradient(to bottom, #8be9fd, #bd93f9);");
        
        Label subtitle = new Label("SCARE VS LAUGH TOUCHDOWN");
        subtitle.getStyleClass().add("text-sub");
        subtitle.setStyle("-fx-letter-spacing: 5px;");

        Label prompt = new Label("CHOOSE YOUR FACTION");
        prompt.getStyleClass().add("text-normal");
        prompt.setStyle("-fx-font-weight: bold; -fx-opacity: 0.7;");

        ToggleGroup roleGroup = new ToggleGroup();
        
        ToggleButton scarerBtn = new ToggleButton("SCARER");
        scarerBtn.setToggleGroup(roleGroup);
        scarerBtn.setSelected(true);
        scarerBtn.getStyleClass().add("button");
        scarerBtn.setStyle("-fx-background-color: #ff5555; -fx-text-fill: white; -fx-font-size: 18px;");
        scarerBtn.setPrefWidth(150);
        scarerBtn.setOnAction(e -> selectedRole = Role.SCARER);

        ToggleButton laugherBtn = new ToggleButton("LAUGHER");
        laugherBtn.setToggleGroup(roleGroup);
        laugherBtn.getStyleClass().add("button");
        laugherBtn.setStyle("-fx-background-color: #50fa7b; -fx-text-fill: #282a36; -fx-font-size: 18px;");
        laugherBtn.setPrefWidth(150);
        laugherBtn.setOnAction(e -> selectedRole = Role.LAUGHER);

        HBox roleBox = new HBox(30, scarerBtn, laugherBtn);
        roleBox.setAlignment(Pos.CENTER);

        Button startBtn = new Button("ENTER THE ARENA");
        startBtn.getStyleClass().addAll("button", "btn-primary");
        startBtn.setPrefWidth(330);
        startBtn.setPrefHeight(60);
        startBtn.setStyle("-fx-font-size: 20px;");
        startBtn.setOnAction(e -> controller.startGame(selectedRole));

        Button instructionsBtn = new Button("How to Play");
        instructionsBtn.getStyleClass().add("button");
        instructionsBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #8be9fd; -fx-border-color: #8be9fd; -fx-border-radius: 12;");
        instructionsBtn.setOnAction(e -> showInstructions());

        glassContainer.getChildren().addAll(title, subtitle, prompt, roleBox, startBtn, instructionsBtn);
        root.getChildren().add(glassContainer);
    }

    private void showInstructions() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Instructions");
        alert.setHeaderText("How to Play DoorDash");
        alert.setContentText("1. Roll the dice to move your monster.\n" +
                             "2. Use powerups to gain an advantage.\n" +
                             "3. Land on doors, socks, and conveyors to trigger events.\n" +
                             "4. First monster to reach cell 99 with enough energy wins!");
        alert.getDialogPane().setPrefSize(400, 200);
        alert.show();
    }

    public VBox getRoot() {
        return root;
    }
}
