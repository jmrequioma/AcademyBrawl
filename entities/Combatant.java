package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import util.Box;


public class Combatant extends Box{
	public float x;                        // position of player
	public float xa = 0;                   // movement across the x axis

	private float y = 420;                 // position along the y-axis (constant except when jumping, duh)
	private float ya = 0;                  // movement across the y axis
	
	private int view = 0;                  // 0 for facing left and 1 for facing right
	private int player;                    // which player is this player (1 or 2)
	
	private float hP;                      // player hp
	private float energy;                  // player energy gauge for use of ultimate skills
	
	public boolean hit = false;            // if a player is hit
	public boolean moving = false;        // if a player is blocked by another player
	public boolean stopped = false;
	public boolean attacking = false; 
	private boolean falling = false; 
	private boolean jumping = false; 
	
	// Sprite sheets to be used
	private SpriteSheet kIdleL, kIdleR, kWalkL, kWalkR, kJumpL, kJumpR,
	 					kDuckL, kDuckR, kLPunchL, kLPunchR, kSPunchL, kSPunchR,
						kLKickL, kSKickL, kSKickR, kLKickR, kHitL, kHitR;
	
	// Animations to be used
	private Animation kAnimation, kAnimationIL, kAnimationIR, kAnimationWL, kAnimationWR, kAnimationJL, kAnimationJR,
					  kAnimationDL, kAnimationDR, kAnimationLPL, kAnimationLPR, kAnimationSPL, kAnimationSPR,
						kAnimationLKL, kAnimationSKL, kAnimationSKR, kAnimationLKR, kAnimationHL, kAnimationHR;

	// constructor for the class
	public Combatant(int player){
		this.player = player;
		if (player == 2){
			x = 660;       // If 2nd player the view is towards the first player 
			view = 1;	  //and is positioned near the end of the screen
		}
	}
	
