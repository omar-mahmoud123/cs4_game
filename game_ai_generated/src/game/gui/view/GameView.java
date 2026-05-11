package game.gui.view;

import game.engine.Board;
import game.engine.Game;
import game.engine.cells.*;
import game.engine.monsters.Monster;
import game.gui.controller.GameController;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.scene.effect.Glow;
import javafx.scene.effect.DropShadow;

public class GameView {
    private BorderPane root;
    private GameController controller;
    private Game game;
    private GridPane boardGrid;
    private VBox playerStats;
    private VBox opponentStats;
    private Label gameLog;
    private StackPane boardStack;
    
    private Circle p1Token;
    private Circle p2Token;
    
    private final double CELL_STEP = 65.0; // 60px cell + 5px gap

    public GameView(GameController controller, Game game) {
        this.controller = controller;
        this.game = game;
        
        root = new BorderPane();
        root.setPadding(new Insets(25));
        
        playerStats = new VBox(15);
        playerStats.setPrefWidth(280);
        playerStats.setPadding(new Insets(25));
        playerStats.getStyleClass().add("glass-panel");
        
        opponentStats = new VBox(15);
        opponentStats.setPrefWidth(280);
        opponentStats.setPadding(new Insets(25));
        opponentStats.getStyleClass().add("glass-panel");
        
        root.setLeft(playerStats);
        root.setRight(opponentStats);

        boardStack = new StackPane();
        boardGrid = new GridPane();
        boardGrid.setHgap(5);
        boardGrid.setVgap(5);
        boardGrid.setAlignment(Pos.CENTER);
        
        // Tokens directly in StackPane
        p1Token = createToken(Color.web("#8be9fd"), "P1");
        p2Token = createToken(Color.web("#ff5555"), "P2");
        
        boardStack.getChildren().add(boardGrid);
        boardStack.getChildren().addAll(p1Token, p2Token);
        
        // Ensure tokens are centered and ready for translation
        StackPane.setAlignment(p1Token, Pos.CENTER);
        StackPane.setAlignment(p2Token, Pos.CENTER);
        
        root.setCenter(boardStack);
        
        VBox bottomMenu = new VBox(15);
        bottomMenu.setPadding(new Insets(20));
        bottomMenu.getStyleClass().add("glass-panel");
        
        gameLog = new Label("Game Started! Welcome to DoorDash.");
        gameLog.getStyleClass().add("text-normal");
        gameLog.setStyle("-fx-font-size: 16px; -fx-text-fill: #8be9fd;");
        
        Button rollBtn = new Button("ROLL DICE");
        rollBtn.getStyleClass().addAll("button", "btn-primary");
        rollBtn.setPrefWidth(180);
        rollBtn.setPrefHeight(50);
        rollBtn.setOnAction(e -> controller.rollDice());
        
        Button powerupBtn = new Button("POWER UP");
        powerupBtn.getStyleClass().addAll("button", "btn-secondary");
        powerupBtn.setPrefWidth(180);
        powerupBtn.setPrefHeight(50);
        powerupBtn.setOnAction(e -> controller.usePowerup());
        
        HBox actionsBox = new HBox(20, rollBtn, powerupBtn);
        actionsBox.setAlignment(Pos.CENTER_LEFT);
        
        bottomMenu.getChildren().addAll(gameLog, actionsBox);
        root.setBottom(bottomMenu);
        
        animateEntrance();
    }

    private Circle createToken(Color color, String id) {
        Circle c = new Circle(18, color);
        c.setStroke(Color.WHITE);
        c.setStrokeWidth(2);
        c.setEffect(new DropShadow(10, color));
        return c;
    }

    private void animateEntrance() {
        FadeTransition ftL = new FadeTransition(Duration.seconds(1), playerStats);
        ftL.setFromValue(0); ftL.setToValue(1);
        FadeTransition ftR = new FadeTransition(Duration.seconds(1), opponentStats);
        ftR.setFromValue(0); ftR.setToValue(1);
        new ParallelTransition(ftL, ftR).play();
    }

    public BorderPane getRoot() {
        return root;
    }

    public void updateView() {
        renderBoard();
        renderStats();
        updateTokenPositions(true);
    }

