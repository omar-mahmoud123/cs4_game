package game.gui;

import game.engine.monsters.Monster;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameOverController {

    @FXML
    private Label opponentEnergyLabel;

    @FXML
    private Label playerEnergyLabel;

    @FXML
    private Label winnerLabel;

    @FXML
    private Button returnButton;

    public void setGameData(Monster winner, Monster player, Monster opponent) {
        winnerLabel.setText(winner.getName() + " (" + winner.getRole() + ") WINS!");
        
        playerEnergyLabel.setText(player.getName() + " Final Energy: " + player.getEnergy());
        opponentEnergyLabel.setText(opponent.getName() + " Final Energy: " + opponent.getEnergy());
    }

    @FXML
    void onReturnButtonClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StartView.fxml"));
            Parent root = loader.load();

            javafx.scene.Node source = (javafx.scene.Node) event.getSource();
            Stage window = (Stage) source.getScene().getWindow();
            
            window.setScene(new Scene(root, 600, 500)); 
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
