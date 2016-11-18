package main;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import entities.*;

public class PlayState extends BasicGameState {
	private int ID = 6;
	private int delta;
	
	private Sound pause, resume;
	
	private Combatant player1, player2;
	
	//TODO do this
	private Image ui;    // for health bars, timers. etc
	
	// TODO add UI to improve health bars
	private float hbx1 = 10;	 // health bar coodinates
	private float hbx2 = 490;
	private float hby = 15;
	
	private float hbw = 300;     // health bar width
	private float hbwM = 292;    // health bar length
	private float hbwM2 = -292;  // health bar length
	private float hbh = 30;      // health bar height
	private Rectangle hb;        // health background
	private Rectangle hbIn;	     // health foreground
	private Rectangle hb2;	     // health background
	private Rectangle hb2In;     // health foreground 
	public PlayState(int state) {}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player1 = new Combatant(1);   // Make player 1
		player2 = new Combatant(2);   // Make player 2
		player1.init();               // Initialize player 1
		player2.init();               //Initialize player 2
		
		// sound effects when pausing and unpausing
		pause = new Sound("res/soundEffects/pause.wav");
		resume = new Sound("res/soundEffects/resume.wav");
		
		hb = new Rectangle(hbx1, hby, hbw, hbh);   // first player health bar
		hb2 = new Rectangle(hbx2, hby, hbw, hbh);    // second player health bar
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
				
		player1.render(gc, g);
		player2.render(gc, g);
		
		// show amount of time passed and how to puase and resume
		g.drawString("TIME ELAPSED = " + delta, 100, 100);
		if (!gc.isPaused())
			g.drawString("PRESS ESC TO PAUSE", 100, 50);
		else 
			g.drawString("PRESS ESC TO UNPAUSE", 100, 50);
		
		// initialize health bars
		hbIn = new Rectangle(hbx1 + 4, hby + 4, hbwM, hbh - 8);
		hb2In = new Rectangle(790 - 4, hby + 4, hbwM2, hbh - 8);
		
		g.fill(hb);
		g.fill(hb2);
		g.draw(hb);
		g.draw(hb2);
		
		// coloring and filling the health bar
		g.setColor(Color.red);
		g.setLineWidth(0);
		g.fill(hbIn);
		
		// to keep the health bar from overflowing
		if (hbwM > 0) {
			g.draw(hbIn);
		} else {
			hbwM = 0;
		}
		
		// coloring and filling the health bar
		g.setColor(Color.blue);
		g.fill(hb2In);
		
		// to keep the health bar from overflowing
		if (hbwM2 < 0) {
			g.draw(hb2In);
		} else {
			hbwM2 = 0;
		}
		g.setColor(Color.gray);  // background

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		this.delta += delta;		// update the amount of time passed
		
		player1.update(gc, delta);
		player2.update(gc, delta);
		
		Input in = gc.getInput();
		
		// Pausing while in game
		//TODO add a pop up UI for various functions
		if (in.isKeyPressed(Input.KEY_ESCAPE)){
			if (!gc.isPaused()){
				pause.play();
				TitleScreen.pauseMusic();
				gc.pause();
			} else {
				resume.play();
				TitleScreen.resumeMusic();
				gc.setPaused(false);
			}
			
		}

			if (player1.attacking){
				if (player1.getView() == 0)      // the players hit box extends when attacking
					player1.resize(175, 175);
				else 
					player2.resize(175, 175);    // the players hit box extends when attacking
				if (player1.hitTest(player2)){
					player2.hit = true;          // if player 1 hits player 2
					hbwM2 += 3;                  // player 1loses hp
				}
			}
			
			if (hbwM2 >= 0){
				System.out.println("PLAYER 2 LOSE");    // if hp is zero you are led to the game analysis state 
				TitleScreen.musicCredits();				// TODO add some victory taunt and victory UI
				sbg.enterState(GameTester.summary, new FadeOutTransition(), new FadeInTransition());
			}
			
			else if (player2.attacking){
				if (player2.getView() == 0)		// the players hit box extends when attacking
					player2.resize(175, 175);
				else 
					player1.resize(175, 175);  // the players hit box extends when attacking
				if (player2.hitTest(player1)){
					player1.hit = true;        // if player 2 hits player 1
					hbwM -= 3;                 // player 1loses hp
				}
			if (hbwM <= 0){
				System.out.println("PLAYER 1 LOSE");    // if hp is zero you are led to the game analysis state 
				TitleScreen.musicCredits();				// TODO add some victory taunt and victory UI
				sbg.enterState(GameTester.summary, new FadeOutTransition(), new FadeInTransition());
			}
			
			}
			if (player1.hitTest(player2) || player2.hitTest(player1)){
				// when hit repositions the player just a bit to falsify the collision
				if (player1.moving && !player2.moving){
					if (player1.x < player2.x)
						player1.x = player2.x - 100;
					else 
						player1.x = player2.x + 100;
					//FIXME too much acceleration when landing from a jump
					//player2.x += (3 * player1.xa);
				} else if (!player1.moving && player2.moving){
					if (player2.x < player1.x)
						player2.x = player1.x - 100;
					else 
						player2.x = player1.x + 100;
					//FIXME too much acceleration when landing from a jump
					//player1.x += (3 * player2.xa);
				} else{
					// if both players are moving and they collide, stops them 
					player1.xa = 0;
					player2.xa = 0;
					player1.stopped = true;
					player2.stopped = true;
				}
			} else {
				// allows them to move when not collided
				player1.stopped = false;
				player2.stopped = false;
			}
			
		
		// Makes both players face each other
		if (player1.x > player2.x){
			player1.setView(1);
			player2.setView(0);
		} else {
			player1.setView(0);
			player2.setView(1);
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
}
