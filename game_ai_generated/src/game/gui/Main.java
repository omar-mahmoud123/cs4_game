package game.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import game.gui.controller.GameController;

public class Main extends Application {
    
    private Stage primaryStage;
    private GameController controller;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("DoorDash: Scare vs Laugh Touchdown");
        
        // Ensure the game can be terminated by clicking 'X'
        this.primaryStage.setOnCloseRequest(e -> System.exit(0));
        
        try {
            this.controller = new GameController(this);
            this.controller.showStartMenu();
        } catch (Exception e) {
            showErrorDialog("Startup Error", "Failed to start the game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void showErrorDialog(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
