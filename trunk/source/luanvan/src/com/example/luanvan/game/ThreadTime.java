package com.example.luanvan.game;

public class ThreadTime {

	private boolean running;
	private int maxTime = 5*60,time;
	private Game game;
	private Thread thread;
	
	public ThreadTime(Game game) {
		this.game = game;
		running = true;
		time = maxTime;
	}
	
	public void runTime() {
		
		while(running){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
				time -= 1;
				if(time <= 0){
					time = 0;
					stopTime();
					if( game.gameData.isUnknow() )
						game.actionTimeOver();
					break;
				}
		}
	}
	
	public void startTime() {
		running = true;
		time = maxTime;
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				runTime();
			}
		});
		thread.start();
	}
	
	public void stopTime(){
		running = false;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	
}
