package game.gui;

import game.engine.Board;
import game.engine.Game;
import game.engine.cards.Card;
import game.engine.cells.*;
import game.engine.exceptions.InvalidMoveException;
import game.engine.exceptions.OutOfEnergyException;
import game.engine.monsters.Dasher;
import game.engine.monsters.Monster;
import game.engine.monsters.MultiTasker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
public class BoardController {

    @FXML
    private GridPane boardGrid;

    @FXML
    private Label currentTurnLabel;

    @FXML
    private Label gameMessageLabel;

    @FXML
    private Label opponentActiveStatusEffectsLabel;

    @FXML
    private Label opponentCurrentEnergyLabel;

    @FXML
    private Label opponentCurrentPositionLabel;

    @FXML
    private Label opponentCurrentRoleLabel;

    @FXML
    private Label opponentNameLabel;

    @FXML
    private Label opponentOriginalRoleLabel;

    @FXML
    private Label opponentTypeLabel;

    @FXML
    private Label playerActiveStatusEffectsLabel;

    @FXML
    private Label playerCurrentEnergyLabel;

    @FXML
    private Label playerCurrentPositionLabel;

    @FXML
    private Label playerCurrentRoleLabel;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label playerOriginalRoleLabel;

    @FXML
    private Label playerTypeLabel;

    @FXML
    private Label resultOfLastDiceRollLabel;

    @FXML
    private Button rollDiceButton;

    @FXML
    private CheckBox usePowerupCheckbox;

    private Game game;
    private StackPane[] cellUIs = new StackPane[100]; 

    private Circle playerIcon;
    private Circle opponentIcon;

