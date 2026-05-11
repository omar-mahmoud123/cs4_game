package game.gui.view;

import game.engine.monsters.Monster;
import game.gui.controller.GameController;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EndScreen {
    private VBox root;

    public EndScreen(GameController controller, Monster winner, Monster p1, Monster p2) {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        
        VBox glass = new VBox(30);
        glass.setAlignment(Pos.CENTER);
        glass.getStyleClass().add("glass-panel");
        glass.setMaxWidth(500);
        
        Label title = new Label("VICTORY");
        title.getStyleClass().add("text-header");
        title.setStyle("-fx-font-size: 50px; -fx-text-fill: #50fa7b;");
        
        Label winnerLabel = new Label(winner.getName());
        winnerLabel.getStyleClass().add("text-header");
        winnerLabel.setStyle("-fx-font-size: 32px; -fx-text-fill: white;");
        
        Label roleLabel = new Label(winner.getRole().toString());
        roleLabel.getStyleClass().add("text-sub");

        VBox stats = new VBox(10);
        stats.setAlignment(Pos.CENTER);
        stats.getChildren().addAll(
            createStatLabel(p1.getName() + " Final Energy: " + p1.getEnergy()),
            createStatLabel(p2.getName() + " Final Energy: " + p2.getEnergy())
        );

        Button replayBtn = new Button("RETURN TO MENU");
        replayBtn.getStyleClass().addAll("button", "btn-primary");
        replayBtn.setPrefWidth(250);
        replayBtn.setPrefHeight(50);
        replayBtn.setOnAction(e -> controller.showStartMenu());

        glass.getChildren().addAll(title, winnerLabel, roleLabel, stats, replayBtn);
        root.getChildren().add(glass);
    }

    private Label createStatLabel(String text) {
        Label l = new Label(text);
        l.getStyleClass().add("text-normal");
        return l;
    }

    public VBox getRoot() {
        return root;
    }
}
