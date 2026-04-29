package game.engine.monsters;

import game.engine.Role;

public class MultiTasker extends Monster {
	private int normalSpeedTurns;
	
	public MultiTasker(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
		this.normalSpeedTurns = 0;
	}

	public int getNormalSpeedTurns() {
		return normalSpeedTurns;
	}

	public void setNormalSpeedTurns(int normalSpeedTurns) {
		this.normalSpeedTurns = normalSpeedTurns;
	}

	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		this.setNormalSpeedTurns(2);
	}
	
	@Override
	public void move(int distance) {
		int turns = this.getNormalSpeedTurns();
		if(turns > 0) {
			super.move(distance);
			this.setNormalSpeedTurns(turns - 1);
		} else {
			super.move((1 / 2) * distance);
		}
	}
	
	public void setEnergy(int energy) {
		super.setEnergy(energy + 200);
	}

}