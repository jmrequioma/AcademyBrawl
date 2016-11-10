package entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import util.Box;

public abstract class Entity extends Box{
	
	public Image image;
	public Color color;
	
	public Entity(){
		try {
			init();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void init() throws SlickException;
	
	public abstract void render(GameContainer gc, Graphics g);
	
	public abstract void update(GameContainer gc, int delta);
	
	public void TestLeft(){
		
	}
	
}
