package game.engine.monsters;

import game.engine.Constants;
import game.engine.Role;

public class Dynamo extends Monster {
	
	public Dynamo(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}

	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		opponentMonster.setFrozen(true);
	}
	
	public void setEnergy(int energy) {
		int energyChange = energy - this.getEnergy();
		int newEnergy = Math.max(this.getEnergy() + 2 * energyChange, Constants.MIN_ENERGY);
		
		super.setEnergy(newEnergy);
	}
}
