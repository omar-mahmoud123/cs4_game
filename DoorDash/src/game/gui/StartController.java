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
        Role selectedRole = null;
        if (scarerRadio.isSelected()) {
            selectedRole = Role.SCARER;
        } else if (laugherRadio.isSelected()) {
            selectedRole = Role.LAUGHER;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select either SCARER or LAUGHER to begin!");
            alert.showAndWait();
            // law ma7asalsh return hey7awel y load game with null role
            return;
        }

        try {
        	// ne3mel new game engine
            Game newGame = new Game(selectedRole);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("BoardView.fxml"));
            Parent root = loader.load();

            BoardController boardController = loader.getController();
            boardController.setGame(newGame);

            // bengeeb el window 3shan n8ayarha lel view el tany
            Stage window = (Stage) scarerRadio.getScene().getWindow();
            window.setScene(new Scene(root, 1000, 700));
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed to load the game board: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
