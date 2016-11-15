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
        gc.setTargetFrameRate(400);                  // set desired frame rate
        gc.setAlwaysRender(true);                   // always rendering even if alt-tabbed
        gc.setMaximumLogicUpdateInterval(120);      // max update interval
        gc.setVSync(true);							// to enable Vertical syncing
        gc.setShowFPS(false);                       // to remove fps display
        
		this.getState(menu).init(gc, this);
		this.getState(play).init(gc, this);
		this.enterState(menu);
	}
	
	public static void main(String[] args) {
		AppGameContainer apgc;
		
		try {
			apgc = new AppGameContainer(new GameTester(GAME_NAME));			
			apgc.setDisplayMode(800, 600, false);   // set window border to 800 x 600 (WidthxHeight)
			apgc.start();
		} catch (SlickException e) {	
			e.printStackTrace();
		}
	}

}
