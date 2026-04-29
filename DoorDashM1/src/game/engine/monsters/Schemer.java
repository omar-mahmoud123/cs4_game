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
		int targetEnergy = target.getEnergy();
		int stolenEnergy = Math.min(targetEnergy, Constants.SCHEMER_STEAL);

		target.setEnergy(targetEnergy - stolenEnergy);

		return stolenEnergy;
	}
	
	@Override
	public void setEnergy(int energy) {
		super.setEnergy(energy + 10);
	}

	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		int totalEnergy = this.getEnergy() + this.stealEnergyFrom(opponentMonster);
		ArrayList<Monster> stationedMonsters = Board.getStationedMonsters();
		for(int i = 0; i < stationedMonsters.size(); i++) {
			totalEnergy += stealEnergyFrom(stationedMonsters.get(i));
		}
		
		this.setEnergy(totalEnergy);
	}
}
