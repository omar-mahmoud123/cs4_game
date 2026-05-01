package game.engine.cards;

import game.engine.monsters.Monster;

public class SwapperCard extends Card {

	public SwapperCard(String name, String description, int rarity) {
		super(name, description, rarity, true);
	}

	@Override
	public void performAction(Monster player, Monster opponent) {
		int playerPos = player.getPosition();
		int opponentPos = opponent.getPosition();

		if(playerPos < opponentPos) {
			player.setPosition(opponentPos);
			opponent.setPosition(playerPos);
		}
		
	}
	
}
