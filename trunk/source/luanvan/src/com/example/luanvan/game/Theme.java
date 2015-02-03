package com.example.luanvan.game;

import com.example.luanvan.R;
import com.example.luanvan.R.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.view.View;

public class Theme {
	
	private int idTheme = 1;
	private Bitmap bmFlower;
	private Bitmap bmHoney;
	private Bitmap bmNoHoney;
	private Bitmap bmBackground;
	private Bitmap bmNumber[];
	View view;
	
	public Theme() {
		
	}
	
	public Theme(int id, View v){
		view = v;
		setIdTheme(id);
	}

	public int getIdTheme() {
		return idTheme;
	}

	public void setIdTheme(int idTheme) {
		
		this.idTheme = idTheme;
		
		
		switch(idTheme){
		case 1:{
			
			bmFlower = BitmapFactory.decodeResource(view.getResources(), R.drawable.flower1);
			bmHoney = BitmapFactory.decodeResource(view.getResources(), R.drawable.honey1);
			bmNoHoney = BitmapFactory.decodeResource(view.getResources(), R.drawable.nohoney1);
			bmBackground = BitmapFactory.decodeResource(view.getContext().getResources(), R.drawable.background1);
			bmNumber = new Bitmap[12];
			bmNumber[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.num0);
			bmNumber[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.num1);
			bmNumber[2] = BitmapFactory.decodeResource(view.getResources(), R.drawable.num2);
			bmNumber[3] = BitmapFactory.decodeResource(view.getResources(), R.drawable.num3);
			bmNumber[4] = BitmapFactory.decodeResource(view.getResources(), R.drawable.num4);
			bmNumber[5] = BitmapFactory.decodeResource(view.getResources(), R.drawable.num5);
			bmNumber[6] = BitmapFactory.decodeResource(view.getResources(), R.drawable.num6);
			bmNumber[7] = BitmapFactory.decodeResource(view.getResources(), R.drawable.num7);
			bmNumber[8] = BitmapFactory.decodeResource(view.getResources(), R.drawable.num8);
			bmNumber[9] = BitmapFactory.decodeResource(view.getResources(), R.drawable.num9);
			
			
			break;
		}
		default:{
			break;
		}
		}
		
	}

	public Bitmap getBmFlower() {
		return bmFlower;
	}

	public void setBmFlower(Bitmap bmAppleGood) {
		this.bmFlower = bmAppleGood;
	}

	public Bitmap getBmHoney() {
		return bmHoney;
	}

	public void setBmHoney(Bitmap bmAppleBad) {
		this.bmHoney = bmAppleBad;
	}

	public Bitmap[] getBmNumber() {
		return bmNumber;
	}

	public void setBmNumber(Bitmap[] bmNumber) {
		this.bmNumber = bmNumber;
	}
	
	public Bitmap getBmNumber(int number){
		if(number<bmNumber.length)
			return bmNumber[number];
		
		return null;
	}
	
	public void setBmNumber(int number, Bitmap bm){
		if(number<bmNumber.length)
			bmNumber[number] = bm;
	}

	public Bitmap getBmBackground() {
		return bmBackground;
	}

	public void setBmBackground(Bitmap bmBackground) {
		this.bmBackground = bmBackground;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Bitmap getBmNoHoney() {
		return bmNoHoney;
	}

	public void setBmNoHoney(Bitmap bmNoHoney) {
		this.bmNoHoney = bmNoHoney;
	}


}
