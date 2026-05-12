package game.gui;

import game.engine.Game;
import game.engine.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class StartController {

    @FXML
    private RadioButton laugherRadio;

    @FXML
    private ToggleGroup roleToggleGroup;

    @FXML
    private RadioButton scarerRadio;
    @FXML
    void onStartButtonClicked(ActionEvent event) {
        // 1. Determine which role the user selected
        Role selectedRole = null;
        if (scarerRadio.isSelected()) {
            selectedRole = Role.SCARER;
        } else if (laugherRadio.isSelected()) {
            selectedRole = Role.LAUGHER;
        } else {
            // Force the user to pick a side!
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select either SCARER or LAUGHER to begin!");
            alert.showAndWait();
            return;
        }

        // 2. Initialize the Game and load the Board View
        try {
            // Instantiate the backend engine
            Game newGame = new Game(selectedRole);

            // Load the FXML for the board
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BoardView.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the engine to it
            BoardController boardController = loader.getController();
            boardController.setGame(newGame); // This triggers drawBoard()!

            // Get the current window (Stage) and change the scene
            Stage window = (Stage) scarerRadio.getScene().getWindow();
            window.setScene(new Scene(root, 1350, 750));
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed to load the game board: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
