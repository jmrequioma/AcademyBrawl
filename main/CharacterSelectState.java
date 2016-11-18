package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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

public class CharacterSelectState extends BasicGameState{
	private int ID = 2;	
	
	//the pointer of which character is currently highlighted
	private int index = 1;
	
	// the player who is currently choosing
	private int player = 1;
	
	// storing the numerical value of the character chosen by the players
	private int player1Choice = 1;
	private int player2Choice = 1;
	
	// background image
	private Image background;
	
	// animation for highlighting the currently focused character
	private SpriteSheet one, two, three, four, five, six ,seven, eight, nine					;
	private Animation selected, select1, select2, select3, select4, select5, select6, select7, select8, select9; 
	
	// appropriate sound effects
	private Sound selecting ,chosen, cancel;
	
	public CharacterSelectState(int state){}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// background image initialization
		background = new Image("res/selectionEffects/selection.png");
		
		// sound effect initalization
		selecting = new Sound("res/soundEffects/charSelect.wav");
		chosen = new Sound("res/soundEffects/charSelected.wav");
		cancel = new Sound("res/soundEffects/close.wav");
		
		// Sprite sheet initalization
		one = new SpriteSheet("res/selectionEffects/select1.png", Window.WIDTH, Window.HEIGHT);
		two = new SpriteSheet("res/selectionEffects/select2.png", Window.WIDTH, Window.HEIGHT);
		three = new SpriteSheet("res/selectionEffects/select3.png", Window.WIDTH, Window.HEIGHT);
		four = new SpriteSheet("res/selectionEffects/select4.png", Window.WIDTH, Window.HEIGHT);
		five = new SpriteSheet("res/selectionEffects/select5.png", Window.WIDTH, Window.HEIGHT);
		six = new SpriteSheet("res/selectionEffects/select6.png", Window.WIDTH, Window.HEIGHT);
		seven = new SpriteSheet("res/selectionEffects/select7.png", Window.WIDTH, Window.HEIGHT);
		eight = new SpriteSheet("res/selectionEffects/select8.png", Window.WIDTH, Window.HEIGHT);
		nine = new SpriteSheet("res/selectionEffects/select9.png", Window.WIDTH, Window.HEIGHT);
		
		// Animation initialization
		select1 = new Animation(one, 100);
		select2 = new Animation(two, 100);
		select3 = new Animation(three, 100);
		select4 = new Animation(four, 100);
		select5 = new Animation(five, 100);
		select6 = new Animation(six, 100);
		select7 = new Animation(seven, 100);
		select8 = new Animation(eight, 100);
		select9 = new Animation(nine, 100);
		
		// default, pointing to the first character
		selected = select1;
	
	}/*End of init*/

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		
		background.draw();			 // background drawing
		
		selected.setPingPong(true);	 // this means that the animation loops from start to finish and also vice versa	  	
		selected.draw();			 // draws the title screen
		
	}/*End of render*/

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		selected.update(delta);  			// to adjust the frame rate
		
		Input in = gc.getInput();
		
		// added in case the players want to go back to the main menu state
		// TODO: pop up a confirmation message
		if (in.isKeyPressed(Input.KEY_ESCAPE)){
			TitleScreen.musicMenu();
			sbg.enterState(GameTester.menu, new FadeOutTransition(), new FadeInTransition());
		}
		
		// when a player has decided on a character
		// TODO add confirmation when 2nd player chooses his character
		if (in.isKeyPressed(Input.KEY_ENTER)){
			chosen.play();				  // play chosen sound effect	 
			if (player == 1){             // if player1 
				player1Choice = index;	  // saves his selected index
				player = 2;				  // changes to player 2 
				index = 1; 				  // places pointer back to its default position	
			
			} else {					   			// if player 2
				player2Choice = index;              // instantly starts the match
				player = 1;							// resets back to lpayer 1
				TitleScreen.musicArena1();          // TODO: add a Splash Screen to show case both characters before a match
				sbg.enterState(GameTester.play, new FadeOutTransition(), new FadeInTransition());
			}
			// FIXME: add confirmation or allowing dual character selection 
		} else if (in.isKeyPressed(Input.KEY_BACK) && player == 1){
			cancel.play();
			player = 1;
		}
		
		// logic when scrolling through characters
		// TODO: dual character select and maybe add a stage select &/ mechanics to win conditions
		if (in.isKeyPressed(Input.KEY_UP) || in.isKeyPressed(Input.KEY_W)){
			selecting.play();
			if (index != 1 && index != 2 && index != 3){	// if index is not one of the topmost the index is moved upward in the column
				index -= 3;									
			} else {										// else the pointer is moved to the lowest box in the column
				index += 6;	
			}
		}
		else if (in.isKeyPressed(Input.KEY_DOWN) || in.isKeyPressed(Input.KEY_S)){
			selecting.play();
			if (index != 7 && index != 8 && index != 9){    // if the index is not the lowest in the column then the pointer can move downward
				index += 3;
			} else {										// else, moves the pointer to the topmost box in the column
				index -= 6;
			}
		} else if (in.isKeyPressed(Input.KEY_LEFT) || in.isKeyPressed(Input.KEY_A)){
			selecting.play();
			if (index != 1 && index != 4 && index != 7){	// if the index is not in the leftmost edges then the pointer can move left
				index -= 1;
			} else {										// else, the pointer gets moved to the rightmost boxes in the row
				index += 2;
			}
			
		}else if (in.isKeyPressed(Input.KEY_RIGHT) || in.isKeyPressed(Input.KEY_D)){
			selecting.play();
			if (index != 3 && index != 6 && index != 9){    // if the index is not in the rightmost edges then the pointer can move right
				index += 1;
			} else {										// else, the pointer gets moved to the leftmost boxes in the row
				index -= 2;
			}
		}
		
		// Updates the highlighted character depending on the selected index
		// TODO: dual highlighting
		if (index == 1)
			selected = select1;
		else if (index == 2)
			selected = select2;
		else if (index == 3)
			selected = select3;
		else if (index == 4)
			selected = select4;
		else if (index == 5)
			selected = select5;
		else if (index == 6)
			selected = select6;
		else if (index == 7)
			selected = select7;
		else if (index == 8)
			selected = select8;
		else if (index == 9)
			selected = select9;

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}
