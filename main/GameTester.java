package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import util.Window;

public class GameTester extends StateBasedGame {
	
	public static final String GAME_NAME = "School Rumble";
	public static final int titleScreen = 0;
	public static final int menu = 1;
	public static final int characterSelect = 2;
	public static final int controls = 3;
	public static final int options = 4;
	public static final int credits = 5;
	public static final int play = 6;
	public static final int summary = 7;
	
	
	public GameTester(String name) {
		super(name);
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
        gc.setTargetFrameRate(120);                  // set desired frame rate
        gc.setAlwaysRender(true);                   // always rendering even if alt-tabbed
        gc.setMinimumLogicUpdateInterval(15);      // min update interval
        gc.setMaximumLogicUpdateInterval(15);      // max update interval
        gc.setVSync(true);							// to enable Vertical syncing
        gc.setShowFPS(false);                       // to remove fps display
        
        // adding the states to the Game container
        this.addState(new TitleScreen(titleScreen));
		this.addState(new MenuState(menu));
		this.addState(new CharacterSelectState(characterSelect));
		this.addState(new OptionState(options));
		this.addState(new CreditsState(credits));
		this.addState(new PlayState(play));
		this.addState(new ControlsState(controls));
		this.addState(new BattleSummaryState(summary));
		
		// enter the title screen
		this.enterState(titleScreen);
    }
	
	public static void main(String[] args) {
		AppGameContainer apgc;
		
		try {
			apgc = new AppGameContainer(new GameTester(GAME_NAME));
			apgc.setDisplayMode(Window.WIDTH, Window.HEIGHT, false);   // set window border to 800 x 600 (WidthxHeight)
			apgc.start();
		} catch (SlickException e) {	
			e.printStackTrace();
		}
	}

}
