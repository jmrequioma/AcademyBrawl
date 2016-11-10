package main;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.state.*;

import entities.*;

public class PlayState extends BasicGameState {
	private int ID = 1;
	Entity entity;
	
	//Animation player1, movingLeft, movingBack, jumping, crouching, blocking, gettingHit;
	Image map;
	boolean quit = false;
	int[] duration = {200, 200};
	
	public PlayState(int state) {
		
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		entity = new Combatant();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		
		g.drawString("Welcome", 365, 310);
		g.setColor(Color.pink);
		//r = new Rectangle(x, Y, 100, 50);
		//g.draw(r);
		entity.render(gc, g);;

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		entity.update(gc, delta);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
}