	// Initialization of all variables
	public void init() {
		super.resize(125, 175); // sets the width and height of the player for collision detection
		super.rePosition(x, y); // sets the current players position for collision detection
		try {
			// initialization of sprite sheets
			kIdleL = new SpriteSheet("res/characters/comsci/idleleft.png", 125, 175);
			kIdleR = new SpriteSheet("res/characters/comsci/idleright.png", 125, 175);
			kWalkL = new SpriteSheet("res/characters/comsci/walkleft.png", 125, 175);
			kWalkR = new SpriteSheet("res/characters/comsci/walkright.png", 125, 175);
			kJumpL = new SpriteSheet("res/characters/comsci/jumpleft.png", 111, 220);
			kJumpR = new SpriteSheet("res/characters/comsci/jumpright.png", 111, 220);
			kDuckL = new SpriteSheet("res/characters/comsci/duckleft.png", 125, 175);
			kDuckR = new SpriteSheet("res/characters/comsci/duckright.png", 125, 175);
			kLPunchL = new SpriteSheet("res/characters/comsci/lightpunchleft.png", 160, 175);
			kLPunchR = new SpriteSheet("res/characters/comsci/lightpunchright.png", 160, 175);
			kSPunchL = new SpriteSheet("res/characters/comsci/heavypunchleft.png", 160, 175);
			kSPunchR = new SpriteSheet("res/characters/comsci/heavypunchright.png", 160, 175);
			kLKickL = new SpriteSheet("res/characters/comsci/lightkickleft.png", 160, 175);
			kLKickR = new SpriteSheet("res/characters/comsci/lightkickright.png", 160, 175);
			kSKickL = new SpriteSheet("res/characters/comsci/heavykickleft.png", 160, 175);
			kSKickR = new SpriteSheet("res/characters/comsci/heavykickright.png", 160, 175);
			kHitR = new SpriteSheet("res/characters/comsci/ghitright.png", 125, 175);
			kHitL = new SpriteSheet("res/characters/comsci/ghitleft.png", 125, 175);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		// initialization of Animations per action 
		kAnimationIL = new Animation(kIdleL, 100);
		kAnimationIR = new Animation(kIdleR, 100);
		kAnimationWL = new Animation(kWalkL, 100);
		kAnimationWR = new Animation(kWalkR, 100);
		kAnimationJL = new Animation(kJumpL, 100);
		kAnimationJR = new Animation(kJumpR, 100);
		kAnimationDL = new Animation(kDuckL, 100);
		kAnimationDR = new Animation(kDuckR, 100);
		kAnimationLPL = new Animation(kLPunchL, 100);
		kAnimationLPR = new Animation(kLPunchR, 100);
		kAnimationSPL = new Animation(kSPunchL, 100);
		kAnimationSPR = new Animation(kSPunchR, 100);
		kAnimationLKL = new Animation(kLKickL, 100);
		kAnimationLKR = new Animation(kLKickR, 100);
		kAnimationSKL = new Animation(kSKickL, 100);
		kAnimationSKR = new Animation(kSKickR, 100);
		kAnimationHL = new Animation(kHitL, 100);
		kAnimationHR = new Animation(kHitR, 100);
		
		// main animation to be drawn
		kAnimation = kAnimationIL;  // set as idly facing left by default
		kAnimation.setSpeed(0.69f); // set the speed of the animation to 69% speed to improve rendering
	}
	
	// Rendering of graphics
	public void render(GameContainer gc, Graphics g) {
		//TODO improve this snippet of code in order to consistently draw animation instead of
		//adding a special case when falling 
		
		// if the player is not falling then there is animation
		kAnimation.setSpeed(0.69f); // set the speed of the animation to 69% speed to improve rendering
		if (!falling)
			g.drawAnimation(kAnimation, x, y);
		else                                                  // the moment the player is falling the player is shown to be free falling
			if (view == 0)
				g.drawImage(kJumpR.getSubImage(0, 7), x, y);  // gets the image where a free falling image is seen
			else 
				g.drawImage(kJumpL.getSubImage(0, 7), x, y); // gets the image where a free falling image is seen
	}

	// Update every new action
	public void update(GameContainer gc, int delta) {
		if (gc.isPaused())
			kAnimation.stop();
	    else{
	    	kAnimation.start();
	    	kAnimation.update(delta);
		
	    	super.rePosition(x, y); // updates the current x and y coordinates of the player
	    	if (!attacking)
	    		super.resize(125, 175);
		
	    	// user input
	    	Input in = gc.getInput();
		
	    	// Sees if the knock back animation is done
	    	if (kAnimation.getCurrentFrame() == kAnimationHL.getImage(3) ||
	    			kAnimation.getCurrentFrame() == kAnimationHR.getImage(3)
	    			&& hit)
	    	{
	    		xa = 0;
	    		hit = false;
	    		System.out.println(hit);
	    	}
    	
	    	// Sees if the attack animation is finished in order to 
	    	// update the player's attacking variable
	    	if (    kAnimation.getCurrentFrame() == kAnimationLPR.getImage(4) ||
	    			kAnimation.getCurrentFrame() == kAnimationLPL.getImage(4) ||
	    			kAnimation.getCurrentFrame() == kAnimationSPR.getImage(8) ||
	    			kAnimation.getCurrentFrame() == kAnimationSPL.getImage(8) ||
	    			kAnimation.getCurrentFrame() == kAnimationLKR.getImage(5) ||
	    			kAnimation.getCurrentFrame() == kAnimationLKL.getImage(5) ||
	    			kAnimation.getCurrentFrame() == kAnimationSKR.getImage(9) ||
	    			kAnimation.getCurrentFrame() == kAnimationSKL.getImage(9) 
	    			&& attacking)
	    	{
	    		attacking = false;
	    	}
    	
	    	// Player 1 controls
	    	if (player == 1){
	    		// if player is not hit he can attack
	    		if (!hit){
	    			// TODO resize the box to simulate an actual punch or kick
	    			// FIXME heavy attacks causes a dual proc of knock back animation
	    			//light punch
	    			if (in.isKeyPressed(Input.KEY_U) && !attacking) {
	    				attacking = true;
	    				if (view == 0) // if facing left
	    					kAnimation = kAnimationLPR;
	    				else           // if facing right
	    					kAnimation = kAnimationLPL;
				
	    				//light kick 
	    			}else if (in.isKeyPressed(Input.KEY_J) && !attacking) {
	    				attacking = true;
	    				if (view == 0)	// if facing left
	    					kAnimation = kAnimationLKR;
	    				else 			// if facing right
	    					kAnimation = kAnimationLKL;
		    		
	    				//heavy punch
	    			}else if (in.isKeyPressed(Input.KEY_I) && !attacking) {
	    				attacking = true;
	    				if (view == 0)	// if facing left
	    					kAnimation = kAnimationSPR;
	    				else 			// if facing right
	    					kAnimation = kAnimationSPL;
		    		
	    				//heavy kick	
	    			}else if (in.isKeyPressed(Input.KEY_K) && !attacking) {
	    				attacking = true;
	    				if (view == 0)	// if facing left
	    					kAnimation = kAnimationSKR;
	    				else 			// if facing right
	    					kAnimation = kAnimationSKL;
	    			}
	    			// Getting hit by another player 
	    		} else if (hit){
	    			if (view == 1){ // if facing left
	    				kAnimation = kAnimationHL;
	    				if (x <= 665)
	    					xa = 4;
	    			} else{    	   // if facing right
	    				kAnimation = kAnimationHR;
	    				if (x >= 5)
	    					xa = -4;
	    			}
	    			x += xa;
	    		}
		    	
	    		// If the player is either not hit and not attacking he can move
	    		if (!attacking && !hit){
				
	    			//jumping
	    			// jump only works if the player is not currently jumping
	    			if (in.isKeyPressed(Input.KEY_W) && !jumping && !falling) {
	    				ya = 15;        // sets the y-velocity
	    				if (view == 0) // if facing left
	    					kAnimation = kAnimationJR;
	    				else           // if facing right
	    					kAnimation = kAnimationJL;
	    				jumping = true;

	    				//ducking 	
	    			}else if (in.isKeyDown(Input.KEY_S)) {
	    				xa = 0; 		 // stops the player at that point 
	    				if (view == 0)	// if facing left
	    					kAnimation = kAnimationDR;
	    				else           // if facing right
	    					kAnimation = kAnimationDL;
	    				moving = false;
	    				super.resize(60, 125);
				
	    				//moving left
	    			}else if (in.isKeyDown(Input.KEY_A)) {
	    				view = 1; 		                     // changes the view of the player to facing right
	    				if (!jumping)                       // if not currently in the air it changes the animation
	    					kAnimation = kAnimationWL;
	    				if (!stopped)
	    					xa = (200 / 1000.0f) * -delta; // sets the x-velocity
	    				moving = true;
					
	    				//moving right
	    			} else if (in.isKeyDown(Input.KEY_D)) {
	    				view = 0;            			  // changes the view to left
	    				if (!jumping)	                  // if not currently in the air it changes the animation
	    					kAnimation = kAnimationWR;
	    				if (!stopped)
	    					xa = (200 / 1000.0f) * delta; // sets the x-velocity
	    				moving = true;
					
	    				// if player is not doing any actions
	    			} else if (!jumping && !falling && !attacking){
	    				xa = 0;                         // player is not moving
	    				if (view == 0)                  // if facing left
	    					kAnimation = kAnimationIR;  
	    				else                            // if facing right
	    					kAnimation = kAnimationIL;
	    				moving = false;
	    			}
	    			x += xa;						 // updates the current position of the player
	    			logic();                         // external function used to keep the player in bounds
	    		}
	    		// Player 2 controls
	    	} else {
	    		// if player is not hit he can attack
	    		if (!hit){
	    			// FIXME heavy attacks causes a dual proc of knock back animation    	
	    			//light punch
	    			if (in.isKeyPressed(Input.KEY_NUMPAD4) && !attacking) {
	    				attacking = true;
	    				if (view == 0) // if facing left
	    					kAnimation = kAnimationLPR;
	    				else           // if facing right
	    					kAnimation = kAnimationLPL;
				
	    				//light kick 
	    			}else if (in.isKeyPressed(Input.KEY_NUMPAD1) && !attacking) {
	    				attacking = true;
	    				if (view == 0)	// if facing left
	    					kAnimation = kAnimationLKR;
	    				else 			// if facing right
	    					kAnimation = kAnimationLKL;
		    		
	    				//heavy punch
	    			}else if (in.isKeyPressed(Input.KEY_NUMPAD5) && !attacking) {
	    				attacking = true;
	    				if (view == 0)	// if facing left
	    					kAnimation = kAnimationSPR;
	    				else 			// if facing right
	    					kAnimation = kAnimationSPL;
		    		
	    				//heavy kick	
	    			}else if (in.isKeyPressed(Input.KEY_NUMPAD2) && !attacking) {
	    				attacking = true;
	    				if (view == 0)	// if facing left
	    					kAnimation = kAnimationSKR;
	    				else 			// if facing right
	    					kAnimation = kAnimationSKL;
	    			} 
		    	
	    			// Getting hit by another player 
	    		} else if (hit){
	    			if (view == 0){ // if facing left
	    				kAnimation = kAnimationHR;
	    				if (x >= 5)
	    					xa = -4;
	    			} else{    	  // if facing right
	    				kAnimation = kAnimationHL;
	    				if (x <= 665)
	    					xa = 4;
	    			}
	    			x += xa;
	    		}
		    
	    		// If the player is either not hit or not attacking he can move
	    		if (!attacking && !hit){
	    			//jumping
	    			// jump only works if the player is not currently jumping
	    			if (in.isKeyPressed(Input.KEY_UP) && !jumping && !falling) {
	    				ya = 15;        // sets the y-velocity
	    				if (view == 0) // if facing left
	    					kAnimation = kAnimationJR;
	    				else           // if facing right
	    					kAnimation = kAnimationJL;
	    				jumping = true;
					
	    				//ducking 	
	    			}else if (in.isKeyDown(Input.KEY_DOWN)) {
	    				xa = 0; 		 // stops the player at that point 
	    				if (view == 0)	// if facing left
	    					kAnimation = kAnimationDR;
	    				else           // if facing right
	    					kAnimation = kAnimationDL;
	    				moving = false;
	    				super.resize(60, 125);
				
	    				//moving left
	    			}else if (in.isKeyDown(Input.KEY_LEFT)) {
	    				view = 1; 		                     // changes the view of the player to facing right
	    				if (!jumping)                       // if not currently in the air it changes the animation
	    					kAnimation = kAnimationWL;
	    				if (!stopped)
	    					xa = (200 / 1000.0f) * -delta; // sets the x-velocity 
	    				moving = true;
					
	    				//moving right
	    			} else if (in.isKeyDown(Input.KEY_RIGHT)) {
	    				view = 0;            			  // changes the view to left
	    				if (!jumping)	                  // if not currently in the air it changes the animation
	    					kAnimation = kAnimationWR;
	    				if (!stopped)
	    					xa = (200 / 1000.0f) * delta; // sets the x-velocity 
	    				moving = true;
					
	    				// if player is not doing any actions
	    			} else if (!jumping && !falling && !attacking){
	    				xa = 0;                         // player is not moving
	    				if (view == 0)                  // if facing left
	    					kAnimation = kAnimationIR;  
	    				else                            // if facing right
	    					kAnimation = kAnimationIL;
	    				moving = false;
	    			}
	    			x += xa;						 // updates the current position of the player
	    			logic();                         // external function used to keep the player in bounds
	    		}
	    	}
	    }
	}
	  // End of update()
	
	// Contains some game logics that are updated every update call
	public void logic(){
		if (x <= 1) 		 		// if the player is nearing the left side of the screen
			x = 1;					// gets repositioned to the left edge of the screen
		
		if (x >= 670)				// if the player is nearing the right side of the screen
			x = 670;				// gets repositioned to the right edge of the screen
		
		if (jumping && !falling)    // if the player is starting to jump
			y -=  ya;			    // y-position of is updated by the y-velocity	
		
		if (y == 135 && jumping){   // when the player reaches the peak of his jump 
			falling = true;         // falling occurs and the player momentarily stops in air
		    jumping = false;
		    ya = 0;				    // falling occurs and the player momentarily stops in air
		}   
		  
		if (y >= 400 && falling){  // checks if the player hits the ground
			y = 420;			   // repositions the player to the default y-position
			ya = 0;                // player stops going up or down
			jumping = false;      
			falling = false;
		}
		
		if (falling && !jumping){
			ya += 1.5;			  // if the player is currently falling ya or gravity gradually increases
			y += ya;			  // and the player's descent gets faster
		}
	}// End of logic()
	
	// gets the current view of the player (facing left or right)
	public int getView(){
		return view;
	}
	
	// sets the current view of the player (facing left or right)
	public void setView(int view){
		this.view = view;
	}
	
}// End of class
