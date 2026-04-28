package game.engine.cards;

import game.engine.monsters.Monster;

public class SwapperCard extends Card {
    public SwapperCard(String name, String description, int rarity) {
        super(name, description, rarity, true);
    }

	public void performAction(Monster player, Monster opponent) {
		
		int playerpos = player.getPosition();
		int opponentpos = opponent.getPosition();
		if (playerpos<opponentpos) {
			opponent.setPosition(playerpos);
			player.setPosition(opponentpos);
		}
		
	}
    
    

}
