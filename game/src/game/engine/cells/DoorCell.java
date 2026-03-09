package game.engine.cells;

import game.engine.Role;
import game.engine.interfaces.CanisterModifier;

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
	
}
