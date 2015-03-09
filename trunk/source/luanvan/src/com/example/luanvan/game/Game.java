package com.example.luanvan.game;

import java.util.ArrayList;

import com.example.luanvan.GamePanel;
import com.example.luanvan.GamePlayActivity;
import com.example.luanvan.element.BoundElement;
import com.example.luanvan.element.Flower;
import com.example.luanvan.element.GameAlertDialog;
import com.example.luanvan.element.GameData;
import com.example.luanvan.element.LocationElement;
import com.example.luanvan.element.MyView;
import com.example.luanvan.element.OptionConfig;
import com.example.luanvan.element.SizeElement;
import com.example.luanvan.element.Sound;
import com.example.luanvan.element.Theme;
import com.example.luanvan.element.ViewPanel;
import com.example.luanvan.element.Flower.StateFlower;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.PointF;
import android.hardware.Camera.Size;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;


public class Game extends MyView{

	int fps = 30; // so khung hinh tren giay
	BoundElement boMaxMT,boMtFlower,boCostBg,boHelp,boMenu;
	int slHang = 6, slCot = 8,tyleSau = 15;
	int time;
	float padding = 1;
	float sizeCell; 
	Thread thrDraw;
	ThreadTime thrTime;
	Theme theme;
	GameData gameData;
	public boolean running = true;
	private GameAlertDialog dialogMenu;
	
//	
//	public Game(Context context) {
//		super(context);
//		
//	}
	public Game(ViewPanel view){
		super(view);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub]
		
		
		slHang = OptionConfig.myConfig.getRow();
		slCot = OptionConfig.myConfig.getColunm();
		tyleSau = 100 - OptionConfig.myConfig.getPerHoney();
		
		
		gameData = new GameData();
		gameData.createNew(slHang, slCot, tyleSau);
		
//		theme = new Theme(1,this); ------------
		theme = new Theme(1,getView());
		setBound();
		setBoundFlower();
		dialogMenu = new PlaygameMenu(this);
		dialogMenu.onCreated();
		thrTime = new ThreadTime(this);
		thrTime.startTime();
		thrDraw = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int timeWait = 1000/fps;
				Canvas cv ;
				running = true;
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
		
