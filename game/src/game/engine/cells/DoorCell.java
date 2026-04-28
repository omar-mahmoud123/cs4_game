package game.engine.cells;

import java.util.ArrayList;

import game.engine.Board;
import game.engine.Role;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class DoorCell extends Cell implements CanisterModifier{
	private final Role role;
	private final int energy;
	private boolean activated;
	
	public DoorCell(String name, Role role, int energy) {
		super(name);
		this.role = role;
		this.energy = energy;
		activated = false;
	}
	
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	public boolean isActivated() {
		return this.activated;
	}
	
	public Role getRole() {
		return this.role;
	}
	
	public int getEnergy() {
		return this.energy;
	}

	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		monster.alterEnergy(canisterValue);
		
	}

	public void onLand(Monster landingMonster, Monster opponentMonster) {
		if(!isActivated()) {
			Role r = landingMonster.getRole();
			ArrayList<Monster> stationedList = Board.getStationedMonsters();
			if(this.getRole()==r) {
				modifyCanisterEnergy(landingMonster,this.getEnergy());
				for(int i = 0; i< stationedList.size();i++) {
					if(r == stationedList.get(i).getRole()) {
						modifyCanisterEnergy(stationedList.get(i),this.getEnergy());
					}
				}
			}else {
				modifyCanisterEnergy(landingMonster,-this.getEnergy());
				for(int i = 0; i< stationedList.size();i++) {
					if(r == stationedList.get(i).getRole()) {
						modifyCanisterEnergy(stationedList.get(i),-this.getEnergy());
					}
				}
			}
		}
		
	}
}
