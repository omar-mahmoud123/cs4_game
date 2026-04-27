package game.engine.cells;

import java.util.ArrayList;

import game.engine.Board;
import game.engine.cards.Card;
import game.engine.monsters.Monster;

public class CardCell extends Cell{
	public CardCell (String name) {
		super(name);
	}

	public void onLand(Monster landingMonster, Monster opponentMonster) {
		Card c = Board.drawCard();
		c.performAction(landingMonster, opponentMonster);
	}
}
