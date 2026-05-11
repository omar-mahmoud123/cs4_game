package game.gui.controller;

import java.io.IOException;
import java.util.Random;

import game.engine.Game;
import game.engine.Role;
import game.engine.exceptions.GameActionException;
import game.gui.Main;
import game.gui.view.EndScreen;
import game.gui.view.GameView;
import game.gui.view.StartMenu;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GameController {
    private Main mainApp;
    private Game game;
    private GameView gameView;

    public GameController(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void showStartMenu() {
        StartMenu startMenu = new StartMenu(this);
        Scene scene = new Scene(startMenu.getRoot(), 1000, 750);
        scene.getStylesheets().add(getClass().getResource("/game/gui/assets/style.css").toExternalForm());
        mainApp.getPrimaryStage().setScene(scene);
        mainApp.getPrimaryStage().show();
    }

    public void startGame(Role playerRole) {
        try {
            this.game = new Game(playerRole);
            this.gameView = new GameView(this, game);
            Scene scene = new Scene(gameView.getRoot(), 1400, 950);
            scene.getStylesheets().add(getClass().getResource("/game/gui/assets/style.css").toExternalForm());
            mainApp.getPrimaryStage().setScene(scene);
            
            // Initial render
            gameView.updateView();
        } catch (IOException e) {
            mainApp.showErrorDialog("Error Loading Game", "Could not load game data (CSV files missing or malformed).");
        }
    }

    public void rollDice() {
        int roll = new Random().nextInt(6) + 1;
        
        gameView.showDiceRoll(roll, () -> {
            try {
                game.playTurn(); 
                checkWinCondition();
                gameView.updateView();
            } catch (GameActionException e) {
                gameView.showErrorAnimation(e.getMessage());
                gameView.updateView();
            } catch (Exception e) {
                gameView.showErrorAnimation("Error: " + e.getMessage());
            }
        });
    }

    public void usePowerup() {
        try {
            game.usePowerup();
            gameView.updateView();
            gameView.getGameLog().setText(game.getCurrent().getName() + " used a powerup!");
            gameView.getGameLog().setStyle("-fx-font-size: 16px; -fx-text-fill: #50fa7b;");
        } catch (GameActionException e) {
            gameView.showErrorAnimation(e.getMessage());
        } catch (Exception e) {
            gameView.showErrorAnimation("Error: " + e.getMessage());
        }
    }

    private void checkWinCondition() {
        if (game.getWinner() != null) {
            EndScreen endScreen = new EndScreen(this, game.getWinner(), game.getPlayer(), game.getOpponent());
            Scene scene = new Scene(endScreen.getRoot(), 1000, 800);
            scene.getStylesheets().add(getClass().getResource("/game/gui/assets/style.css").toExternalForm());
            mainApp.getPrimaryStage().setScene(scene);
        }
    }

    public void showInfoDialog(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