    // bteb2a called automatically by the FXMLLoader
    // bt5aly el tokens msh foo2 ba3d
    @FXML
    public void initialize() {
        playerIcon = new Circle(10, Color.BLUE);
        playerIcon.setTranslateX(-15);
        
        opponentIcon = new Circle(10, Color.RED);
        opponentIcon.setTranslateX(15);
        
        // attach listener 3shan el cheats
        boardGrid.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::handleKeyCheats);
            }
        });
    }
    private void handleKeyCheats(KeyEvent event) {
    	// law el game lesa mabada2sh 3shan el spam bas mayermeesh errors
        if (game == null) return;

        // el event howa ay keystroke
        switch (event.getCode()) {
            case W:
                game.getCurrent().setPosition(99);
                
                gameMessageLabel.setText("CHEAT ACTIVATED: Player warped to the final cell!");
                updateUI(); 
                break;
                
            case E:
                Monster currentMonster = game.getCurrent();
                
                // zawed 100 energy lel curent
                currentMonster.alterEnergy(100);
                gameMessageLabel.setText("CHEAT ACTIVATED: " + currentMonster.getName() + " gained 100 Energy!");
                // refresh labels 3shan el new energy
                updateUI();
                break;
            // law 3amal ay keystroke tanya ignore
            default:
                break;
        }
    }

    public void setGame(Game game) {
        this.game = game;
        drawBoard();
        updateUI();
        gameMessageLabel.setText("Game Started! Welcome to the Floor.");
    }
    
    // btreset el board w tersemha tany
    private void drawBoard() {
    	// lazem temsa7 eli fat 3shan law masalan dost play again aw fi door ba2a activated
        boardGrid.getChildren().clear();
        // bengeeb el cell data mn el backend
        Cell[][] engineBoard = game.getBoard().getBoardCells();

        for (int i = 0; i < 100; i++) {
            int engineRow = (i / 10);
            int engineCol = (i % 10);
            // fel odd rows el cells btebda2 mn el ymeen lel shemal 3shan yeb2a nafs el shakl el matloob
            if (engineRow % 2 == 1) {
                engineCol = 9 - engineCol;
            }
            
            Cell cell = engineBoard[engineRow][engineCol];
            
            // da el box eli hayb2a 3ala kol cell
            StackPane cellView = new StackPane();
            cellView.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

            // ba3mel el text eli 3ala el cell
            // el cell index eli maktoob
            String cellText = String.valueOf(i);
            
            if (cell instanceof DoorCell) {
                boolean isExhausted = ((DoorCell)cell).isActivated();
                String energy = String.valueOf(((DoorCell)cell).getEnergy());
                
                // law msh exausted el door cell loono light blue
                cellView.setStyle("-fx-background-color: lightblue; -fx-border-color: black;");
                // law exhausted byeb2a gray
                if (isExhausted) {
                    cellView.setStyle("-fx-background-color: darkgray; -fx-border-color: black;");
                }
                // add energy value
                cellText += "\nEnergy: " + energy;
            } 
            else if (cell instanceof MonsterCell) {
            	cellView.setStyle("-fx-background-color: dodgerblue; -fx-border-color: black;");
                
                MonsterCell mCell = (MonsterCell) cell;
                
                String monsterName = mCell.getCellMonster().getName();
                
                cellText += "\n" + monsterName;
            } 
            else if (cell instanceof CardCell) {
                cellView.setStyle("-fx-background-color: pink; -fx-border-color: black;");
                cellText += "\nCard";
            } 
            else if (cell instanceof ConveyorBelt) {
                cellView.setStyle("-fx-background-color: lightgreen; -fx-border-color: black;");
                cellText += "\nBelt";
            } 
            else if (cell instanceof ContaminationSock) {
                cellView.setStyle("-fx-background-color: orange; -fx-border-color: black;");
                cellText += "\nSock";
            } 
            else {
                cellView.setStyle("-fx-background-color: lightyellow; -fx-border-color: black;");
            }

            Text label = new Text(cellText);
            // law el text 3ada el 55 pixel yeb2a wrap it around 3shan el cells matse7sh 3ala ba3d
            label.setWrappingWidth(55);
            label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            // 7ot el text 3ala el cell
            cellView.getChildren().add(label);

            // 7ot el cell views (StackPane) fe list 3shan testa5demha ba3deen
            cellUIs[i] = cellView;

            // fel 3ady el rows btebda2 mn foo2 fa e7na 3ayzeen n render mn a5r row el awl 3shan el zero teb2a ta7t
            int guiRow = 9 - engineRow;
            
            // 7ot el cell fel board pane
            boardGrid.add(cellView, engineCol, guiRow);
        }
    }
    
    
    // ba3d kol action (button clicked, card drawn etc) el backend byet8ayar fa me7tageen n sync el frontend m3ah
    private void updateUI() {
        Monster player = game.getPlayer();
        Monster opponent = game.getOpponent();

        playerNameLabel.setText("Name: " + player.getName());
        playerOriginalRoleLabel.setText("Original Role: " + player.getOriginalRole());
        playerCurrentRoleLabel.setText("Current Role: " + player.getRole() + 
            (player.isConfused() ? " (Confused for " + player.getConfusionTurns() + " turns)" : ""));
        playerTypeLabel.setText("Type: " + player.getClass().getSimpleName());
        playerCurrentEnergyLabel.setText("Current Energy: " + player.getEnergy());
        playerCurrentPositionLabel.setText("Current Position: " + player.getPosition());
        
        String playerStatus = "";
        if (player.isFrozen()) playerStatus += "[Frozen] ";
        if (player.isShielded()) playerStatus += "[Shielded] ";
        if (player.isConfused()) playerStatus += "[Confused: " + player.getConfusionTurns() + " left] ";

        if (player instanceof Dasher) {
            int momentum = ((Dasher) player).getMomentumTurns();
            if (momentum > 0) playerStatus += "[Momentum Rush: " + momentum + " left] ";
        } 
        else if (player instanceof MultiTasker) {
            int focus = ((MultiTasker) player).getNormalSpeedTurns();
            if (focus > 0) playerStatus += "[Focus Mode: " + focus + " left] ";
        }

        playerActiveStatusEffectsLabel.setText("Active Status: " + (playerStatus.isEmpty() ? "None" : playerStatus));

        opponentNameLabel.setText("Name: " + opponent.getName());
        opponentOriginalRoleLabel.setText("Original Role: " + opponent.getOriginalRole());
        opponentCurrentRoleLabel.setText("Current Role: " + opponent.getRole() + 
            (opponent.isConfused() ? " (Confused for " + opponent.getConfusionTurns() + " turns)" : ""));
        opponentTypeLabel.setText("Type: " + opponent.getClass().getSimpleName());
        opponentCurrentEnergyLabel.setText("Current Energy: " + opponent.getEnergy());
        opponentCurrentPositionLabel.setText("Current Position: " + opponent.getPosition());
        
        String oppStatus = "";
        if (opponent.isFrozen()) oppStatus += "[Frozen] ";
        if (opponent.isShielded()) oppStatus += "[Shielded] ";
        if (opponent.isConfused()) oppStatus += "[Confused: " + opponent.getConfusionTurns() + " left] ";

        if (opponent instanceof Dasher) {
            int momentum = ((Dasher) opponent).getMomentumTurns();
            if (momentum > 0) oppStatus += "[Momentum Rush: " + momentum + " left] ";
        } 
        else if (opponent instanceof MultiTasker) {
            int focus = ((MultiTasker) opponent).getNormalSpeedTurns();
            if (focus > 0) oppStatus += "[Focus Mode: " + focus + " left] ";
        }

        opponentActiveStatusEffectsLabel.setText("Active Status: " + (oppStatus.isEmpty() ? "None" : oppStatus));

        currentTurnLabel.setText("Current Turn: " + game.getCurrent().getName());

        // 3ashan law door activated y turn gray
        drawBoard(); 

        // 7ot el player wel opponent icons f makanhom
        cellUIs[player.getPosition()].getChildren().add(playerIcon);
        cellUIs[opponent.getPosition()].getChildren().add(opponentIcon);
        
        // law 7ad keseb bn call handleGameOver
        if (game.getWinner() != null) {
            handleGameOver(game.getWinner());
        }
    }

    @FXML
    void onRollDiceClicked(ActionEvent event) {
    	// bn7ot fe try 3shan n3ml popup law el player we2e3 3al opponent masalan badal ma y crash
        try {
            if (usePowerupCheckbox.isSelected()) {
                game.usePowerup();
                gameMessageLabel.setText(game.getCurrent().getName() + " used their powerup!");
             // uncheck lel next turn
                usePowerupCheckbox.setSelected(false);
            }
            
            // abl ma n playTurn m7tageen n save el current player 
            String turnName = game.getCurrent().getName();
            // n save law kan frozen abl mayl3ab 3shan nekteb eno kan skipped
            boolean wasFrozen = game.getCurrent().isFrozen();
            
            // 3shan mn3mlsh print le card mn el door eli fat
            Board.lastDrawnCard = null; 
            
            // bte3mel kol 7aga fel backend
            game.playTurn();

            if (wasFrozen) {
                gameMessageLabel.setText(turnName + " was frozen and skipped their turn!");
            } else {
                resultOfLastDiceRollLabel.setText("Result of last dice roll: " + game.getLastRoll());
                
                // law lastDrawnCard msh null yeb2a akid sa7ab card this turn
                if (Board.lastDrawnCard != null) {
                    Card drawnCard = Board.lastDrawnCard;
                    
                    gameMessageLabel.setText(turnName + " landed on a Card Cell!\n" +
                        "Drew: " + drawnCard.getName() + " - " + drawnCard.getDescription());
                        
                } else {
                    gameMessageLabel.setText(turnName + " rolled a " + game.getLastRoll() + "!");
                }
            }

            // lazem n updat 3shan n7arak el tokens
            updateUI();

        } catch (OutOfEnergyException e) {
            showErrorPopup("Powerup Failed", e.getMessage());
            usePowerupCheckbox.setSelected(false);
        } catch (InvalidMoveException e) {
            showErrorPopup("Invalid Move", e.getMessage());
        } catch (Exception e) {
            showErrorPopup("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }
    
    // 3shan manektebhash kaza mara fel updateUI exceptions
    private void showErrorPopup(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        // bet5aly el popup modal 3shan el execution y wa2af l7ad ma te2fel el alert 
        alert.showAndWait();
    }

    
    private void handleGameOver(Monster winner) {
    	// 3shan law el user 3amal y spam roll dice awl 7aga n disable 3shan el errors
        rollDiceButton.setDisable(true);
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverView.fxml"));
            Parent root = loader.load();

            GameOverController gameOverController = loader.getController();
            // bn pass el results lel gameOverView
            gameOverController.setGameData(winner, game.getPlayer(), game.getOpponent());

            Stage window = (Stage) rollDiceButton.getScene().getWindow();
         // betbadel el window lel gamOver
            window.setScene(new Scene(root, 600, 500));
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
            showErrorPopup("Game Over Error", "Could not load Game Over screen.");
        }
    }
    
}
