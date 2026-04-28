package game.engine.cards;

import game.engine.Role;
import game.engine.monsters.Monster;

public class ConfusionCard extends Card {

    private final int duration;

    public ConfusionCard(String name, String description, int rarity, int duration) {
        super(name, description, rarity, false);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

	public void performAction(Monster player, Monster opponent) {
		opponent.setConfusionTurns(duration);
		player.setConfusionTurns(duration);
		
		Role p = player.getRole();
		Role o = opponent.getRole();
		
		player.setRole(o);
		opponent.setRole(p);
		
	}
    
    
}
