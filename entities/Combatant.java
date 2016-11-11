package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;


public class Combatant extends Entity{
	private float x; 
	private float y = 420;
	private float Y = 500;
	private int view = 0; // 0 for facing left and 1 for facing right
	private boolean falling = true;
	private boolean jumping = false;
	private SpriteSheet kIdleL, kIdleR, kWalkL, kWalkR, kJumpL, kJumpR;
	private Animation kAnimation, kAnimationIL, kAnimationIR, kAnimationWL, kAnimationWR, kAnimationJL, kAnimationJR;

	@Override
	public void init() {
		try {
			kIdleL = new SpriteSheet("res/idleleft.png", 90, 175);
			kIdleR = new SpriteSheet("res/idleright.png", 90, 175);
			kWalkL = new SpriteSheet("res/walkleft.png", 90, 175);
			kWalkR = new SpriteSheet("res/walkright.png", 90, 175);
			kJumpL = new SpriteSheet("res/jumpleft.png", 120, 175);
			kJumpR = new SpriteSheet("res/jumpright.png", 120, 175);
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
		
		kAnimation = kAnimationIR;
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		// TODO Auto-generated method stub
		if (falling == false)
			g.drawAnimation(kAnimation, x, y);
		else if (falling == true && y > 330 && y < 420){
			// If falling the animation remains constant
			 if (view == 1)
				g.drawImage(kJumpL.getSprite(0, 3), x, y);
			 else 
				 g.drawImage(kJumpR.getSprite(0, 3), x, y);
		}
	}

	@Override
	public void update(GameContainer gc, int delta) {
		kAnimation.update(delta);
		
		Input in = gc.getInput();
		
		if (x >= 0 && x <= 700) { // within bounds
			if (in.isKeyDown(Input.KEY_W) && view == 0 && jumping == false) {
				kAnimation = kAnimationJR;
				jumping = true;
			} else if (in.isKeyDown(Input.KEY_W) && jumping == false) {
				kAnimation = kAnimationJL;
				jumping = true;
			}else if (in.isKeyDown(Input.KEY_A)) {
				if (jumping == false)
					kAnimation = kAnimationWL;
				x -= (200 / 1000.0f) * delta;
				view = 1;
			} else if (in.isKeyDown(Input.KEY_D)) {
				if (jumping == false)	
					kAnimation = kAnimationWR;
				x += (200 / 1000.0f) * delta;
				view = 0;
			} else if (view == 1 && jumping == false){
				kAnimation = kAnimationIL;
			} else if (jumping == false){
				kAnimation = kAnimationIR;
			}
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
			y -=  10;
		if (y == 300)
			falling = true;
		if (y >= 420){
			y = 420;
			jumping = false;
			falling = false;
		}
		if (falling == true)
			y += 4;	
	}// End of gravity()
}// End of class
