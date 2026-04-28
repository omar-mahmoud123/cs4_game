package game.engine.monsters;
import game.engine.Constants;
import game.engine.Role;

public class MultiTasker extends Monster{
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
	
	public void move(int distance) {
		int normalTurns = getNormalSpeedTurns();
		if ( normalTurns>0) {
			super.move(distance);
			setNormalSpeedTurns(normalTurns - 1);

		}else {
			super.move(distance*(1/2));
		}
		
	}
	
	public void setEnergy(int energy) {
		super.setEnergy(energy+200);
	}

	public void executePowerupEffect(Monster opponentMonster) {
		setNormalSpeedTurns(2);
	}
}