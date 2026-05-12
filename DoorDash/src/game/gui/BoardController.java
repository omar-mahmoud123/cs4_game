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
	
	 // --- Game Engine Variables ---
	    private Game game;
	    // We store the UI cells in an array so we can easily update them later without looping the grid
	    private StackPane[] cellUIs = new StackPane[100]; 
	
	    // Used to represent the monsters on the board
	    private Circle playerIcon;
	    private Circle opponentIcon;
	
	    @FXML
	    public void initialize() {
	        // Initialize the monster UI icons
	        playerIcon = new Circle(10, Color.BLUE);
	        playerIcon.setTranslateX(-15); // Shift player slightly left
	        
	        opponentIcon = new Circle(10, Color.RED);
	        opponentIcon.setTranslateX(15); // Shift opponent slightly right
	    }
	
	    /**
	     * Called from StartController after the game is initialized.
	     */
	    public void setGame(Game game) {
	        this.game = game;
	        drawBoard();
	        updateUI();
	        gameMessageLabel.setText("Game Started! Welcome to the Floor.");
	    }
	
	    /**
	     * Creates the 100 visual cells based on the engine's board array.
	     */
	    private void drawBoard() {
	        boardGrid.getChildren().clear();
	        Cell[][] engineBoard = game.getBoard().getBoardCells();
	
	        for (int i = 0; i < 100; i++) {
	            // Get the specific row/col from your engine's logic
	            int engineRow = (i / 10);
	            int engineCol = (i % 10);
	            if (engineRow % 2 == 1) {
	                engineCol = 9 - engineCol; // Zigzag logic
	            }
	            
	            Cell cell = engineBoard[engineRow][engineCol];
	            
	            StackPane cellView = new StackPane();
	            cellView.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
	
	            // Format the cell background and text based on type
	            String cellText = String.valueOf(i); // Start with index number
	            
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
	            cellView.getChildren().add(label);
	
	            // Store the view in our array so we can add/remove monsters from it easily
	            cellUIs[i] = cellView;
	
	            // GUI Row math: JavaFX puts row 0 at the top, but your engine puts row 0 at the bottom.
	            // We invert the row so cell 0 is at the bottom-left of the screen.
	            int guiRow = 9 - engineRow;
	            boardGrid.add(cellView, engineCol, guiRow);
	        }
	    }
	
	    /**
	     * Updates all labels, board graphics, and checks for a winner.
	     */
	    private void updateUI() {
	        Monster player = game.getPlayer();
	        Monster opponent = game.getOpponent();
	
	        // --- Update Player Stats ---
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
	
	        // Check for specific subclass powerup turns
	        if (player instanceof Dasher) {
	            int momentum = ((Dasher) player).getMomentumTurns();
	            if (momentum > 0) playerStatus += "[Momentum Rush: " + momentum + " left] ";
	        } 
	        else if (player instanceof MultiTasker) {
	            int focus = ((MultiTasker) player).getNormalSpeedTurns();
	            if (focus > 0) playerStatus += "[Focus Mode: " + focus + " left] ";
	        }
	
	        playerActiveStatusEffectsLabel.setText("Active Status: " + (playerStatus.isEmpty() ? "None" : playerStatus));
	
	        // --- Update Opponent Stats ---
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
	
	        // Check for specific subclass powerup turns
	        if (opponent instanceof Dasher) {
	            int momentum = ((Dasher) opponent).getMomentumTurns();
	            if (momentum > 0) oppStatus += "[Momentum Rush: " + momentum + " left] ";
	        } 
	        else if (opponent instanceof MultiTasker) {
	            int focus = ((MultiTasker) opponent).getNormalSpeedTurns();
	            if (focus > 0) oppStatus += "[Focus Mode: " + focus + " left] ";
	        }
	
	        opponentActiveStatusEffectsLabel.setText("Active Status: " + (oppStatus.isEmpty() ? "None" : oppStatus));
	
	        // --- Update Game State Labels ---
	        currentTurnLabel.setText("Current Turn: " + game.getCurrent().getName());

	        // --- Redraw doors FIRST so the board is fresh and exhausted doors turn grey ---
	        drawBoard(); 

	        // --- Update Board Positions ---
	        // 2. Add icons to their new positions on the freshly drawn board
	        cellUIs[player.getPosition()].getChildren().add(playerIcon);
	        cellUIs[opponent.getPosition()].getChildren().add(opponentIcon);
	        
	        // --- Check for Winner ---
	        if (game.getWinner() != null) {
	            handleGameOver(game.getWinner());
	        }
	    }
	
	    @FXML
	    void onRollDiceClicked(ActionEvent event) {
	        try {
	            // 1. Handle Powerup if selected
	            if (usePowerupCheckbox.isSelected()) {
	                game.usePowerup();
	                gameMessageLabel.setText(game.getCurrent().getName() + " used their powerup!");
	                usePowerupCheckbox.setSelected(false); // uncheck it for next turn
	            }
	
	            // 2. Play the Turn
	            String turnName = game.getCurrent().getName();
	            boolean wasFrozen = game.getCurrent().isFrozen();
	            
	            // Clear the card memory BEFORE the turn
	            Board.lastDrawnCard = null; 
	            
	            game.playTurn();
	
	            // 3. Post-Turn Updates
	            if (wasFrozen) {
	                gameMessageLabel.setText(turnName + " was frozen and skipped their turn!");
	            } else {
	                // Update the dice roll label!
	                resultOfLastDiceRollLabel.setText("Result of last dice roll: " + game.getLastRoll());
	                
	                // Check if a card was drawn during the turn
	                if (Board.lastDrawnCard != null) {
	                    // Get the card that was saved
	                    Card drawnCard = Board.lastDrawnCard;
	                    
	                    // Show it in the message log (or use an Alert popup!)
	                    gameMessageLabel.setText(turnName + " landed on a Card Cell!\n" +
	                        "Drew: " + drawnCard.getName() + " - " + drawnCard.getDescription());
	                        
	                    // Optional: If you want a popup instead of just text, you can call:
	                    // showCardPopup(drawnCard.getName(), drawnCard.getEffect());
	                } else {
	                    // Normal move message
	                    gameMessageLabel.setText(turnName + " rolled a " + game.getLastRoll() + "!");
	                }
	            }
	
	            // Refresh visuals (Moves icons, updates energy labels, etc.)
	            updateUI();
	
	        } catch (OutOfEnergyException e) {
	            showErrorPopup("Powerup Failed", e.getMessage());
	            usePowerupCheckbox.setSelected(false);
	        } catch (InvalidMoveException e) {
	            // The game should not be stopped / terminated for any invalid action
	            showErrorPopup("Invalid Move", e.getMessage());
	        } catch (Exception e) {
	            showErrorPopup("Error", "An unexpected error occurred: " + e.getMessage());
	        }
	    }
	
	    /**
	     * Helper method to show error alerts without terminating the game.
	     */
	    private void showErrorPopup(String title, String message) {
	        Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	
	    /**
	     * Handles switching to the Game Over screen.
	     */
	    /**
	     * Handles switching to the Game Over screen.
	     */
	    private void handleGameOver(Monster winner) {
	        rollDiceButton.setDisable(true); // Stop playing
	        
	        try {
	            // Load the Game Over FXML
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverView.fxml"));
	            Parent root = loader.load();

	            // Get the controller and pass the final stats to it
	            GameOverController gameOverController = loader.getController();
	            
	            // Pass the winner, and both players so we can show their final energy
	            gameOverController.setGameData(winner, game.getPlayer(), game.getOpponent());

	            // Get the current window and change the scene
	            Stage window = (Stage) rollDiceButton.getScene().getWindow();
	            window.setScene(new Scene(root, 600, 500)); // Adjust size as needed
	            window.show();

	        } catch (Exception e) {
	            e.printStackTrace();
	            // Fallback to alert just in case the FXML fails to load
	            showErrorPopup("Game Over Error", "Could not load Game Over screen.");
	        }
	    }
	    
	}
