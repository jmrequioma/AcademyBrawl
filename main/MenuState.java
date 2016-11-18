package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class MenuState extends BasicGameState {
	
	// sound effects to be used
	Sound scroll ,choose, error, close;
	
	private int ID = 1;	
	
	// pointer to where the focus is on from 2 (play) to 6 (exit) 
	// pointer is by default pointing to play
	private int select = 2;
	
	// Images to be used
	private Image display, play, options, controls, credits, exit;
	
	public MenuState(int state) {}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// Initialization of Sound Effects
		scroll = new Sound("res/soundEffects/select.wav");
		choose = new Sound("res/soundEffects/choose.wav");
		error = new Sound("res/soundEffects/error.wav");
		close = new Sound("res/soundEffects/close.wav");
		
		// initialization of images
		play = new Image("res/menu/play.png");
		options = new Image("res/menu/options.png");
		controls = new Image("res/menu/controls.png");
		credits = new Image("res/menu/credits.png");
		exit = new Image("res/menu/exit.png");
		
		// sets the main display to be pointing o play by default 
		// TODO: make a serious background (current one is for alpha puproses)
		display = play;
		
	}/*End of init*/

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		display.draw();		// draws the display

	}/*End of render*/

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		Input in = gc.getInput();
		
		// When a player has chosen what state he wants to enter
		if (in.isKeyPressed(Input.KEY_ENTER)){
			switch (select){
				case 2:
					// plays the appropriate sound effect
					choose.play();
					
					//plays the background music for character select and fades towards the character select state
					TitleScreen.musicCharSelect();
					sbg.enterState(select,  new FadeOutTransition(), new FadeInTransition());
					break;
				case 3:
					// FIXME: Currently unavailable due to lack of display
					// nothing wrong with the class itself 
					error.play();
					//TODO make the display
					//sbg.enterState(select,  new FadeOutTransition(), new FadeInTransition());
					break;
				case 4:
					// FIXME: Currently unavailable due to lack of display
					// nothing wrong with the class itself 
					error.play();
					//TODO make the display
					//sbg.enterState(select,  new FadeOutTransition(), new FadeInTransition());
					break;
				case 5:
					// plays the chosen background music
					choose.play();
					
					//plays the background music for credits and fades towards the credits state
					TitleScreen.musicCredits();                  
					sbg.enterState(select,  new FadeOutTransition(), new FadeInTransition());
					break;
				default:
					//TODO add a confirmation pop-up
					//close.play(1, 10);
					gc.exit();
			}
		}
		
		// when a player is still choosing upon an action
		
		if (in.isKeyPressed(Input.KEY_UP) || in.isKeyPressed(Input.KEY_W)){
			scroll.play();
			
			// when a player is already at the topmost pointer it points him to the lowest pointer
			if (select == 2)
				select = 6;
			// else the pointer goes up
			else 
				select--;
		}
		
		else if (in.isKeyPressed(Input.KEY_DOWN) || in.isKeyPressed(Input.KEY_S)){
			scroll.play();
			
			// when a player is already at the bottom most pointer it points him to the highest pointer
			if (select == 6)
				select = 2;
			// else the pointer goes down
			else
				select++;
		}
		
		// To change the current display depending on the pointer
		if (select == 2)
			display = play;
		else if (select == 3)
			display = controls;
		else if (select == 4)
			display = options;
		else if (select == 5)
			display = credits;
		else 
			display = exit;
		
	}/*End of update*/

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
}
