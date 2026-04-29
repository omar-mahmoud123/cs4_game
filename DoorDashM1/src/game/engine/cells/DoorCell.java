package game.engine.cells;

import java.util.ArrayList;

import game.engine.Board;
import game.engine.Role;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class DoorCell extends Cell implements CanisterModifier {
	private Role role;
	private int energy;
	private boolean activated;
	
	public DoorCell(String name, Role role, int energy) {
		super(name);
		this.role = role;
		this.energy = energy;
		this.activated = false;
	}
	
	public Role getRole() {
		return role;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean isActivated) {
		this.activated = isActivated;
	}
	
	@Override
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		super.onLand(landingMonster, opponentMonster);
		if(!this.isActivated()) {
			
			if(landingMonster.getRole().compareTo(this.getRole()) != 0){
				
				if(!landingMonster.isShielded()) {
					modifyCanisterEnergy(landingMonster, -this.getEnergy());
					this.setActivated(false);
				}
				
			} else {
				modifyCanisterEnergy(landingMonster, this.getEnergy());
				this.setActivated(true);
			}
			
		}

	}

	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		monster.alterEnergy(canisterValue);
	
		ArrayList<Monster> stationedMonsters = Board.getStationedMonsters();
		
		for(int i = 0; i < stationedMonsters.size(); i++) {
			
			Monster current = stationedMonsters.get(i);
			
			if(current.getRole().compareTo(monster.getRole()) == 0)
				current.alterEnergy(canisterValue);
		}
				
	}

}
