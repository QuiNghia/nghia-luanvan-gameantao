package com.example.luanvan.element;

public class SizeElement {

	float width, height;

	public SizeElement() {
		width = 0;
		height = 0;
	}

	public SizeElement(float width, float height) {
		
		this.width = width;
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "SizeElement [width=" + width + ", height=" + height + "]";
	}
	
	
}
