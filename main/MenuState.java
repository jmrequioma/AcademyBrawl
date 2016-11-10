package main;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState {
	Music mainmusic;
	private int ID = 0;
	private Rectangle r;
	private float x;
	private float Y = 500;
	private float xPos, yPos;
	private String mouse = "";
	
	public MenuState(int state) {
		
	}
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
		g.drawString(mouse, 50, 50);
		g.setColor(Color.pink);
		//r = new Rectangle(x, Y, 100, 50);
		//g.draw(r);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		
		Input in = gc.getInput();
		
		xPos = in.getMouseX();
		yPos = in.getMouseY();
		mouse = "x: " + xPos + " y: " + yPos;
		/*
		if (x >= 0 && x <= 700) {
			if (in.isKeyDown(Input.KEY_LEFT)) {
				x -= (200 / 1000.0f) * delta;
			} else if (in.isKeyDown(Input.KEY_RIGHT)) {
				x += (200 / 1000.0f) * delta;
				
			}
		} else {
			if (x < 0) {
				x = 1;
			} else if (x > 700) {
				x = 700;
			}
			*/
		/*
		}else if (in.isKeyDown(Input.KEY_UP)) {
		}
			y -= (200 / 1000.0f) * delta;
		} else if (in.isKeyDown(Input.KEY_DOWN)) {
			y += (200 / 1000.0f) * delta;
		}
		*/
		if (in.getMouseX() >= 300 && in.getMouseX() <= 500 && in.getMouseY() >= 400 && in.getMouseY() <= 475) {
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(1);
			}
		}
		
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
