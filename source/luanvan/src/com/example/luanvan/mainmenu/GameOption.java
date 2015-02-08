package com.example.luanvan.mainmenu;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.example.luanvan.R;
import com.example.luanvan.element.BoundElement;
import com.example.luanvan.element.GameAlertDialog;
import com.example.luanvan.element.MyView;
import com.example.luanvan.element.OptionConfig;
import com.example.luanvan.element.Sound;
import com.example.luanvan.element.ViewPanel;

public class GameOption extends GameAlertDialog{
	
	OptionConfig config;
	Bitmap bmBgColor,bmBg,bmBtnSave,bmBtnBack,bmRadioOn, bmRadioOff, bmSlider,bmSliderScroll;
	BoundElement boRoot, boBg,boBtnSave, boBtnBack, boRadioKho,boRadioTB,boRadioDe,boRadioTC
				,boSliderRow,boSliderCol,boSliderHoney, boRadioSound
				, boSliderScRow,boSliderScCol,boSliderScHoney ;
	
	int preChoose = -1;
	public GameOption(MyView view) {
		super(view);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doDraw(Canvas c) {
		// TODO Auto-generated method stub
		drawBackground(c);
		drawButton(c);
		drawLabel(c);
	}

	@Override
	public void onCreated() {
		// TODO Auto-generated method stub
		config = new OptionConfig();
		config.Import(OptionConfig.myConfig);
		loadBitmapAndSetBound();
		resizeBitmap();
		updateBoundOption();
		//setEvent();
	}

	@Override
	public void onDestroyed() {
		// TODO Auto-generated method stub
		
	}
	
	public void drawBackground(Canvas c){
		Paint p = new Paint();
		c.drawBitmap(bmBgColor, boRoot.getLocationX(), boRoot.getLocationY(), p);
		c.drawBitmap(bmBg, boBg.getLocationX(), boBg.getLocationY(), p);
	}
	
	public void drawButton(Canvas c){
		Paint p = new Paint();
		Bitmap bm;
		c.drawBitmap(bmBtnBack, boBtnBack.getLocationX(), boBtnBack.getLocationY(), p);
		c.drawBitmap(bmBtnSave, boBtnSave.getLocationX(), boBtnSave.getLocationY(), p);
		//ve button am thanh
		if(config.isTurnOnVolum()) bm = bmRadioOn;
		else bm = bmRadioOff;
		c.drawBitmap(bm, boRadioSound.getLocationX(), boRadioSound.getLocationY(), p);
		//ve button kho
		if(config.isLevelDifficult()) bm = bmRadioOn;
		else bm = bmRadioOff;
		c.drawBitmap(bm, boRadioKho.getLocationX(), boRadioKho.getLocationY(), p);
		//ve button de
		if(config.isLevelEasy()) bm = bmRadioOn;
		else bm = bmRadioOff;
		c.drawBitmap(bm, boRadioDe.getLocationX(), boRadioDe.getLocationY(), p);
		//ve button trung binh
		if(config.isLevelMedium()) bm = bmRadioOn;
		else bm = bmRadioOff;
		c.drawBitmap(bm, boRadioTB.getLocationX(), boRadioTB.getLocationY(), p);
		//ve button trung binh
		if(config.isLevelOption())
			bm = bmRadioOn;
		else bm = bmRadioOff;
		c.drawBitmap(bm, boRadioTC.getLocationX(), boRadioTC.getLocationY(), p);
		//ve button tuy chon
		if(config.isLevelOption()){
			c.drawBitmap(bmSlider, boSliderRow.getLocationX(), boSliderRow.getLocationY(),p);
			c.drawBitmap(bmSlider, boSliderCol.getLocationX(), boSliderCol.getLocationY(),p);
			c.drawBitmap(bmSlider, boSliderHoney.getLocationX(), boSliderHoney.getLocationY(),p);
			//ve nut cuon
			
			c.drawBitmap(bmSliderScroll, boSliderScRow.getLocationX(), boSliderScRow.getLocationY(),p);
			c.drawBitmap(bmSliderScroll, boSliderScCol.getLocationX(), boSliderScCol.getLocationY(),p);
			c.drawBitmap(bmSliderScroll, boSliderScHoney.getLocationX(), boSliderScHoney.getLocationY(),p);
		}
		
	}
	public void drawLabel(Canvas c){
		float fontSize = boRadioDe.getHeight();//14;
		Typeface tf = Typeface.create("Helvetica",Typeface.BOLD);
		int clStrong = Color.rgb(29, 108, 30); 
		int clNomal = Color.rgb(102, 102, 102);
		Paint paint = new Paint();
		paint.setColor(Color.rgb(102, 102, 102));
		paint.setTypeface(tf);
		paint.setTextSize(14);
		float x,y;
		String text;
		
		text = "Khó";
		x = boRadioKho.getLocationX()+ boRadioKho.getWidth() + 10;
		y = boRadioKho.getLocationY() + fontSize;
		if(config.isLevelDifficult()) paint.setColor(clStrong);
		else paint.setColor(clNomal);
		c.drawText(text, x, y, paint);
		
		text = "Trung bình";
		y = boRadioTB.getLocationY() + fontSize;
		if(config.isLevelMedium()) paint.setColor(clStrong);
		else paint.setColor(clNomal);
		c.drawText(text, x, y, paint);
		
		text = "Dễ";
		y = boRadioDe.getLocationY() + fontSize;
		if(config.isLevelEasy()) paint.setColor(clStrong);
		else paint.setColor(clNomal);
		c.drawText(text, x, y, paint);
		
		text = "Tùy chọn";
		y = boRadioTC.getLocationY() + fontSize;
		if(config.isLevelOption()) paint.setColor(clStrong);
		else paint.setColor(clNomal);
		c.drawText(text, x, y, paint);
		
		text = "Hàng : " ;
		x = boBg.getLocationX() + boBg.getWidth()/6;
		y = boSliderRow.getLocationY() + fontSize;
		c.drawText(text, x, y, paint);
		x = boBg.getWidth()/2 - 3*fontSize/2;
		c.drawText(""  + config.getRow(), x, y, paint);
		
		
		text = "Cột :" ;
		x = boBg.getLocationX() + boBg.getWidth()/6;
		y = boSliderCol.getLocationY() +fontSize;
		c.drawText(text, x, y, paint);
		x = boBg.getWidth()/2 - 3*fontSize/2;
		c.drawText(""  + config.getColunm(), x, y, paint);
		
		text = "Tỷ lệ mật :" ;
		x = boBg.getLocationX() + boBg.getWidth()/6;
		y = boSliderHoney.getLocationY() + fontSize;
		c.drawText(text, x, y, paint);
		x = boBg.getWidth()/2 - 3*fontSize/2;
		c.drawText( config.getPerHoney() +"%", x, y, paint);
		
		text = "Âm thanh";
		x = boRadioSound.getLocationX()+ boRadioSound.getWidth() + 10;
		y = boRadioSound.getLocationY() + fontSize;
		if(config.isTurnOnVolum()) paint.setColor(clStrong);
		else paint.setColor(clNomal);
		c.drawText(text, x, y, paint);
		
		
	}
	
	public void setEvent(){
		view.getView().setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX();
				float y = event.getY();
				
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					preChoose = -1;
					
					boolean kt = true;
					if(boBtnBack.checkPointIn(x, y)){
						//tat setting
						setShow(false);
						((MainMenu)view).setEvent();
					}
					else if(boBtnSave.checkPointIn(x, y)){
						config.saveConfig();
						setShow(false);
						view.setEvent();
					}
					else if(boRadioDe.checkPointIn(x, y)){
						config.setLevelEasy();
					}
					else if(boRadioKho.checkPointIn(x, y))
						config.setLevelDifficult();
					else if(boRadioTB.checkPointIn(x, y)){
						config.setLevelMedium();
					}
					else if(boRadioTC.checkPointIn(x, y)){
						config.setLevelOption(config.getRow(), config.getColunm(), config.getPerHoney());
					}
					else if(boRadioSound.checkPointIn(x, y)){
						config.changeVolum();
					}
					else if(boSliderRow.checkPointIn(x, y) && config.isLevelOption()){
						preChoose = 1;
						changeOption(x, y, 1);
						kt = false;
					}
						
					else if(boSliderCol.checkPointIn(x, y) && config.isLevelOption()){
						preChoose = 2;
						changeOption(x, y, 2);
						kt = false;
					}
					else if(boSliderHoney.checkPointIn(x, y) && config.isLevelOption()){
						preChoose = 3;
						changeOption(x, y, 3);
						kt = false;
					}
					else
						kt = false;
					
					if(kt)
						Sound.mySound.playMusicButton();
					return true;
					
				}else if(event.getAction() == MotionEvent.ACTION_MOVE){
					if(preChoose != -1)
						changeOption(x, y, preChoose);
					//updateBoundOption();
				}
				else {
					if(preChoose != -1){
						Sound.mySound.playMusicButton();
						updateBoundOption();
						preChoose = -1;
					}
					
				}
				return true;
			}
		});
	}
	// thay doi cau hinh tuy chon
	public void changeOption(float x, float y, int choose){
		int dNum;
		if(choose == 1){
			dNum = (int)( (x - boSliderRow.getLocationX())/(boSliderRow.getWidth()-boSliderScRow.getWidth()) *(float) OptionConfig.deltaRow() ) 
					+ OptionConfig.MIN_ROW;
			if(dNum > OptionConfig.MAX_ROW) dNum = OptionConfig.MAX_ROW;
			if(dNum < OptionConfig.MIN_ROW) dNum = OptionConfig.MIN_ROW;
			//if(dNum != config.getRow()) Sound.mySound.playMusicButton();
			config.setLevelOption(dNum, config.getColunm(), config.getPerHoney());
			if(x >= boSliderRow.getLocationX() 
					&& x <= boSliderRow.getLocationX() + boSliderRow.getWidth()-boSliderScRow.getWidth())
			boSliderScRow.setLocationX(x);
		}
		else if(choose == 2){
			dNum = (int)( (x - boSliderCol.getLocationX())/(boSliderCol.getWidth()-boSliderScCol.getWidth()) *(float) OptionConfig.deltaColunm() ) 
					+ OptionConfig.MIN_COLUNM;
			if(dNum > OptionConfig.MAX_COLUNM) dNum = OptionConfig.MAX_COLUNM;
			if(dNum < OptionConfig.MIN_COLUNM) dNum = OptionConfig.MIN_COLUNM;
			//if(dNum != config.getColunm()) Sound.mySound.playMusicButton();
			config.setLevelOption(config.getRow(), dNum, config.getPerHoney());
			if(x >= boSliderCol.getLocationX() 
					&& x <= boSliderCol.getLocationX() + boSliderCol.getWidth() - boSliderScCol.getWidth())
				boSliderScCol.setLocationX(x);
		}
		else if(choose == 3){
			dNum = (int)( (x - boSliderHoney.getLocationX())/(boSliderHoney.getWidth()-boSliderScHoney.getWidth()) *(float) OptionConfig.deltaPerHoney() ) 
					+ OptionConfig.MIN_PER_HONEY;
			if(dNum > OptionConfig.MAX_PER_HONEY) dNum = OptionConfig.MAX_PER_HONEY;
			if(dNum < OptionConfig.MIN_PER_HONEY) dNum = OptionConfig.MIN_PER_HONEY;
			//if(dNum != config.getPerHoney()) Sound.mySound.playMusicButton();
			config.setLevelOption(config.getRow(),config.getColunm(), dNum);
			if(x >= boSliderHoney.getLocationX() 
					&& x <= boSliderHoney.getLocationX() + boSliderHoney.getWidth() - boSliderScHoney.getWidth())
				boSliderScHoney.setLocationX(x);
		}
	}
	
	public void updateBoundOption(){
		float x;
		x = boSliderRow.getLocationX() + (boSliderRow.getWidth()-boSliderScRow.getWidth())*(config.getRow() - OptionConfig.MIN_ROW)/OptionConfig.deltaRow();
		boSliderScRow.setLocationX(x);
		
		x = boSliderCol.getLocationX() + (boSliderCol.getWidth()-boSliderScCol.getWidth())*(config.getColunm() - OptionConfig.MIN_COLUNM)/OptionConfig.deltaColunm();
		boSliderScCol.setLocationX(x);
		
		x = boSliderHoney.getLocationX() + (boSliderHoney.getWidth()-boSliderScHoney.getWidth())*(config.getPerHoney() - OptionConfig.MIN_PER_HONEY)/OptionConfig.deltaPerHoney();
		boSliderScHoney.setLocationX(x);
		
	}
	
	public void loadBitmapAndSetBound(){
		bmBgColor = BitmapFactory.decodeResource(view.getResources(), R.drawable.setting_background_color);
		bmBg = BitmapFactory.decodeResource(view.getResources(), R.drawable.setting_background);
		bmBtnBack = BitmapFactory.decodeResource(view.getResources(), R.drawable.setting_button_back);
		bmBtnSave = BitmapFactory.decodeResource(view.getResources(), R.drawable.setting_button_save);
		bmRadioOff = BitmapFactory.decodeResource(view.getResources(), R.drawable.setting_radio_off);
		bmRadioOn  = BitmapFactory.decodeResource(view.getResources(), R.drawable.setting_radio_on);
		bmSlider = BitmapFactory.decodeResource(view.getResources(), R.drawable.setting_slider);
		bmSliderScroll = BitmapFactory.decodeResource(view.getResources(), R.drawable.setting_slider_scroll);
		
		float x,y,w,h;
		boRoot = new BoundElement(0, 0, view.getWidth(), view.getHeight());
		boBg = new BoundElement(0, 0, boRoot.getWidth(), boRoot.getHeight());
		
		
		w = h = Math.min(boRoot.getWidth()*0.069f, boRoot.getHeight()*0.046f );
		x = boRoot.getWidth() * 0.35f;
		y = boRoot.getHeight() * 0.2f;
		boRadioKho = new BoundElement(x, y, w, h);
		
		y = boRadioKho.getLocationY() + boRadioKho.getHeight() + 5;
		
		boRadioTB = new BoundElement(x, y, w, h);
		
		y = boRadioTB.getLocationY() + boRadioTB.getHeight() + 5;
		boRadioDe = new BoundElement(x, y, w, h);
		
		y = boRadioDe.getLocationY() + boRadioDe.getHeight() + 5;
		boRadioTC = new BoundElement(x, y, w, h);
		
		x = boRoot.getWidth()/2f;
		y = boRadioTC.getLocationY() + boRadioTC.getHeight() + 5;
		w = boRoot.getWidth() * 0.3125f;
		h = (float)bmSlider.getHeight() * w/bmSlider.getWidth();
		boSliderRow = new BoundElement(x, y, w, h);
		
		y = boSliderRow.getLocationY() + boSliderRow.getHeight() + 5 ;
		boSliderCol = new BoundElement(x, y, w, h);
		
		y = boSliderCol.getLocationY() + boSliderCol.getHeight() + 5;
		boSliderHoney = new BoundElement(x, y, w, h);
		
		x = boRadioTC.getLocationX();
		y = boSliderHoney.getLocationY() + boSliderHoney.getHeight() + 10;
		w = h = boRadioTC.getWidth();
		boRadioSound = new BoundElement(x, y, w, h);
		
		w = boRoot.getWidth()*0.25f;
		h = bmBtnBack.getHeight() * (w/bmBtnBack.getWidth());
		x = boRoot.getWidth()/2 - w - 10;
		y = boRadioSound.getLocationY() + boRadioSound.getHeight() + 10;
		boBtnBack = new BoundElement(x, y, w, h);
		
		x = boRoot.getWidth()/2f + 10;
		y = boBtnBack.getLocationY();
		w = boBtnBack.getWidth();
		h = boBtnBack.getHeight();
		boBtnSave = new BoundElement(x, y, w, h);
		
		int co = config.getColunm();
		int ro = config.getRow();
		int ho = config.getPerHoney();
		
		h = boSliderRow.getHeight();
		w = bmSliderScroll.getWidth() * h/bmSliderScroll.getHeight();
		y = boSliderRow.getLocationY();
		x = boSliderRow.getLocationX() + 
				boSliderRow.getWidth()*config.getRow()/OptionConfig.MAX_ROW - OptionConfig.MIN_ROW;
		boSliderScRow = new BoundElement(x, y, w, h);
		
		y = boSliderCol.getLocationY();
		x = boSliderCol.getLocationX() + 
				boSliderCol.getWidth()*config.getColunm()/OptionConfig.MAX_COLUNM - OptionConfig.MIN_COLUNM;
		boSliderScCol = new BoundElement(x, y, w, h);
		
		y = boSliderHoney.getLocationY();
		x = boSliderHoney.getLocationX() + 
				boSliderHoney.getWidth()*config.getPerHoney()/OptionConfig.MAX_PER_HONEY - OptionConfig.MIN_PER_HONEY;
		boSliderScHoney = new BoundElement(x, y, w, h);
	}
	
	public void resizeBitmap(){
		bmBg = Bitmap.createScaledBitmap(bmBg, (int)boBg.getWidth(), (int)boBg.getHeight(),true);
		bmBgColor = Bitmap.createScaledBitmap(bmBgColor, (int)boRoot.getWidth(), (int)boRoot.getHeight(),true);
		bmBtnSave = Bitmap.createScaledBitmap(bmBtnSave, (int)boBtnSave.getWidth(), (int)boBtnSave.getHeight(),true);
		bmBtnBack = Bitmap.createScaledBitmap(bmBtnBack, (int)boBtnBack.getWidth(), (int)boBtnBack.getHeight(),true);
		bmRadioOn = Bitmap.createScaledBitmap(bmRadioOn, (int)boRadioKho.getWidth(), (int)boRadioKho.getHeight(),true);
		bmRadioOff = Bitmap.createScaledBitmap(bmRadioOff, (int)boRadioKho.getWidth(), (int)boRadioKho.getHeight(),true);
		bmSlider = Bitmap.createScaledBitmap(bmSlider, (int)boSliderRow.getWidth(), (int)boSliderRow.getHeight(),true);
		bmSliderScroll = Bitmap.createScaledBitmap(bmSliderScroll, (int)boSliderScRow.getWidth(), (int)boSliderScRow.getHeight(),true);;
	}
	@Override
	public void setShow(boolean show) {
		super.setShow(show);
		if(show){
			config.Import(OptionConfig.myConfig);
			setEvent();
		}
	}
}
