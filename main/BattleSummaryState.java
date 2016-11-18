package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class BattleSummaryState extends BasicGameState{
	private int ID = 7;
	
	private Sound back;
	
	public BattleSummaryState(int state) {}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		//TODO add background images
		back = new Sound("res/soundEffects/close.wav");
		
	}/*End of init*/

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		// TODO: actual battle analysis
		g.drawString("STILL IN BETA, PRESS ESC TO EXIT GAME", 100, 100);
		
	}/*End of render*/

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		Input in = gc.getInput();
		
		if (in.isKeyPressed(Input.KEY_ESCAPE) || in.isKeyPressed(Input.KEY_BACK)){
			
			gc.exit();   // just exits the game for now
			
			//TODO: reset the game
			//back.play();
			//TitleScreen.musicMenu();
			//sbg.enterState(GameTester.menu, new FadeOutTransition(), new FadeInTransition());
		}
	}/*End of update*/

	@Override
	public int getID() {
		return ID;
	}

}
