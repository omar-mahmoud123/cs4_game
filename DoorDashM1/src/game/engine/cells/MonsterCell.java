package game.engine.cells;

import game.engine.monsters.*;

public class MonsterCell extends Cell {
	private Monster cellMonster;

	public MonsterCell(String name, Monster cellMonster) {
		super(name);
		this.cellMonster = cellMonster;
	}

	public Monster getCellMonster() {
		return cellMonster;
	}
	
	@Override
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		super.onLand(landingMonster, opponentMonster);
		Monster monsterCell = this.getCellMonster();
		
		if(monsterCell.getRole().compareTo(landingMonster.getRole()) == 0) {
			landingMonster.executePowerupEffect(opponentMonster);
		} else {
			if(landingMonster.getEnergy() > monsterCell.getEnergy()) {
				landingMonster.alterEnergy(monsterCell.getEnergy() - landingMonster.getEnergy());
				monsterCell.alterEnergy(landingMonster.getEnergy() - monsterCell.getEnergy());
			}
		}
		
		
	}

}
