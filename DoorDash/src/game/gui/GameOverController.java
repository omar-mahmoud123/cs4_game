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

    // el parameters passed by el BoardController
    public void setGameData(Monster winner, Monster player, Monster opponent) {
    	// passed el moster msh el string 3shan cleaner badal ma n pass more parameters bengeeb eli 3ayzeeno mn el monster
        winnerLabel.setText(winner.getName() + " (" + winner.getRole() + ") WINS!");
        
        playerEnergyLabel.setText(player.getName() + " Final Energy: " + player.getEnergy());
        opponentEnergyLabel.setText(opponent.getName() + " Final Energy: " + opponent.getEnergy());
    }

    @FXML
    void onReturnButtonClicked(ActionEvent event) {
        try {
        	// hat el StartView
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StartView.fxml"));
            // 7awelo le java objects
            Parent root = loader.load();
            
            // hena bengeeb el source node using el event msh button mo3ayan
            javafx.scene.Node source = (javafx.scene.Node) event.getSource();
            // bengeeb el current window eli heya el window bta3t el node
            Stage window = (Stage) source.getScene().getWindow();
            
            // bn swap el window lel StartView
            window.setScene(new Scene(root, 600, 500)); 
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
