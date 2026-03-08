package game.engine.dataloader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import game.engine.Role;
import game.engine.cards.Card;
import game.engine.cards.ConfusionCard;
import game.engine.cards.EnergyStealCard;
import game.engine.cards.ShieldCard;
import game.engine.cards.StartOverCard;
import game.engine.cards.SwapperCard;
import game.engine.cells.Cell;
import game.engine.cells.ContaminationSock;
import game.engine.cells.ConveyorBelt;
import game.engine.cells.DoorCell;
import game.engine.monsters.Dasher;
import game.engine.monsters.Dynamo;
import game.engine.monsters.Monster;
import game.engine.monsters.MultiTasker;
import game.engine.monsters.Schemer;

public class DataLoader {
	public static final String CARDS_FILE_NAME = "cards.csv";
	public static final String CELLS_FILE_NAME = "cells.csv";
	public static final String MONSTER_FILE_NAME = "monsters.csv";

	
	public static ArrayList<Card> readCards() throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(CARDS_FILE_NAME));
	
		String line;
		ArrayList<Card> cardList = new ArrayList<Card>();
		while((line = reader.readLine()) != null) {
			String[] cardData = line.split(",");
			
			switch(cardData[0]) {
			case "SWAPPER" : 
				cardList.add(new SwapperCard(cardData[1], cardData[2], Integer.parseInt(cardData[3])));
				break;
			case "SHIELD" :
				cardList.add(new ShieldCard(cardData[1], cardData[2], Integer.parseInt(cardData[3])));
				break;
			case "ENERGYSTEAL" :
				cardList.add(new EnergyStealCard(cardData[1], cardData[2], Integer.parseInt(cardData[3]), Integer.parseInt(cardData[4])));
				break;
			case "STARTOVER" :
				cardList.add(new StartOverCard(cardData[1], cardData[2], Integer.parseInt(cardData[3]), cardData[4].equals("true")));
				break;
			case "CONFUSION" :
				cardList.add(new ConfusionCard(cardData[1], cardData[2], Integer.parseInt(cardData[3]), Integer.parseInt(cardData[4])));
				break;
			}
		}
		
		reader.close();
		return cardList;
	}
	
	

	public static ArrayList<Cell> readCells() throws IOException {
			
		BufferedReader reader = new BufferedReader(new FileReader(CELLS_FILE_NAME));
	
		String line;
		ArrayList<Cell> cellList = new ArrayList<Cell>();
		while((line = reader.readLine()) != null) {
			String[] cellData = line.split(",");
			
			if(cellData.length == 3) { // It's Door cell
				cellList.add(new DoorCell(cellData[0], Role.valueOf(cellData[1]), Integer.parseInt(cellData[2])));
			} else { // It's Transport cell
				
				if(Integer.parseInt(cellData[1]) > 0) // It's ConveyorBelt
					
					cellList.add(new ConveyorBelt(cellData[0], Integer.parseInt(cellData[1])));

				else // It's ContaminationSock

					cellList.add(new ContaminationSock(cellData[0], Integer.parseInt(cellData[1])));
			}
		}
		
		reader.close();
		return cellList;	
	}
	
	


	public static ArrayList<Monster> readMonsters() throws IOException {
			
		BufferedReader reader = new BufferedReader(new FileReader(MONSTER_FILE_NAME));
	
		String line;
		ArrayList<Monster> monsterList = new ArrayList<Monster>();
		while((line = reader.readLine()) != null) {
			String[] monsterData = line.split(",");
			
			switch(monsterData[0]) {
			case "DYNAMO" : 
				monsterList.add(new Dynamo(monsterData[1], monsterData[2], Role.valueOf(monsterData[3]), Integer.parseInt(monsterData[4])));
				break;
			case "DASHER" :
				monsterList.add(new Dasher(monsterData[1], monsterData[2], Role.valueOf(monsterData[3]), Integer.parseInt(monsterData[4])));
				break;
			case "SCHEMER" :
				monsterList.add(new Schemer(monsterData[1], monsterData[2], Role.valueOf(monsterData[3]), Integer.parseInt(monsterData[4])));
				break;
			case "MULTITASKER" :
				monsterList.add(new MultiTasker(monsterData[1], monsterData[2], Role.valueOf(monsterData[3]), Integer.parseInt(monsterData[4])));
				break;
			}
			
			
		}
		
		reader.close();
		return monsterList;	
	}
	
	
}




