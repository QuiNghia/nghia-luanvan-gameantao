package com.example.luanvan.mainmenu;

import com.example.luanvan.GamePanel;
import com.example.luanvan.GamePlayActivity;
import com.example.luanvan.MainActivity;
import com.example.luanvan.R;
import com.example.luanvan.SyncActivity;
import com.example.luanvan.element.BoundElement;
import com.example.luanvan.element.MyView;
import com.example.luanvan.element.Sound;
import com.example.luanvan.element.ViewPanel;
import com.example.luanvan.game.Game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainMenu extends MyView{

	private MainMenu myMainMenu;
	private BoundElement  boBtnStart, boBtnExit, boBtnSetting, boBtnHelp, boBtnSync, boBee;
	private Thread thrDraw;
	private Bitmap bmBackground, bmBtnStart,bmBtnExit, bmBtnSetting, bmBtnHelp, bmBtnSync;
	private Bitmap bmBee[];
	private GameOption gameOption;
	private int fps = 20, timeDelay; 
	private float width, height;
	private boolean running;
	private int cntBee = 0;
	
//	public MainMenu(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//	}

	public MainMenu(ViewPanel view) {
		super(view);
		myMainMenu = this;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
		running = true;
		cntBee = 0;
		loadBitmap();
		setBoundElement();
		setEvent();
		gameOption = new GameOption(this);
		gameOption.onCreated();
		thrDraw = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int timeWait = 1000/fps;
				Canvas cv ;
				while(running){
					cv= getHolder().lockCanvas();
					if(cv!=null){
						doDraw(cv);
					}
					getHolder().unlockCanvasAndPost(cv);
					try {
						Thread.sleep(timeWait);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}
		});
		thrDraw.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if(thrDraw != null && thrDraw.isAlive() ){
			running = false;
			//((Activity)getContext()).finish();
		}
	}

	@Override
	public void doDraw(Canvas c) {
		// TODO Auto-generated method stub
		drawBackground(c);
		drawButton(c);
		drawBee(c);
		if(gameOption != null && gameOption.isShow())
			gameOption.doDraw(c);
	}
	
	public void drawBackground(Canvas c){
		Paint p = new Paint();
		c.drawBitmap(bmBackground, 0, 0, p);
	}
	
	public void drawButton(Canvas c){
		Paint p = new Paint();
		c.drawBitmap(bmBtnExit, boBtnExit.getLocationX(), boBtnExit.getLocationY(), p);
		c.drawBitmap(bmBtnHelp, boBtnHelp.getLocationX(), boBtnHelp.getLocationY(), p);
		c.drawBitmap(bmBtnSetting, boBtnSetting.getLocationX(), boBtnSetting.getLocationY(), p);
		c.drawBitmap(bmBtnStart, boBtnStart.getLocationX(), boBtnStart.getLocationY(), p);
		c.drawBitmap(bmBtnSync, boBtnSync.getLocationX(), boBtnSync.getLocationY(), p);
		
	}
	
	public void drawBee(Canvas c){
		
		Paint p = new Paint();
		c.drawBitmap(bmBee[cntBee], boBee.getLocationX(), boBee.getLocationY(), p);
		
		if(++cntBee >= 12)
			cntBee=0;
	}
	
	public void setBoundElement(){
		float x,y,w,h;
		
		bmBackground = Bitmap.createScaledBitmap(bmBackground, getWidth(), getHeight(), true);
		
		w = getWidth() * 150f/320f;
		h = w/bmBtnStart.getWidth() * bmBtnStart.getHeight();
		x = getWidth() - w;
		y =  getHeight()/2 - h - 10f;
		boBtnStart = new BoundElement(x,y, w, h);
		bmBtnStart = Bitmap.createScaledBitmap(bmBtnStart, (int)w, (int)h, true);
		
		y = getHeight()/2f + 10f;
		boBtnExit = new BoundElement(x, y, w, h);
		bmBtnExit = Bitmap.createScaledBitmap(bmBtnExit, (int)w, (int)h, true);
		
		w = h = 66f/320f * getWidth();
		x = getWidth() - w;
		y = getHeight() - 66;
		boBtnSetting = new BoundElement(x, y, w, h);
		bmBtnSetting = Bitmap.createScaledBitmap(bmBtnSetting, (int)w, (int)h, true);
		
		x = 0;
		boBtnHelp = new BoundElement(x, y, w, h);
		bmBtnHelp = Bitmap.createScaledBitmap(bmBtnHelp, (int)w, (int)h, true);
		
		x = getWidth() - w;
		y = 0;
		boBtnSync = new BoundElement(x, y, w, y);
		bmBtnSync = Bitmap.createScaledBitmap(bmBtnSync, (int)w, (int)h, true);
		
		w = 70f/320f*getWidth();
		h = w/bmBee[0].getWidth() * bmBee[0].getHeight();
		x = boBtnStart.getLocationX()+ boBtnStart.getWidth()/2 - w/2;
		y = boBtnStart.getLocationY() - h;
		boBee = new BoundElement(x, y, w,h);
		for(int i = 0 ; i < 12 ; i ++){
			bmBee[i] = Bitmap.createScaledBitmap(bmBee[i], (int)w, (int)h, true);
		}
	}
	
	public  void loadBitmap(){
		
		bmBackground = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_background);
		bmBtnExit    = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_button_thoat);
		bmBtnHelp    = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_button_help);
		bmBtnSetting = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_button_setting);
		bmBtnStart   = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_button_bat_dau);
		bmBtnSync    = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_button_syns);
		bmBee        = new Bitmap[12];
		bmBee[0]     = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee1);
		bmBee[1]     = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee2);
		bmBee[2]     = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee3);
		bmBee[3]     = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee4);
		bmBee[4]     = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee5);
		bmBee[5]     = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee6);
		bmBee[6]     = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee7);
		bmBee[7]     = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee8);
		bmBee[8]     = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee9);
		bmBee[9]     = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee10);
		bmBee[10]    = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee11);
		bmBee[11]    = BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee12);
		
	}
	
	public void setEvent(){
		getView().setOnTouchListener(new View.OnTouchListener() {
			
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction() != MotionEvent.ACTION_DOWN)
					return false;
				float x = event.getX();
				float y = event.getY();
				boolean kt = true;
				
				if(boBtnExit.checkPointIn(x, y)){
					//thoat
					//updateThread.setRunning(false);
					surfaceDestroyed(getHolder());
					//Sound.mySound.destroy();
					System.exit(0);
					//((Activity) getContext()).finish();
				}
				else if(boBtnHelp.checkPointIn(x, y)){
					// tro giup
				}
				else if(boBtnSetting.checkPointIn(x, y)){
					//cai dat
					if(gameOption == null){
						gameOption = new GameOption(myMainMenu);
						gameOption.onCreated();
					}
					if( gameOption.isShow())
						gameOption.setShow(false);
					else{
						gameOption.setShow(true);
					}
				}
				else if(boBtnStart.checkPointIn(x, y)){
					//bat dau choi
					hieuUngChange();
//					Intent i = new Intent(getContext(), GamePlayActivity.class);
//					getContext().startActivity(i);
					//((Activity)getContext()).finish();
					((GamePanel)getView()).startPanelGame();
				}
				else if(boBtnSync.checkPointIn(x, y)){
					//dang nhap
					Intent i = new Intent(getView().getContext(), GamePlayActivity.class);
					getView().getContext().startActivity(i);
				}
				else kt = false;
				
				if(kt)
					Sound.mySound.playMusicButton();
				
				return true;
			}
		});
	
		
	}
	public void hieuUngChange(){
		float speedX = getWidth()/20;
		float speedY = getHeight()/20;
		int timewait = 1000/fps;
		for(int i = 0 ; i < 20 ; i ++){
			boBee.setLocationY(boBee.getLocationY() - speedY);
			boBtnStart.setLocationX(boBtnStart.getLocationX() + speedX);
			boBtnExit.setLocationX(boBtnExit.getLocationX() + speedX);
			boBtnHelp.setLocationX(boBtnHelp.getLocationX() - speedX);
			boBtnSetting.setLocationX(boBtnSetting.getLocationX() + speedX);
			boBtnSync.setLocationY(boBtnSync.getLocationY() - speedY);
			try {
				Thread.sleep(timewait);
			} catch (InterruptedException e) {}
		}
	}
	
	
}


