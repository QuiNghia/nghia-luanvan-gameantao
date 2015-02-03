package com.example.luanvan.game;

import com.example.luanvan.R;
import com.example.luanvan.R.drawable;

import android.content.Context;
import android.media.MediaPlayer;

public class Sound {

	MediaPlayer mpGetHoney;
	Context context;
	public Sound(Context ct) {
		// TODO Auto-generated constructor stub
		context = ct;
		mpGetHoney = MediaPlayer.create(context, R.drawable.button);
	}
	public MediaPlayer getMpGetHoney() {
		return mpGetHoney;
	}
	public void setMpGetHoney(MediaPlayer mpGetHoney) {
		this.mpGetHoney = mpGetHoney;
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	
	public void playGetHoney(){
		if(mpGetHoney != null)
			mpGetHoney.start();
	}
}
