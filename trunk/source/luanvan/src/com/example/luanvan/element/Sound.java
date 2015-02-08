package com.example.luanvan.element;

import java.io.IOException;

import com.example.luanvan.R;
import com.example.luanvan.R.drawable;

import android.content.Context;
import android.media.MediaPlayer;
import android.sax.StartElementListener;

public class Sound {
	
	public static Sound mySound;
	
	boolean volum;
	MediaPlayer mpButton;
	MediaPlayer mpGetHoney;
	Context context;
	MediaPlayer musicBackground;
	
	public Sound(Context ct) {
		// TODO Auto-generated constructor stub
		volum = true;
		context = ct;
		mpGetHoney = MediaPlayer.create(context, R.drawable.button);
		musicBackground = MediaPlayer.create(context, R.drawable.sound_background);
		mpButton = MediaPlayer.create(context, R.drawable.button);
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
	
	public MediaPlayer getMpButton() {
		return mpButton;
	}
	public void setMpButton(MediaPlayer mpButton) {
		this.mpButton = mpButton;
	}
	public void playGetHoney(){
		if(mpGetHoney == null || !volum) return;
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
		if( !volum) return;
			musicBackground.setLooping(isLoop);
			musicBackground.start();
		
	}
	public void pauseMusicBackground(){
		if(musicBackground == null) return;
		if(musicBackground.isPlaying())
			musicBackground.pause();
	}
	public void playMusic(MediaPlayer mp, boolean loop){
		if(mp == null || !volum) return;
		if(mp.isPlaying() ) mp.stop();
		mp.setLooping(loop);
		mp.start();
	}
	public void stopMusic(MediaPlayer mp){
		if(mp == null) return;
		if(mp.isPlaying()) 
			mp.stop();
	}
	
	public void playMusicButton(){
		if(!volum) return ;
		playMusic(mpButton, false);
	}
	
	public void turnOnVolum(){
		volum = true;
		playMusicBackground(true);
	}
	public void turnOffVolum(){
		volum = false;
		pauseMusicBackground();
		
	}
	public void destroy(){
		stopMusic(mpButton);
		stopMusic(mpGetHoney);
		stopMusic(musicBackground);
		mpButton = null;
		mpGetHoney = null;
		musicBackground = null;
		
	}
}
