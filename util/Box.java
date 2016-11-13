package util;

public class Box {
	public float x, y, width, height;
	
	public Box(){}
	
	public Box(float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public float getEndX(){ return (x + width); }
	
	public float getEndY(){ return (y + height); }
	
	public float getHalfWidth(){ return (width / 2.0f); }
	
	public float getHalfHeight(){ return (height / 2.0f); }
	
	public float getCenterX(){ return (x + getHalfWidth()); }
	
	public float getCenterY(){ return (y + getHalfHeight()); }
	
	public boolean hitTest(Box b){
		return (b.getEndX() >= x && b.getEndY() >= y && getEndX() >= b.x && getEndY() >= b.y);
	}
	
	public void resize(float width, float height){
		this.width = width;
		this.height = height;
	}
	
	public void rePosition(float x, float y){
		this.x = x;
		this.y = y;
	}
}
