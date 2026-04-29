package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import game.engine.cells.Cell;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.OutOfEnergyException;
import game.engine.monsters.*;

public class Game {
	private Board board;
	private ArrayList<Monster> allMonsters; 
	private Monster player;
	private Monster opponent;
	private Monster current;
	
	public Game(Role playerRole) throws IOException {
		this.board = new Board(DataLoader.readCards());
		
		this.allMonsters = DataLoader.readMonsters();
		
		this.player = selectRandomMonsterByRole(playerRole);
		this.opponent = selectRandomMonsterByRole(playerRole == Role.SCARER ? Role.LAUGHER : Role.SCARER);
		this.current = player;
		
		this.allMonsters.remove(this.player);
		this.allMonsters.remove(this.opponent);
		
		Board.setStationedMonsters(allMonsters);
		
		ArrayList<Cell> specialCells = DataLoader.readCells();
		board.initializeBoard(specialCells);
	}
	
	public Board getBoard() {
		return board;
	}
	
	public ArrayList<Monster> getAllMonsters() {
		return allMonsters; 
	}
	
	public Monster getPlayer() {
		return player;
	}
	
	public Monster getOpponent() {
		return opponent;
	}
	
	public Monster getCurrent() {
		return current;
	}
	
	public void setCurrent(Monster current) {
		this.current = current;
	}
	
	private Monster selectRandomMonsterByRole(Role role) {
		Collections.shuffle(allMonsters);
	    return allMonsters.stream()
	    		.filter(m -> m.getRole() == role)
	    		.findFirst()
	    		.orElse(null);
	}
	
	private Monster getCurrentOpponent() {
		if(this.getCurrent().compareTo(this.getPlayer()) == 0) {
			return this.getOpponent();
		}
		return this.getPlayer();
	}
	
	private int rollDice() {
		return 1 + (int) (Math.random() * 6);
	}
	
	public void usePowerup() throws OutOfEnergyException {
		if(this.getCurrent().getEnergy() >= Constants.POWERUP_COST) {
			this.getCurrent().setEnergy(this.getCurrent().getEnergy() - Constants.POWERUP_COST);
		} else {
			throw new OutOfEnergyException();
		}
	}
	
	private void switchTurn() {
		this.setCurrent(this.getCurrentOpponent());
	}
	
	private boolean checkWinCondition(Monster monster) {
		return monster.getPosition() == Constants.WINNING_POSITION && monster.getEnergy() == Constants.WINNING_ENERGY;
	}
	
	public Monster getWinner() {
		if(checkWinCondition(this.getPlayer())) return this.getPlayer();
		if(checkWinCondition(this.getOpponent())) return this.getOpponent();
		return null;
	}
	
}