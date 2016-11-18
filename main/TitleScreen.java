package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import util.Window;


public class TitleScreen extends BasicGameState{
	private int ID = 0;
	
	public static Music music;				// main music that keeps on playing
	public static Music titleMusic;			// title screen music
	public static Music menuMusic;			// main menu music
	public static Music charSelectMusic;	// Character Select Music
	public static Music creditsMusic;		// Credits Music 
	
	//TODO get more music for the upcoming arenas
	public static Music Arena1;
	
	private Sound start;				// sound effect when pressing enter to start
	private boolean started = false;	// to check if enter was pressed
	
	private SpriteSheet screen;			// display for
	private Animation title;			// title screen
	
	private int delta; 					// to store the amount of time passed 
	
	public TitleScreen(int state){}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		// initializing other music to be used in the game
		titleMusic = new Music ("res/music/kombat.wav"); 			
		menuMusic = new Music("res/music/menu.wav");
		charSelectMusic = new Music("res/music/charSelect.wav");
		creditsMusic = new Music("res/music/credits.wav");
		
		Arena1 = new Music("res/music/arena1.wav");					//	1st Arena's background music
		
		start = new Sound ("res/soundEffects/start.wav");			//initialization of sound effect/s 
		
		// sets the background of the title screen with animations
		screen = new SpriteSheet("res/background/titleScreen.png", Window.WIDTH, Window.HEIGHT);	
		title = new Animation(screen, 100);
		
		// plays the music for the title screen
		musicTitle();
		 
	}/*End of init*/

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		
		title.setPingPong(true);  // this means that the animation loops from start to finish and also vice versa
		title.draw();			  // draws the title screen	
	}/*End of render*/

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		this.delta += delta;             // updates the amount of time passed (in milliseconds)
		
		title.update(delta);			// adjust the animation speed to delta time
		
		// if the player has not yet pressed enter the time keeps resetting to 0
		if (started == false){
			this.delta = 0;
		}
		
		// accepting input via Game container
		Input in = gc.getInput();

		// when player presses start the program no longer resets time to 0
		if (in.isKeyPressed(Input.KEY_ENTER)){
			started = true;
			start.play();
			title.setSpeed(title.getSpeed() * 3); // triples the animation speed for emphasis
		}
		
		// when a second passes the game fades into the main menu with also a change in music
		if (this.delta / 1000 == 1){
			sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
			musicMenu();
		}
	}/*End of update*/

	// pauses the background music
	public static void pauseMusic(){
		music.pause();
	}
	
	// resumes the background music from the point of pause
	public static void resumeMusic(){
		music.resume();
	}
	
	// loops the background music
	public static void loopMusic(){
		music.loop();
	}
	
	// lowers the background music volume
	public static void lowerVolume(){
		System.out.println(music.getVolume());
		music.setVolume(music.getVolume() - 0.02f);
	}
	
	// increases the background music volume
	public static void increaseVolume(){
		music.setVolume(music.getVolume() + 0.02f);
	}
	
	// changes the background music to the music during the title screen and loops it
	public static void musicTitle(){
		music = titleMusic;
		loopMusic();
	}
	
	// changes the background music to the music during the menu screen and loops it
	public static void musicMenu(){
		music = menuMusic;
		loopMusic();
	}
	
	// changes the background music to the music during the character select screen and loops it
	public static void musicCharSelect(){
		music = charSelectMusic;
		loopMusic();
	}
	
	// changes the background music to the music during the credits screen and loops it
	public static void musicCredits(){
		music = creditsMusic;
		loopMusic();
	}
	
	// changes the background music to the music during the 1st arena's initialization
	// TODO add more arenas and music for each arena
	public static void musicArena1(){
		music = Arena1;
		loopMusic();
	}
	

	@Override
	public int getID() {
		return ID;
	}
	/*End of class*/
}
