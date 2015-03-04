package com.example.luanvan.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.luanvan.GamePanel;
import com.example.luanvan.R;
import com.example.luanvan.element.BoundElement;
import com.example.luanvan.element.GameAlertDialog;
import com.example.luanvan.element.MyView;
import com.example.luanvan.element.Sound;


/**
 * 
 * @author Qui Nghia
 *
 */
public class PlaygameMenu extends GameAlertDialog{


	public PlaygameMenu(MyView view) {
		super(view);
		// TODO Auto-generated constructor stub
	}

	BoundElement boBg, boBack, boReplay, boBackT,boReplayT;
	Bitmap bmBg,bmBack, bmReplay;
	
	@Override
	public void doDraw(Canvas c) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		
		c.drawBitmap(bmBg, boBg.getLocationX(), boBg.getLocationY(), paint);
		c.drawBitmap(bmBack, boBackT.getLocationX(), boBackT.getLocationY(), paint);
		c.drawBitmap(bmReplay, boReplayT.getLocationX(), boReplayT.getLocationY(), paint);
	}

	@Override
	public void onCreated() {
		// TODO Auto-generated method stub
		loadBitmap();
		setBound();
	}

	@Override
	public void onDestroyed() {
		// TODO Auto-generated method stub
		boBg = boBack = boReplay = boBackT = boReplayT = null;
		bmBg = bmBack = bmReplay = null;
	}

	public void loadBitmap(){
		bmBg = BitmapFactory.decodeResource(view.getResources(), R.drawable.setting_background_color);
		bmBack = BitmapFactory.decodeResource(view.getResources(), R.drawable.playgame_menu_buton_back);
		bmReplay = BitmapFactory.decodeResource(view.getResources(), R.drawable.playgame_menu_button_replay);
	}

	public void setBound(){
		float x,y,w,h;
		x = y = 0;
		w = view.getWidth();
		h = view.getHeight();
		boBg = new BoundElement(x, y, w, h); 
		bmBg = Bitmap.createScaledBitmap(bmBg, (int)w, (int)h, true);
		
		w = 0.4f*view.getWidth();
		h = w/bmBack.getWidth() * bmBack.getHeight();
		x = view.getWidth()/2 - w/2;
		y = view.getHeight()/2 - h - 10;
		boBack = new BoundElement(x, y, w, h);
		bmBack = Bitmap.createScaledBitmap(bmBack, (int)w, (int)h, true);
		
		y = view.getHeight()/2 + 10;
		boReplay = new BoundElement(x, y, w, h);
		bmReplay = Bitmap.createScaledBitmap(bmReplay, (int)w, (int)h, true);
	
		boBackT = new BoundElement();
		boReplayT = new BoundElement();
		boBackT.Import(boBack);
		boReplayT.Import(boReplay);
		
		
	}
	
	@Override
	public void setShow(boolean show) {
		// TODO Auto-generated method stub
		super.setShow(show);
		if(show){
			setEvent();
			HieuUngShow();
		}
		else{
			view.setEvent();
		}
	}
	
	private void HieuUngShow(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				float delta = getView().getWidth();
				int timeWait = 1000/((Game)view).fps;
				int speed = 20;
				int sl = (int)delta/speed;
				
				boBackT.setLocationX(boBack.getLocationX() + delta);
				boReplayT.setLocationX(boReplay.getLocationX() + delta);
				
				for(int i = 0 ; i < sl ; i ++){
					boBackT.setLocationX(boBackT.getLocationX() - speed);
					boReplayT.setLocationX(boReplayT.getLocationX() - speed);
					try {
						Thread.sleep(timeWait);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				boBackT.Import(boBack);
				boReplayT.Import(boReplay);
			}
		}).start();
		
	}
	
	public void setEvent(){
		view.getView().setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX();
				float y = event.getY();
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					if(boBack.checkPointIn(x, y)){
						((GamePanel)view.getView()).startPanelMainMenu();
					}
					else if(boReplay.checkPointIn(x, y)){
						setShow(false);
						((Game)view).rePlay();
					}
					else 
						setShow(false);
					Sound.mySound.playMusicButton();
					return true;
					
				}
				return false;
			}
		});
	}
}
