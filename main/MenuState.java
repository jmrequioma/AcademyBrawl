package main;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState {
	Music mainmusic;
	private int ID = 0;	
	public MenuState(int state) {}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		Image play = new Image("res/play.png");
		Image exit = new Image("res/exit.png");
		play.draw(300, 400);
		exit.draw(300, 500);
		g.setBackground(Color.darkGray);
		g.drawString("School Rumble!!!", 330, 40); 
		g.setColor(Color.pink);
		//r = new Rectangle(x, Y, 100, 50);
		//g.draw(r);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		
		Input in = gc.getInput();

		// When mouse enters the start button
		if (in.getMouseX() >= 300 && in.getMouseX() <= 500 && in.getMouseY() >= 400 && in.getMouseY() <= 475) {
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(1);
			}
		}
		// When mouse enters exit button
		if (in.getMouseX() >= 300 && in.getMouseX() <= 500 && in.getMouseY() >= 500 && in.getMouseY() <= 575) {
			if (Mouse.isButtonDown(0)) {
				gc.exit();;
			}
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
}
