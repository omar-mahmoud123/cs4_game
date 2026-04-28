package game.engine.monsters;
import java.util.ArrayList;

import game.engine.Board;
import game.engine.Role;

public class Schemer extends Monster{
	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}
	
	private int stealEnergyFrom(Monster target) {
		int e=target.getEnergy();
		if (e>10) {
			target.setEnergy(e-10);
			return 10;
		}else {
			target.setEnergy(e-10);
			if(e>0) {
				return e;		
			}else {
				return 0;
			}
			
		}
		
	}
	
	public void setEnergy(int energy) {
		super.setEnergy(energy+10);
	}
	
	public void executePowerupEffect(Monster opponentMonster) {
		int total=stealEnergyFrom(opponentMonster);
		ArrayList<Monster> stationedList = Board.getStationedMonsters();
		for (int i=0;i<stationedList.size();i++) {
			total+= stealEnergyFrom(stationedList.get(i));
		}
		
		this.setEnergy(this.getEnergy()+total);
		
	}
	
	
	
	
	
	
}
