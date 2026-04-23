package game.engine.monsters;
import game.engine.Constants;
import game.engine.Role;

public class Dynamo extends Monster{
	public Dynamo(String name, String description, Role role, int energy) {
		super(name, description, role, Math.abs(energy));
	}
	
	public void executePowerupEffect(Monster opponentMonster) {
		opponentMonster.setFrozen(true);
	}
	
	public void setEnergy(int energy) {
		int energy_change=(energy-this.getEnergy())*2;
		int new_energy = Math.max(energy_change, Constants.MIN_ENERGY);
		this.setEnergy(new_energy);
	}
	
	
}