    public void showDiceRoll(int result, Runnable onFinish) {
        Timeline timeline = new Timeline();
        for (int i = 0; i < 8; i++) {
            int temp = (int)(Math.random()*6)+1;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * 100), e -> {
                gameLog.setText("Rolling... " + temp);
            }));
        }
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(800), e -> {
            gameLog.setText("Result: " + result);
            onFinish.run();
        }));
        timeline.play();
    }

    private void renderStats() {
        playerStats.getChildren().clear();
        opponentStats.getChildren().clear();
        renderMonsterStats(game.getPlayer(), playerStats, "YOU");
        renderMonsterStats(game.getOpponent(), opponentStats, "OPPONENT");
    }

    private void renderMonsterStats(Monster m, VBox container, String titleText) {
        Label title = new Label(titleText);
        title.getStyleClass().add("text-sub");
        Label name = new Label(m.getName());
        name.getStyleClass().add("text-header");
        name.setStyle("-fx-font-size: 22px;");
        container.getChildren().addAll(title, name, new Region());
        
        container.getChildren().add(createStatRow("Role:", m.getRole().toString()));
        container.getChildren().add(createStatRow("Energy:", String.valueOf(m.getEnergy())));
        container.getChildren().add(createStatRow("Pos:", String.valueOf(m.getPosition())));
        
        HBox statusBox = new HBox(5);
        if (m.isConfused()) statusBox.getChildren().add(createStatusTag("CONFUSED", "#ff79c6"));
        if (m.isFrozen()) statusBox.getChildren().add(createStatusTag("FROZEN", "#8be9fd"));
        if (m.isShielded()) statusBox.getChildren().add(createStatusTag("SHIELDED", "#50fa7b"));
        container.getChildren().add(statusBox);
    }

    private HBox createStatRow(String key, String value) {
        Label k = new Label(key); k.getStyleClass().add("text-normal"); k.setOpacity(0.6);
        Label v = new Label(value); v.getStyleClass().add("text-normal"); v.setStyle("-fx-font-weight: bold;");
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        return new HBox(k, s, v);
    }

    private Label createStatusTag(String t, String c) {
        Label l = new Label(t);
        l.setStyle("-fx-background-color: "+c+"; -fx-text-fill: #282a36; -fx-padding: 2 6; -fx-background-radius: 4; -fx-font-size: 9px; -fx-font-weight: bold;");
        return l;
    }

    private void renderBoard() {
        boardGrid.getChildren().clear();
        Cell[][] cells = game.getBoard().getBoardCells();
        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[r].length; c++) {
                boardGrid.add(new CellNode(cells[r][c], r, c), c, 9 - r);
            }
        }
    }

    private int p1LastPos = 0;
    private int p2LastPos = 0;

    public void updateTokenPositions(boolean animate) {
        int p1Target = game.getPlayer().getPosition();
        int p2Target = game.getOpponent().getPosition();
        
        double offset = (p1Target == p2Target) ? 10 : 0;
        
        moveToken(p1Token, p1LastPos, p1Target, animate, -offset);
        moveToken(p2Token, p2LastPos, p2Target, animate, offset);
        
        p1LastPos = p1Target;
        p2LastPos = p2Target;
    }

    private double getCellX(int index) {
        int[] pos = indexToRowCol(index);
        return (pos[1] - 4.5) * CELL_STEP;
    }

    private double getCellY(int index) {
        int[] pos = indexToRowCol(index);
        return ((9 - pos[0]) - 4.5) * CELL_STEP;
    }

    private void moveToken(Circle token, int startPos, int targetPos, boolean animate, double offset) {
        if (!animate || startPos == targetPos) {
            token.setTranslateX(getCellX(targetPos) + offset);
            token.setTranslateY(getCellY(targetPos));
            return;
        }

        int distance = Math.abs(targetPos - startPos);
        int numJumps = Math.min(distance, 6); // Cap jumps so it doesn't take forever, e.g. every 5 if 30
        int stepSize = (int) Math.ceil((double) distance / numJumps);
        
        SequentialTransition sequence = new SequentialTransition();
        double timePerJump = 3.0 / numJumps; // Total time must be 3 seconds

        int currentPos = startPos;
        for (int i = 0; i < numJumps; i++) {
            int nextPos = currentPos + (targetPos > startPos ? stepSize : -stepSize);
            if (targetPos > startPos && nextPos > targetPos) nextPos = targetPos;
            if (targetPos < startPos && nextPos < targetPos) nextPos = targetPos;
            if (i == numJumps - 1) nextPos = targetPos;
            
            double targetX = getCellX(nextPos) + offset;
            double targetY = getCellY(nextPos);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(timePerJump), token);
            tt.setToX(targetX);
            tt.setToY(targetY);
            tt.setInterpolator(Interpolator.EASE_BOTH);
            
            ScaleTransition hopUp = new ScaleTransition(Duration.seconds(timePerJump / 2), token);
            hopUp.setToX(1.6);
            hopUp.setToY(1.6);
            hopUp.setInterpolator(Interpolator.EASE_OUT);
            
            ScaleTransition hopDown = new ScaleTransition(Duration.seconds(timePerJump / 2), token);
            hopDown.setToX(1.0);
            hopDown.setToY(1.0);
            hopDown.setInterpolator(Interpolator.EASE_IN);
            
            SequentialTransition hop = new SequentialTransition(hopUp, hopDown);
            ParallelTransition jump = new ParallelTransition(tt, hop);
            
            sequence.getChildren().add(jump);
            currentPos = nextPos;
        }
        
        sequence.play();
    }

    public void showErrorAnimation(String message) {
        gameLog.setText(message);
        gameLog.setStyle("-fx-font-size: 16px; -fx-text-fill: #ff5555;");
        
        TranslateTransition t1 = new TranslateTransition(Duration.millis(50), root);
        t1.setByX(15);
        TranslateTransition t2 = new TranslateTransition(Duration.millis(50), root);
        t2.setByX(-30);
        TranslateTransition t3 = new TranslateTransition(Duration.millis(50), root);
        t3.setByX(30);
        TranslateTransition t4 = new TranslateTransition(Duration.millis(50), root);
        t4.setByX(-15);
        
        new SequentialTransition(t1, t2, t3, t4).play();
    }

    public Label getGameLog() {
        return gameLog;
    }

    private int[] indexToRowCol(int index) {
        if (index > 99) index = 99;
        if (index < 0) index = 0;
        int r = index / 10;
        int c = index % 10;
        if (r % 2 == 1) c = 9 - c;
        return new int[]{r, c};
    }
}
