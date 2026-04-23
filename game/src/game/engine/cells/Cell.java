package game.engine.cells;
import game.engine.monsters.Monster;

public class Cell{
	private final String name;
	private Monster monster;
	
	public Cell (String name) {
		this.name = name;
		monster = null;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Monster getMonster() {
		return this.monster;
	}
	
	public void setMonster(Monster monster) {
		this.monster = monster;
	}
	
	public boolean isOccupied() {
		if (this.getMonster()==null) {
			return false;
		}
		return true;
	}
	

	
}
