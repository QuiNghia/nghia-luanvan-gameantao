package com.example.luanvan.element;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class MyView {
	
	private ViewPanel view;
	
	public MyView(ViewPanel view){
		this.view = view;
	}
	public abstract void doDraw(Canvas c);

	public abstract void surfaceChanged(SurfaceHolder holder, int format, int width,int height);
	public abstract void surfaceCreated(SurfaceHolder holder);
	public abstract void surfaceDestroyed(SurfaceHolder holder);
	public abstract void setEvent();
	
	public ViewPanel getView() {
		return view;
	}
	public void setView(ViewPanel view) {
		this.view = view;
	}
	
	public Context getContext(){
		return view.getContext();
	}
	public SurfaceHolder getHolder(){
		return  view.getHolder();
	}
	
	public int getWidth(){
		return view.getWidth();
	}
	
	public int getHeight(){
		
		return view.getHeight();
	}
	
	public Resources getResources(){
		return view.getResources();
	}
	
}

