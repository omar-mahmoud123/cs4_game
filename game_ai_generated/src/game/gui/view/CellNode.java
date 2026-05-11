package game.gui.view;

import game.engine.Role;
import game.engine.cells.*;
import game.engine.monsters.Monster;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.effect.InnerShadow;

public class CellNode extends StackPane {
    
    private VBox container;
    private Label indexLabel;
    private Label typeLabel;
    private Label detailsLabel;
    
    public CellNode(Cell cell, int row, int col) {
        int index = getIndex(row, col);
        this.getStyleClass().add("cell-node");
        
        Rectangle bg = new Rectangle(60, 60);
        bg.setArcWidth(12);
        bg.setArcHeight(12);
        
        container = new VBox(2);
        container.setAlignment(Pos.CENTER);
        
        indexLabel = new Label(String.valueOf(index));
        indexLabel.getStyleClass().add("cell-index");
        
        typeLabel = new Label("");
        typeLabel.setStyle("-fx-font-size: 8px; -fx-font-weight: bold;");
        
        detailsLabel = new Label("");
        detailsLabel.setStyle("-fx-font-size: 9px; -fx-font-weight: bold;");
        
        setupAppearance(cell, bg);
        
        container.getChildren().addAll(indexLabel, typeLabel, detailsLabel);
        this.getChildren().addAll(bg, container);
    }
    
    private int getIndex(int row, int col) {
        int cols = 10;
        int c = col;
        if (row % 2 == 1) c = 9 - c;
        return row * cols + c;
    }
    
    private void setupAppearance(Cell cell, Rectangle bg) {
        Color base;
        String typeTxt = "";
        String detailsTxt = "";

        if (cell instanceof DoorCell) {
            DoorCell door = (DoorCell) cell;
            typeTxt = "DOOR";
            detailsTxt = door.getEnergy() + " E";
            base = (door.getRole() == Role.SCARER) ? Color.web("#ff5555") : Color.web("#50fa7b");
            if (door.isActivated()) {
                typeTxt = "USED";
                base = base.deriveColor(0, 1, 0.4, 0.5);
            }
        } else if (cell instanceof MonsterCell) {
            base = Color.web("#bd93f9");
            typeTxt = "MONSTER";
            detailsTxt = ((MonsterCell)cell).getCellMonster().getName().split(" ")[0]; // Just first name
        } else if (cell instanceof CardCell) {
            base = Color.web("#ffb86c");
            typeTxt = "CARD";
        } else if (cell instanceof ConveyorBelt) {
            base = Color.web("#8be9fd");
            typeTxt = "FLOW";
        } else if (cell instanceof ContaminationSock) {
            base = Color.web("#f1fa8c");
            typeTxt = "SOCK";
        } else {
            base = Color.web("#44475a");
            typeTxt = "NORMAL";
        }

        LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
            new Stop(0, base.deriveColor(0, 1, 1.2, 0.3)),
            new Stop(1, base.deriveColor(0, 1, 0.8, 0.1))
        );
        
        bg.setFill(gradient);
        bg.setStroke(base.deriveColor(0, 1, 1.5, 0.4));
        bg.setStrokeWidth(1.5);
        
        InnerShadow is = new InnerShadow();
        is.setOffsetX(0); is.setOffsetY(0); is.setColor(base.deriveColor(0, 1, 1, 0.5));
        bg.setEffect(is);

        typeLabel.setText(typeTxt);
        typeLabel.setTextFill(base);
        detailsLabel.setText(detailsTxt);
        detailsLabel.setTextFill(base.deriveColor(0, 1, 1.5, 0.8));
    }
}
