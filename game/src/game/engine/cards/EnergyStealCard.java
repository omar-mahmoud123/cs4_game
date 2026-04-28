package game.engine.cards;

import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class EnergyStealCard extends Card implements CanisterModifier{
    private final int energy;

    public EnergyStealCard(String name, String description, int rarity, int energy) {
        super(name, description, rarity, true);
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }
    
    public void performAction(Monster player, Monster opponent) {
    	int p =player.getEnergy();
    	int o =opponent.getEnergy();

    	if(o>energy) {
    		opponent.alterEnergy(-energy);
    		player.setEnergy(energy+p);
    	}else {
    		opponent.setEnergy(0);
    		player.setEnergy(o+p);
    		
    	}
    }

	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		// TODO Auto-generated method stub
		
	}
    
    // Modifies canister energy of both the player and opponent. , we didnt do that yet

}
