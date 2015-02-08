package com.example.luanvan.element;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public abstract class GameAlertDialog{

	protected MyView view;
	private boolean show;
	
	public GameAlertDialog(MyView view) {
		this.view = view;
		show = false;
	}
	
	public abstract void doDraw(Canvas c) ;

	public abstract void onCreated();
	
	public abstract void onDestroyed();

	public MyView getView() {
		return view;
	}

	public void setView(MyView view) {
		this.view = view;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
	
	
}
