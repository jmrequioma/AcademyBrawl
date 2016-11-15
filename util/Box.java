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
	public float getHalfWidth(){ return (width / 2.0f); }
	
	public float getHalfHeight(){ return (height / 2.0f); }
	
	public float getCenterX(){ return (x + getHalfWidth()); }
	
	public float getCenterY(){ return (y + getHalfHeight()); }
    
	// Gets the x-points
	public float getTopLeftX(){ return (x); }
	public float getBottomRightX(){ return (getCenterX() + (getHalfWidth() / 2.0f)); }
	
	// Gets the y-points
	public float getTopLeftY(){ return (y + height); }
	public float getBottomRightY(){ return (getCenterY() + (getHalfHeight() / 2.0f)); }
	
	public boolean hitTest(Box b){
		
		// if one rectangles is on the left side of other
        if (getBottomRightX() < b.getTopLeftX() || b.getBottomRightX() < getTopLeftX()) {
            return false;
        }

        // if one of the rectangles is below the other.
        if (getTopLeftY() < b.getBottomRightY() || b.getTopLeftY() < getBottomRightY()) {
            return false;
        }
        return true;
	}
	
	// Resizes the box
	public void resize(float width, float height){
		this.width = width;
		this.height = height;
	}
	//Repositions the box
	public void rePosition(float x, float y){
		this.x = x;
		this.y = y;
	}
}
