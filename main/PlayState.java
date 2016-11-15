package main;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import entities.*;

public class PlayState extends BasicGameState {
	Music playmusic;
	private int ID = 1;
	Combatant player1, player2;
	
	Image map;    // for background rendering
	
	public PlayState(int state) {}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player1 = new Combatant(1);   // Make player 1
		player2 = new Combatant(2);   // Make player 2
		player1.init();               // Initialize player 1
		player2.init();               //Initialize player 2
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		
		g.drawString("Welcome", 365, 310);
		g.setColor(Color.pink);
		
		player1.render(gc, g);
		player2.render(gc, g);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		player1.update(gc, delta);
		player2.update(gc, delta);
		
		// If neither player is attacking 
		if (player1.attacking == false && player2.attacking == false){
			
			// If player 2 is blocking player 1
			if (player1.hitTest(player2)){
				player1.stopped = true;
				player1.xa = 0;
			}
			
			// If player 1 is blocking player 2
			if (player2.hitTest(player1)){
				player2.stopped = true;
				player2.xa = 0; 
			} 
			
			// FIXME a bug (fusion) occurs when both players are moving the same direction
			if (player1.getView() == player2.getView()) {
				player1.stopped = false;
				player2.stopped = false;
			}
			
		// If either player is attacking	
		} else {
			
			// Player 1 hits player 2
			if (player1.hitTest(player2)){
				player2.hit = true;
			
			// Player 2 hits player 1
		    } else if (player2.hitTest(player1)){
		    	player1.hit = true;
		    }
		}
		
		// Makes both players face each other
		if (player1.x > player2.x){
			player1.setView(1);
			player2.setView(0);
		} else {
			player1.setView(0);
			player2.setView(1);
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
}
