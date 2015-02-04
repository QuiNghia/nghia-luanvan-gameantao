package com.example.luanvan.element;

import android.graphics.Point;
import android.graphics.PointF;

public class LocationElement extends PointF{

	public LocationElement() {
		super();
	}
	
	public LocationElement(float x, float y) {
		super(x,y);
	}
	
	public LocationElement(Point p){
		super(p);
	}
	@Override
	public String toString() {
		return super.toString();
	}
}
