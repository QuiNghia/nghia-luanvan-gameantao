package com.example.luanvan.element;

public class BoundElement {

	private LocationElement location = new LocationElement();
	private SizeElement size = new SizeElement();

	public BoundElement() {
		// TODO Auto-generated constructor stub
	}

	public BoundElement(LocationElement location, SizeElement size) {
		super();
		this.location = location;
		this.size = size;
	}
	
	public BoundElement(float x, float y, float width, float height) {
		location = new LocationElement(x, y);
		size = new SizeElement(width, height);
	}
	
	public void Import(BoundElement b){
		location.x = b.getLocationX();
		location.y = b.getLocationY();
		size.height = b.getHeight();
		size.width = b.getWidth();
	}

	public LocationElement getLocation() {
		return location;
	}

	public void setLocation(LocationElement location) {
		this.location = location;
	}

	public SizeElement getSize() {
		return size;
	}

	public void setSize(SizeElement size) {
		this.size = size;
	}
	
	public float getLocationX(){
		return location.x;
	}
	
	public float getLocationY(){
		return location.y;
	}
	
	public float getWidth(){
		return size.getWidth();
	}
	
	public float getHeight(){
		return size.getHeight();
	}
	
	public void setLocationX(float x){
		location.set(x, location.y);
	}
	public void setLocationY(float y){
		location.set(location.x, y);
	}
	
	public boolean checkPointIn(float x, float y){
		return x>= location.x && x<= (location.x + size.width) 
				&& y >= location.y && y <= (location.y + size.height);
	}

	@Override
	public String toString() {
		return "BoundElement [" + location.x+","+location.y+","+size.width+","+size.height + "]";
	}
	
	
}
