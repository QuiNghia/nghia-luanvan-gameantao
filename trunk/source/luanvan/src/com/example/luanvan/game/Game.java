package com.example.luanvan.game;

import java.util.ArrayList;

import com.example.luanvan.GamePanel;
import com.example.luanvan.GamePlayActivity;
import com.example.luanvan.element.MyView;
import com.example.luanvan.element.ViewPanel;
import com.example.luanvan.game.Flower.StateFlower;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.hardware.Camera.Size;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;


public class Game extends MyView{

	int fps = 20; // so khung hinh tren giay
	float height = 300 , width = 300;
	int slHang = 5, slCot = 8,tyleSau = 5;
	float padding = 3;
	float sizeCell;
	PointF pointWrap; 
	Thread thrDraw;
	Theme theme;
	GameData gameData;
	Sound sound;
	public boolean running = true;
	

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
		// TODO Auto-generated method stub
		pointWrap = new PointF(10,50);
		
		gameData = new GameData();
		gameData.createNew(slHang, slCot, tyleSau);
		sound = new Sound(getContext());
		
//		theme = new Theme(1,this); ------------
		theme = new Theme(1,getView());
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
		addEvent();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		if(thrDraw != null && thrDraw.isAlive() ){
			running = false;
			//thrDraw.stop();
			
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
		
		float hCell = height/slHang;
		float wCell = width/slCot;
		sizeCell = wCell < hCell ? wCell : hCell;
		float h = slHang*sizeCell;
		float w = slCot*sizeCell;
		PointF po = new PointF();
		float sizeImg = sizeCell - 2*padding;
		Paint paint = new Paint();
		
		pointWrap.set(10 + (width-w)/2, 50 + (height-h)/2);
		
		// ve nen cho flower
		drawBackgroundFlower(c, pointWrap, sizeCell, (sizeCell-sizeImg)/2);
		
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
		for(int i = 0 ; i < slHang ; i++){
			for(int j = 0 ; j < slCot ; j++){
				
				po.set(j*sizeCell + pointWrap.x + padding, i*sizeCell + pointWrap.y + padding);
			
				if(gameData.getFlowerAt(i, j).getState() == StateFlower.CLOSE)
					bm = theme.getBmFlower();
					
				else if(gameData.getFlowerAt(i, j).hasNotHoney())
					bm = theme.getBmNoHoney();
				else
					bm = theme.getBmNumber(gameData.getFlowerAt(i, j).getNumber());
					
				c.drawBitmap(bm, po.x,  po.y,paint);
			}
		}
		
	}
	
	public void drawBackgroundFlower(Canvas c,PointF p, float sizeCell, float padding){
		PointF po = new PointF();
		float sizeImg = sizeCell - 2*padding;
		Paint pVien = new Paint();
		pVien.setStyle(Paint.Style.STROKE);
		pVien.setColor(Color.WHITE);
		pVien.setShadowLayer(3, 0, 0, Color.WHITE);
		pVien.setFilterBitmap(true);
		
		for(int i = 0 ; i < slHang ; i++){
			for(int j = 0 ; j < slCot ; j++){
				po.set(j*sizeCell + p.x + padding, i*sizeCell + p.y + padding);
				c.drawRect(po.x,po.y,po.x+sizeImg,po.y+sizeImg, pVien);
			}
		}
		
	}
	
	public void addEvent(){
		getView().setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(gameData.isGameOver()) return false;
				int me= event.getAction();
				switch(me){
					case MotionEvent.ACTION_DOWN:{
						PointF pClick = new PointF(event.getX(),event.getY());
						if(pClick.x >= pointWrap.x && pClick.x <= (pointWrap.x+width) 
								&& pClick.y >= pointWrap.y && pClick.y <= (pointWrap.y+height)){
							
							Point local = getLocationAtCooridate(pClick.x, pClick.y);// lay vi tri bong hoa
							Flower flower = gameData.getFlowerAt(local.y, local.x); // lay bong hoa o vi tri local
							if(flower.isClose()){
								ArrayList<Point> lsFlower = gameData.takeHoneyAt(local.y, local.x);
								
								if(lsFlower != null && !lsFlower.isEmpty()){
									//xu li khi mo thanh cong
									sound.playGetHoney();
									
									
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
		x -= pointWrap.x;
		y -= pointWrap.y;
		Point p = new Point();
		if(x < 0 || y < 0 || x > slCot*sizeCell || y > slHang*sizeCell)
			return null;
		p.set( (int)(x/sizeCell) , (int)(y/sizeCell) );
		
		return p;
	}
	
	public void actionVictory(){
		Toast.makeText(getContext(), "Victory!", Toast.LENGTH_LONG).show();
		((GamePanel)getView()).startPanelMainMenu();
	}

	public void actionGameOver(){
		//gameData.createNew(slHang, slCot, tyleSau);
		Toast.makeText(getContext(), "GameOver!", Toast.LENGTH_LONG).show();
//		Intent i = new Intent(getContext(), GamePlayActivity.class);
//		getContext().startActivity(i);
		((GamePanel)getView()).startPanelMainMenu();
	}
}
