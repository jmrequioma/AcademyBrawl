package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Combatant extends Entity{
	private float x; // position of player
	private float y = 420; 
	private float xa = 0; // movement across the x axis
	private int view = 0; // 0 for facing left and 1 for facing right
	private boolean falling = true;
	private boolean jumping = false;
	private SpriteSheet kIdleL, kIdleR, kWalkL, kWalkR, kJumpL, kJumpR,
	 					kDuckL, kDuckR;
	private Animation kAnimation, kAnimationIL, kAnimationIR, kAnimationWL, kAnimationWR, kAnimationJL, kAnimationJR,
					  kAnimationDL, kAnimationDR;

	@Override
	public void init() {
		try {
			kIdleL = new SpriteSheet("res/idleleft.png", 125, 175);
			kIdleR = new SpriteSheet("res/idleright.png", 125, 175);
			kWalkL = new SpriteSheet("res/walkleft.png", 125, 175);
			kWalkR = new SpriteSheet("res/walkright.png", 125, 175);
			kJumpL = new SpriteSheet("res/jumpleft.png", 111, 220);
			kJumpR = new SpriteSheet("res/jumpright.png", 111, 220);
			kDuckL = new SpriteSheet("res/duckleft.png", 125, 175);
			kDuckR = new SpriteSheet("res/duckright.png", 125, 175);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		kAnimationIL = new Animation(kIdleL, 100);
		kAnimationIR = new Animation(kIdleR, 100);
		kAnimationWL = new Animation(kWalkL, 100);
		kAnimationWR = new Animation(kWalkR, 100);
		kAnimationJL = new Animation(kJumpL, 100);
		kAnimationJR = new Animation(kJumpR, 100);
		kAnimationDL = new Animation(kDuckL, 100);
		kAnimationDR = new Animation(kDuckR, 100);
		
		kAnimation = kAnimationIR;
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		if (!falling)
			g.drawAnimation(kAnimation, x, y);
		else
			if (view == 0)
				g.drawImage(kJumpR.getSubImage(0, 7), x, y);
			else 
				g.drawImage(kJumpL.getSubImage(0, 7), x, y);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		kAnimation.update(delta);
		super.rePosition(x, y); // Foundation for
		super.resize(125, 175); // player hitboxes
		
		Input in = gc.getInput();
		
		if (x >= 0 && x <= 700) { // within bounds
			if (in.isKeyDown(Input.KEY_W) && jumping == false) {
				if (view == 0)
					kAnimation = kAnimationJL;
				else 
					kAnimation = kAnimationJR;
				jumping = true;
			}else if (in.isKeyDown(Input.KEY_A)) {
				if (jumping == false)
					kAnimation = kAnimationWL;
				xa = (200 / 1000.0f) * -delta;
				view = 1;
			}else if (in.isKeyDown(Input.KEY_S)) {
				if (view == 0)	
					kAnimation = kAnimationDR;
				else 
					kAnimation = kAnimationDL;
			} else if (in.isKeyDown(Input.KEY_D)) {
				if (jumping == false)	
					kAnimation = kAnimationWR;
				xa = (200 / 1000.0f) * delta;
				view = 0;
			} else if (!jumping && !falling){
				xa = 0;
				if (view == 0)
					kAnimation = kAnimationIR;
				else 
					kAnimation = kAnimationIL;
			} 
			x += xa; // movement along the x-axis
			gravity();
		} else {
			if (x < 0) {
				x = 1;
			} else if (x > 700) {
				x = 700;
			}
		}	
	}// End of update()
	
	public void gravity(){
		if (jumping == true && falling == false)
			y -=  15;
		if (y == 180)
			falling = true;
		if (y >= 420){
			y = 420;
			jumping = false;
			falling = false;
		}
		if (falling == true)
			y += 6.5;	
	}// End of gravity()
}// End of class