		setEvent();
		
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		if(thrDraw != null && thrDraw.isAlive() ){
			running = false;
			//thrDraw.stop();
		}
		if(thrTime != null){
			thrTime.stopTime();
		}
	}
							
	@Override
	public void doDraw(Canvas c) {
		// TODO Auto-generated method stub
		
		if(theme == null || theme.getBmFlower() == null){
			//theme.setIdTheme(1);
			return;
		}
		
		DrawBackground(c);
		DrawMatrixFlower(c);
		DrawInfomation(c);
		DrawButton(c);
		if(dialogMenu.isShow())
			dialogMenu.doDraw(c);
	}
	
	// ve hinh nen
	public void DrawBackground(Canvas c){
		if(theme.getBmBackground().getWidth() != getWidth() || theme.getBmBackground().getHeight() != getHeight() ){
			theme.setBmBackground( Bitmap.createScaledBitmap(theme.getBmBackground(),getWidth(),getHeight(),true) );
		}
		Paint paint = new Paint();
		c.drawPaint(paint);
		paint.setColor(Color.GREEN);
		paint.setTextSize(2);
		c.drawBitmap(theme.getBmBackground(), 0, 0,paint);
	}
	
	public void DrawMatrixFlower(Canvas c){
		
		float sizeImg = sizeCell - 2*padding;
		Paint paint = new Paint();
		
		
		// ve nen cho flower
		drawBackgroundFlower(c, (sizeCell-sizeImg)/2);
		
		//set size image
		if(theme.getBmFlower().getWidth() != sizeImg ){
			theme.setBmFlower( Bitmap.createScaledBitmap(theme.getBmFlower(),(int)sizeImg,(int) sizeImg,true) );
			theme.setBmHoney( Bitmap.createScaledBitmap(theme.getBmHoney(),(int)sizeImg,(int) sizeImg,true) );
			theme.setBmNoHoney( Bitmap.createScaledBitmap(theme.getBmNoHoney(),(int)sizeImg,(int) sizeImg,true) );
			for(int i = 0 ; i < 10 ; i ++){
				Bitmap b = theme.getBmNumber(i);
				theme.setBmNumber(i, Bitmap.createScaledBitmap(theme.getBmNumber(i),(int)sizeImg,(int) sizeImg,true) );
			}
		}
		// draw flower
		Bitmap bm;
		Point po = new Point();
		for(int i = 0 ; i < slHang ; i++){
			for(int j = 0 ; j < slCot ; j++){
				
				po.set((int)(gameData.getFlowerAt(i, j).getBound().getLocationX() + padding)
						,(int)(gameData.getFlowerAt(i, j).getBound().getLocationY() + padding) );
			
				if(gameData.getFlowerAt(i, j).getState() == StateFlower.CLOSE)
					bm = theme.getBmFlower();
					
				else if(gameData.getFlowerAt(i, j).hasNotHoney())
					bm = theme.getBmNoHoney();
				else
					bm = theme.getBmNumber(gameData.getFlowerAt(i, j).getNumber());
					
				c.drawBitmap(bm, po.x,  po.y,paint);
			}
		}
		paint.setColor(Color.BLUE);
	}
	
	public void drawBackgroundFlower(Canvas c, float padding){
		PointF pdraw = new PointF();
		BoundElement bound;
		Paint pVien = new Paint();
		pVien.setStyle(Paint.Style.STROKE);
		pVien.setColor(Color.WHITE);
		pVien.setShadowLayer(3, 0, 0, Color.WHITE);
		pVien.setFilterBitmap(true);
		
		for(int i = 0 ; i < slHang ; i++){
			for(int j = 0 ; j < slCot ; j++){
				bound = gameData.getFlowerAt(i, j).getBound();
				
				c.drawRect(bound.getLocationX() + padding,bound.getLocationY() + padding
						,bound.getLocationX() + bound.getWidth() - padding
						,bound.getLocationY() + bound.getHeight() - padding, pVien);
			}
		}
		
	}
	
	public void setBoundFlower(){
		boMaxMT = new BoundElement();
		boMtFlower = new BoundElement();
		boMaxMT.getSize().setWidth( getWidth() - 20 );
		boMaxMT.getSize().setHeight( boHelp.getLocationY() - (boCostBg.getLocationY() + boCostBg.getHeight())- 20);
		boMaxMT.getLocation().set( (getWidth() - boMaxMT.getWidth() )/2
				, boCostBg.getLocationY() + boCostBg.getHeight() + 10);
		
		float hCell = boMaxMT.getHeight()/slHang;
		float wCell = boMaxMT.getWidth()/slCot;
		sizeCell = wCell < hCell ? wCell : hCell;
		boMtFlower.setSize(new SizeElement(slCot*sizeCell, slHang*sizeCell));
		boMtFlower.setLocation(new LocationElement( 
				(boMaxMT.getWidth()-boMtFlower.getWidth())/2 + boMaxMT.getLocationX()
				,(boMaxMT.getHeight() - boMtFlower.getHeight())/2 + boMaxMT.getLocationY()) );
		//set element of matrix
		BoundElement b = new BoundElement();
		float x,y;
		for(int i = 0 ; i < slHang ; i ++){
			for(int j = 0 ; j < slCot ; j ++){
				x = j*sizeCell + boMtFlower.getLocationX();
				y = i*sizeCell + boMtFlower.getLocationY();
				gameData.getFlowerAt(i, j).setBound(new BoundElement(x, y, sizeCell, sizeCell));
			}
		}
		
	}
	
	public void setBound(){
		Bitmap bm;
		float x,y,w,h;
		bm = theme.getBmBgCost();
		x = 0;
		y = 0.03f*getHeight();
		w = 0.4f*getWidth();
		h = bm.getHeight() * w/bm.getWidth();
		boCostBg = new BoundElement(x, y, w, h);
		theme.setBmBgCost(Bitmap.createScaledBitmap(bm,(int)w,(int)h,true));
		
		bm = theme.getBmButtonMenu();
		w = h = (66f/430f)*getHeight();
		x = getWidth() - w;
		y = getHeight() - h;
		boMenu = new BoundElement(x,y,w,h);
		theme.setBmButtonMenu(Bitmap.createScaledBitmap(bm,(int)w,(int)h,true));
		
		bm = theme.getBmButtonHelp();
		w = h = (66f/430f)*getHeight();
		x = 0;
		y = getHeight() - h;
		boHelp =  new BoundElement(x,y,w,h);
		theme.setBmButtonHelp(Bitmap.createScaledBitmap(bm,(int)w,(int)h,true));
	}
	public void DrawInfomation(Canvas c){
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.FILL);
		paint.setColor(Color.BLUE);
		paint.setTextSize(25);
		
		//ve diem
		LocationElement loCost = new LocationElement((boCostBg.getLocationX() + boCostBg.getWidth())/2 
				,boCostBg.getLocationY() + 0.75f*boCostBg.getHeight());
		
		c.drawBitmap(theme.getBmBgCost(), boCostBg.getLocationX(), boCostBg.getLocationY(), paint);
		c.drawText("" + gameData.getCost(), loCost.x, loCost.y, paint);
		//ve thoi gian
		LocationElement loTime = new LocationElement(loCost.x + getView().getWidth()/2,loCost.y );
		c.drawText(String.format("%02d:%02d",  thrTime.getTime()/60,thrTime.getTime()%60)
				, loTime.x, loTime.y, paint); ;
		
	}
	
	public void DrawButton(Canvas c){
		Paint paint = new Paint();
		c.drawBitmap(theme.getBmButtonHelp(), boHelp.getLocationX(), boHelp.getLocationY(), paint);
		c.drawBitmap(theme.getBmButtonMenu(), boMenu.getLocationX(), boMenu.getLocationY(), paint);
	}
	
	public void setEvent(){
		getView().setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(gameData.isGameOver()) return false;
				int me= event.getAction();
				switch(me){
					case MotionEvent.ACTION_DOWN:{
						PointF pClick = new PointF(event.getX(),event.getY());
						if(boMenu.checkPointIn(pClick.x, pClick.y)){
							dialogMenu.setShow(true);
							break;
						}
						Point local = getLocationAtCooridate(pClick.x, pClick.y);// lay vi tri bong hoa
						if(local != null){
							
							
							//Toast.makeText(getContext(), local.toString(), Toast.LENGTH_LONG).show();
							
							Flower flower = gameData.getFlowerAt(local.y, local.x); // lay bong hoa o vi tri local
							
							if(flower.isClose()){
								ArrayList<Point> lsFlower = gameData.takeHoneyAt(local.y, local.x);
								
								if(lsFlower != null && !lsFlower.isEmpty()){
									//xu li khi mo thanh cong
									Sound.mySound.playGetHoney();
									
									
								}else{
									//xu li khi thua
									
								}
								
								if(gameData.isVictory()) actionVictory();
								else if(gameData.isGameOver()) actionGameOver();
							}else{
								
							}
							
						}
						break;
					}
				}
				return true;
			}
		});
	}
	// ham lay vi tri cua bong hoa khi truyen vào toa do thuc
	public Point getLocationAtCooridate(float x, float y){
		for(int i = 0 ; i < slHang ; i ++)
			for(int j = 0 ; j < slCot ; j++){
				if(gameData.getFlowerAt(i, j).getBound().checkPointIn(x, y)){
					return new Point(j,i);
				}
			}
		
		return null;
	}
	
	public void rePlay(){
		running = false;
		
		surfaceCreated(getHolder());
	}
	
	public void actionVictory(){
		thrTime.stopTime();
		Toast.makeText(getContext(), "Victory!", Toast.LENGTH_LONG).show();
		((GamePanel)getView()).startPanelMainMenu();
	}

	public void actionGameOver(){
		//gameData.createNew(slHang, slCot, tyleSau);
		thrTime.stopTime();
		Toast.makeText(getView().getContext(), "GameOver!", Toast.LENGTH_LONG).show();
//		Intent i = new Intent(getContext(), GamePlayActivity.class);
//		getContext().startActivity(i);
		((GamePanel)getView()).startPanelMainMenu();
	}
	public void actionTimeOver(){
		thrTime.stopTime();
		((GamePanel)getView()).startPanelMainMenu();
	}
}

