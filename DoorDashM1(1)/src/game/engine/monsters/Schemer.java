package game.engine.monsters;

import java.util.ArrayList;

import game.engine.Board;
import game.engine.Constants;
import game.engine.Role;

public class Schemer extends Monster {
	
	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}
	
	private int stealEnergyFrom(Monster target) {
		return Math.min(Constants.SCHEMER_STEAL, target.getEnergy());
	}
	
	@Override
	public void setEnergy(int energy) {
		super.setEnergy(energy + 10);
	}
	
	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		int change = stealEnergyFrom(opponentMonster);
		int total_change =  change;
		opponentMonster.setEnergy(opponentMonster.getEnergy() - change);
		ArrayList<Monster> stationedMonsters = Board.getStationedMonsters();
		for(int i = 0; i < stationedMonsters.size() ; i++) {
			change = stealEnergyFrom(stationedMonsters.get(i));
			total_change += change;
			stationedMonsters.get(i).setEnergy(stationedMonsters.get(i).getEnergy() - change);
		}
		this.setEnergy(this.getEnergy() + total_change);
	}
}
