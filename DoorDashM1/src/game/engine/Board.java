package game.engine;


import java.util.ArrayList;

import game.engine.cards.Card;
import game.engine.cells.*;
import game.engine.exceptions.InvalidMoveException;
import game.engine.monsters.Monster;

public class Board {
	private Cell[][] boardCells;
	private static ArrayList<Monster> stationedMonsters; 
	private static ArrayList<Card> originalCards;
	public static ArrayList<Card> cards;
	
	public Board(ArrayList<Card> readCards) {
		this.boardCells = new Cell[Constants.BOARD_ROWS][Constants.BOARD_COLS];
		stationedMonsters = new ArrayList<Monster>();
		originalCards = readCards;
		cards = new ArrayList<Card>();
	}
	
	public Cell[][] getBoardCells() {
		return boardCells;
	}
	
	public static ArrayList<Monster> getStationedMonsters() {
		return stationedMonsters;
	}
	
	public static void setStationedMonsters(ArrayList<Monster> stationedMonsters) {
		Board.stationedMonsters = stationedMonsters;
	}

	public static ArrayList<Card> getOriginalCards() {
		return originalCards;
	}
	
	public static ArrayList<Card> getCards() {
		return cards;
	}
	
	public static void setCards(ArrayList<Card> cards) {
		Board.cards = cards;
	}
	
	private int[] indexToRowCol(int index) {
		int row = index / 10;
		int col = (row % 2 == 0)? index % 10 : 9 - (index % 10);
		
		return new int[] {row, col};
	}
	
	private Cell getCell(int index) {
		int[] index2d = indexToRowCol(index);
		return getBoardCells()[index2d[0]][index2d[1]];
	}
	
	private void setCell(int index, Cell cell) {
		int[] index2d = indexToRowCol(index);
		this.boardCells[index2d[0]][index2d[1]] = cell;
	}
	
	void initializeBoard(ArrayList<Cell> specialCells) {
		int stationedMonsterIndex = 0;
		for(int i = 0; i < 100; i++) {
			int[] index2d = indexToRowCol(i);
			int row = index2d[0];
			int col	= index2d[1];
			
			Cell cell;
			
			if(i % 2 != 0) // checks if door cell
				cell = firstDoorCell(specialCells);
			else 
				if(contains(Constants.CONVEYOR_CELL_INDICES, i)) // checks if belt cell
					cell = firstBeltCell(specialCells);
				else if(contains(Constants.SOCK_CELL_INDICES, i)) // check if socks cell
					cell = firstSocksCell(specialCells);
				else if(contains(Constants.CARD_CELL_INDICES, i)) // checks if card cell
					cell = new CardCell("Card Cell");
				else if(contains(Constants.MONSTER_CELL_INDICES, i)) // checks if monster cell
					cell = new MonsterCell("Monster Cell", Board.stationedMonsters.get(stationedMonsterIndex));
				else
					cell = new Cell("Normal Cell");
			
			this.boardCells[row][col] = cell;
		}
	}
	
	private Cell firstDoorCell(ArrayList<Cell> arr) {
		for(int i = 0; i < arr.size(); i++) 
			if(arr.get(i) instanceof DoorCell) 
				return arr.remove(i);
		return null;
	}
	private Cell firstSocksCell(ArrayList<Cell> arr) {
		for(int i = 0; i < arr.size(); i++) 
			if(arr.get(i) instanceof ContaminationSock) 
				return arr.remove(i);
		return null;
	}
	private Cell firstBeltCell(ArrayList<Cell> arr) {
		for(int i = 0; i < arr.size(); i++) 
			if(arr.get(i) instanceof ConveyorBelt) 
				return arr.remove(i);
		return null;
	}
	
	
	private Boolean contains(int[] arr, int target) {
		for(int i = 0; i < arr.length; i++) 
			if(arr[i] == target) return true;
		return false;
	}
	
	
	private void setCardsByRarity() {
		ArrayList<Card> newList = new ArrayList<Card>();
		
		while(!Board.originalCards.isEmpty()) {
			Card card = Board.originalCards.removeFirst();
			
			for(int i = 0; i < card.getRarity(); i++)
				newList.add(card);
		}
		
		Board.originalCards = newList;
	}
	
	static void reloadCards() {
		ArrayList<Card> newList = new ArrayList<Card>();
		for(int i = 0; i < Board.originalCards.size(); i++) 
			newList.add(Board.originalCards.get(i));
		
		Board.cards = newList;
	}
	
	public static Card drawCard() {
		if(Board.cards.isEmpty()) Board.reloadCards();
		
		return Board.cards.removeFirst();
	}
	
	


	
}
