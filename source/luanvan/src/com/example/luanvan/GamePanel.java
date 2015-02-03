package com.example.luanvan;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.luanvan.element.MyView;
import com.example.luanvan.element.ViewPanel;
import com.example.luanvan.game.Game;
import com.example.luanvan.mainmenu.MainMenu;

public class GamePanel extends ViewPanel{

	MyView view;
	
	public GamePanel(Context context) {
		super(context);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		//view.surfaceChanged(holder, format, width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		view = new MainMenu(this);
		view.surfaceCreated(holder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		view.surfaceDestroyed(holder);
	}

	@Override
	public void doDraw(Canvas c) {
		// TODO Auto-generated method stub
		view.doDraw(c);
	}

	public void startPanelGame(){
		if(view != null)
			surfaceDestroyed(getHolder());
		
		view = new Game(this);
		view.surfaceCreated(getHolder());
	}
	
	public void startPanelMainMenu(){
		if(view != null)
			surfaceDestroyed(getHolder());
		
		view = new MainMenu(this);
		view.surfaceCreated(getHolder());
	}

}
