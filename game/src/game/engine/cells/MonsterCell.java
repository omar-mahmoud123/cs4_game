package game.engine.cells;

public class MonsterCell extends Cell{
	private Monster cellMonster;
	
	public Cell (String name,Monster cellMonster) {
		super(name);
		super.cellMonster=cellMonster;
	}
	

	public Monster getCellMonster() {
		return this.cellMonster;
	}

}