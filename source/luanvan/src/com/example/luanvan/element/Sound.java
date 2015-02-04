package com.example.luanvan.element;

import com.example.luanvan.R;
import com.example.luanvan.R.drawable;

import android.content.Context;
import android.media.MediaPlayer;

public class Sound {

	MediaPlayer mpGetHoney;
	Context context;
	MediaPlayer musicBackground;
	
	public Sound(Context ct) {
		// TODO Auto-generated constructor stub
		context = ct;
		mpGetHoney = MediaPlayer.create(context, R.drawable.button);
		musicBackground = MediaPlayer.create(context, R.drawable.sound_background);
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
		if(mpGetHoney == null) return;
		//if(mpGetHoney.isPlaying())mpGetHoney.stop();
		mpGetHoney.start();
	}
	
	public MediaPlayer getMusicBackground() {
		return musicBackground;
	}
	public void setMusicBackground(MediaPlayer musicBackground) {
		this.musicBackground = musicBackground;
	}
	public void playMusicBackground(boolean isLoop){
		if(musicBackground == null) return;
		if(musicBackground.isPlaying())musicBackground.stop();
		musicBackground.setLooping(isLoop);
		musicBackground.start();
	}
	public void stopMusicBackground(){
		if(musicBackground == null) return;
		if(musicBackground.isPlaying())musicBackground.stop();
	}
}
