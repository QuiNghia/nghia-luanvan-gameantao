package com.example.luanvan.mainmenu;

import com.example.luanvan.GamePanel;
import com.example.luanvan.GamePlayActivity;
import com.example.luanvan.MainActivity;
import com.example.luanvan.R;
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

	MainMenu myMainMenu;
	BoundElement boBtnStart, boBtnExit, boBtnSetting, boBtnHelp, boBtnSync, boBee;
	Thread thrDraw;
	Bitmap bmBackground, bmBtnStart,bmBtnExit, bmBtnSetting, bmBtnHelp, bmBtnSync;
	Bitmap bmBee[];
	GameOption gameOption;
	int fps = 20, timeDelay; 
	float width, height;
	boolean running;
	int cntBee = 0;
	
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
		setBoundElement();
		loadBitmap();
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
		boBtnStart = new BoundElement(getWidth() - 192, getHeight()/2-63-10, 192, 63);
		boBtnExit = new BoundElement(getWidth() - 192, getHeight()/2 + 10, 192, 63);
		boBtnSetting = new BoundElement(getWidth() - 66, getHeight()-66, 66, 66);
		boBtnHelp = new BoundElement(0, getHeight()-66, 66, 66);
		boBtnSync = new BoundElement(getWidth() - 66, 0, 66, 66);
		float x = boBtnStart.getLocationX()+ boBtnStart.getWidth()/2 - 0.25f*188;
		float y = boBtnStart.getLocationY() - 0.5f*175;
		boBee = new BoundElement(x, y, 188*0.5f,175*0.5f);
	}
	
	public  void loadBitmap(){
		
		bmBackground = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_background) 
				,getWidth(),getHeight(),true);
		bmBtnExit    = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_button_thoat)
				,(int)boBtnExit.getWidth(),(int)boBtnExit.getHeight(),true);
		bmBtnHelp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_button_help)
				,(int)boBtnHelp.getWidth(),(int)boBtnHelp.getHeight(), true);
		bmBtnSetting = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_button_setting)
				,(int) boBtnSetting.getWidth(), (int)boBtnSetting.getHeight(), true);
		bmBtnStart = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_button_bat_dau)
				,(int) boBtnStart.getWidth(), (int)boBtnStart.getHeight(), true);
		bmBtnSync = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_button_syns)
				,(int) boBtnSync.getWidth(), (int) boBtnSync.getHeight(), true);
		bmBee = new Bitmap[12];
		
		bmBee[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee1)
				,(int) boBee.getWidth(), (int) boBee.getHeight(), true);
		bmBee[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee2)
				,(int) boBee.getWidth(), (int) boBee.getHeight(), true);
		bmBee[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee3)
				,(int) boBee.getWidth(), (int) boBee.getHeight(), true);
		bmBee[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee4)
				,(int) boBee.getWidth(), (int) boBee.getHeight(), true);
		bmBee[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee5)
				,(int) boBee.getWidth(), (int) boBee.getHeight(), true);
		bmBee[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee6)
				,(int) boBee.getWidth(), (int) boBee.getHeight(), true);
		bmBee[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee7)
				,(int) boBee.getWidth(), (int) boBee.getHeight(), true);
		bmBee[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee8)
				,(int) boBee.getWidth(), (int) boBee.getHeight(), true);
		bmBee[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee9)
				,(int) boBee.getWidth(), (int) boBee.getHeight(), true);
		bmBee[9] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee10)
				,(int) boBee.getWidth(), (int) boBee.getHeight(), true);
		bmBee[10] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee11)
				,(int) boBee.getWidth(), (int) boBee.getHeight(), true);
		bmBee[11] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.main_menu_chibi_bee12)
				,(int) boBee.getWidth(), (int) boBee.getHeight(), true);
		
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


