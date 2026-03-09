package game.engine.monsters;
import game.engine.Role;
import game.engine.Constants;

public abstract class Monster implements Comparable<Monster> {
	private final String name; // READ ONLY
	private final String description; // READ ONLY
	private Role role;
	private final Role originalRole; // READ ONLY
	private int energy;
	private int position;
	private boolean frozen;
	private boolean shielded;
	private int confusionTurns;
	
	public Monster(String name, String description, Role originalRole, int energy) {
		this.name = name;
		this.description = description;
		this.originalRole = originalRole;
		this.energy = energy;
		
		this.role = originalRole;
		this.position = 0;
		this.confusionTurns = 0;
		this.frozen = false;
		this.shielded = false;
	}
	
	// orders monsters based on who is more closer to the end
	public int compareTo(Monster o) {
		return this.position - o.position;
	}

	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public Role getOriginalRole() {
		return originalRole;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = Math.max(energy, Constants.MIN_ENERGY);
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
	    if (position < Constants.STARTING_POSITION) {
	        this.position = Constants.STARTING_POSITION;
	    } else {
	        this.position = position % Constants.BOARD_SIZE;
	    }
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public boolean isShielded() {
		return shielded;
	}

	public void setShielded(boolean shielded) {
		this.shielded = shielded;
	}

	public int getConfusionTurns() {
		return confusionTurns;
	}

	public void setConfusionTurns(int confusionTurns) {
		this.confusionTurns = confusionTurns;
	}
	
}
