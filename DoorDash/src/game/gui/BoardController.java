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

    @FXML
    public void initialize() {
        playerIcon = new Circle(10, Color.BLUE);
        playerIcon.setTranslateX(-15);
        
        opponentIcon = new Circle(10, Color.RED);
        opponentIcon.setTranslateX(15);
        
        boardGrid.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::handleKeyCheats);
            }
        });
    }
    /**
     * Handles keyboard presses for Evaluation Cheats
     */
    private void handleKeyCheats(KeyEvent event) {
        if (game == null) return; // Ignore if game hasn't started yet

        switch (event.getCode()) {
            case W:
                // CHEAT 1: Teleport player to 99 (Energy remains exactly as it is)
                game.getCurrent().setPosition(99);
                
                gameMessageLabel.setText("CHEAT ACTIVATED: Player warped to the final cell!");
                updateUI(); 
                break;
                
            case E:
                // CHEAT 2: +100 Energy to current monster
                Monster currentMonster = game.getCurrent();
                
                // Using alterEnergy() because it is much cleaner than setEnergy(getEnergy() + 100)
                currentMonster.alterEnergy(100);
                gameMessageLabel.setText("CHEAT ACTIVATED: " + currentMonster.getName() + " gained 100 Energy!");
                updateUI(); // Refresh the labels to show the new energy
                break;

            default:
                break; // Ignore any other keys
        }
    }

    public void setGame(Game game) {
        this.game = game;
        drawBoard();
        updateUI();
        gameMessageLabel.setText("Game Started! Welcome to the Floor.");
    }

    private void drawBoard() {
        boardGrid.getChildren().clear();
        Cell[][] engineBoard = game.getBoard().getBoardCells();

        for (int i = 0; i < 100; i++) {
            int engineRow = (i / 10);
            int engineCol = (i % 10);
            if (engineRow % 2 == 1) {
                engineCol = 9 - engineCol;
            }
            
            Cell cell = engineBoard[engineRow][engineCol];
            
            StackPane cellView = new StackPane();
            cellView.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

            String cellText = String.valueOf(i);
            
            if (cell instanceof DoorCell) {
                boolean isExhausted = ((DoorCell)cell).isActivated();
                String energy = String.valueOf(((DoorCell)cell).getEnergy());
                
                cellView.setStyle("-fx-background-color: lightblue; -fx-border-color: black;");
                if (isExhausted) {
                    cellView.setStyle("-fx-background-color: darkgray; -fx-border-color: black;");
                }
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
            label.setWrappingWidth(55);
            label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            cellView.getChildren().add(label);

            cellUIs[i] = cellView;

            int guiRow = 9 - engineRow;
            boardGrid.add(cellView, engineCol, guiRow);
        }
    }

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

        drawBoard(); 

        cellUIs[player.getPosition()].getChildren().add(playerIcon);
        cellUIs[opponent.getPosition()].getChildren().add(opponentIcon);
        
        if (game.getWinner() != null) {
            handleGameOver(game.getWinner());
        }
    }

    @FXML
    void onRollDiceClicked(ActionEvent event) {
        try {
            if (usePowerupCheckbox.isSelected()) {
                game.usePowerup();
                gameMessageLabel.setText(game.getCurrent().getName() + " used their powerup!");
                usePowerupCheckbox.setSelected(false); // uncheck it for next turn
            }

            String turnName = game.getCurrent().getName();
            boolean wasFrozen = game.getCurrent().isFrozen();
            
            Board.lastDrawnCard = null; 
            
            game.playTurn();

            if (wasFrozen) {
                gameMessageLabel.setText(turnName + " was frozen and skipped their turn!");
            } else {
                resultOfLastDiceRollLabel.setText("Result of last dice roll: " + game.getLastRoll());
                
                if (Board.lastDrawnCard != null) {
                    Card drawnCard = Board.lastDrawnCard;
                    
                    gameMessageLabel.setText(turnName + " landed on a Card Cell!\n" +
                        "Drew: " + drawnCard.getName() + " - " + drawnCard.getDescription());
                        
                } else {
                    gameMessageLabel.setText(turnName + " rolled a " + game.getLastRoll() + "!");
                }
            }

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

    private void showErrorPopup(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleGameOver(Monster winner) {
        rollDiceButton.setDisable(true); // Stop playing
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverView.fxml"));
            Parent root = loader.load();

            GameOverController gameOverController = loader.getController();
            
            gameOverController.setGameData(winner, game.getPlayer(), game.getOpponent());

            Stage window = (Stage) rollDiceButton.getScene().getWindow();
            window.setScene(new Scene(root, 600, 500));
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
            showErrorPopup("Game Over Error", "Could not load Game Over screen.");
        }
    }
    
}
