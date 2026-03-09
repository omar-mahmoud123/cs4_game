package game.engine;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.Random;

import game.engine.dataloader.DataLoader;
import game.engine.monsters.Monster;

public class Game {
	private Board board;
	private ArrayList<Monster> allMonsters;
	private Monster player;
	private Monster opponent;
	private Monster current;
	
	public Monster getCurrent() {
		return current;
	}
	public void setCurrent(Monster current) {
		this.current = current;
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

	
	public Game(Role playerRole) throws IOException {
		// creating the board with the loaded cards from the csv
		this.board = new Board(DataLoader.readCards());
		
		// setting all monsters with the loaded monsters from the csv
		this.allMonsters = DataLoader.readMonsters();
		
		// randomly selecting player based on the player’s chosen role
		this.player = selectRandomMonsterByRole(playerRole);
		
		// randomly selecting opponent based on the opposite to player’s chosen role
		if(playerRole == Role.LAUGHER)
			this.opponent = selectRandomMonsterByRole(Role.SCARER);
		else 
			this.opponent = selectRandomMonsterByRole(Role.LAUGHER);

		// setting the current with the player
		this.current = this.player;
		
	}
	
	// randomly returns a monster based on the given role
	private Monster selectRandomMonsterByRole(Role role){
		ArrayList<Monster> monsterList = getAllMonsters();
		
		ArrayList<Monster> roleMonsterList = new ArrayList<Monster>();
		for(int i = 0; i < monsterList.size(); i++) {
			Monster currentMonster = monsterList.get(i);
			
			if(currentMonster.getRole().equals(role))
				roleMonsterList.add(currentMonster);
		}
		
		Random random = new Random();
		
		return roleMonsterList.get(random.nextInt(roleMonsterList.size()));
	}
	
}
