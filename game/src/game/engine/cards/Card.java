package game.engine.cards;

import game.engine.Role;
import game.engine.monsters.Monster;

public abstract class Card {

    private final String name;
    private final String description;
    private final int rarity;
    private final boolean lucky;

    public Card(String name, String description, int rarity, boolean lucky) {
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.lucky = lucky;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRarity() {
        return rarity;
    }

    public boolean isLucky() {
        return lucky;
    }
    
	public abstract void performAction(Monster player, Monster opponent) ;
//	public void performAction(Monster player, Monster opponent) {
//		Role p = player.getRole();
//		Role o = opponent.getRole();
//		
//		player.setRole(o);
//		opponent.setRole(p);
//
//	}
	
	
	

    
}
