package game.engine.monsters;
import game.engine.Role;
public abstract class Monster implements Comparable<Monster> {
	String name;
	String description;
	Role role;
	Role originalRole;
	int energy;
	int position;
	boolean frozen;
	boolean shielded;
	int confusionTurns;
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
}
