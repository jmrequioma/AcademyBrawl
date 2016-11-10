package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameTester extends StateBasedGame {
	
	public static final String GAME_NAME = "School Rumble";
	public static final int menu = 0;
	public static final int play = 1;
	
	public GameTester(String name) {
		super(name);
		this.addState(new MenuState(menu));
		this.addState(new PlayState(play));
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		// PC Master Race only accepts 60 or more fps
        gc.setTargetFrameRate(60);
        gc.setAlwaysRender(true);
        gc.setMaximumLogicUpdateInterval(60);
        gc.setVSync(true);
        // to remove fps display
        gc.setShowFPS(false);
        
		this.getState(menu).init(gc, this);
		this.getState(play).init(gc, this);
		this.enterState(menu);
	}
	
	public static void main(String[] args) {
		AppGameContainer apgc;
		
		try {
			apgc = new AppGameContainer(new GameTester(GAME_NAME));
			
			apgc.setDisplayMode(800, 600, false);
			apgc.start();
		} catch (SlickException e) {	
			e.printStackTrace();
		}
	}

}
