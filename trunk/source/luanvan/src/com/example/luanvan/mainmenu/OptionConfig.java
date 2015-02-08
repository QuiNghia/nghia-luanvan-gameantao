package com.example.luanvan.mainmenu;

import com.example.luanvan.element.Sound;

public class OptionConfig {

	public static OptionConfig myConfig = new OptionConfig();
	
	public enum Level {EASY, MEDIUM, DIFFICULT, OPTION};
	
	private int row, colunm, perHoney;
	private boolean volum;
	private Level level;
	
	public OptionConfig() {
		setLevelOption(6, 6, 10);
		turnOnVolum();
	}
	
	public void setLevelEasy(){
		level = Level.EASY;
		row = colunm = 6;
		perHoney = 95;
	}
	public void setLevelMedium(){
		level = Level.MEDIUM;
		row = colunm = 6;
		perHoney = 90;
	}
	public void setLevelDifficult(){
		level = Level.DIFFICULT;
		row = colunm = 6;
		perHoney = 85;
	}
	public void setLevelOption(int row, int col, int per){
		level = Level.OPTION;
		this.row = row;
		this.colunm = col;
		this.perHoney = per;
	}
	public boolean isLevelEasy(){
		return level == Level.EASY;
	}
	public boolean isLevelMedium(){
		return level == Level.MEDIUM;
	}
	public boolean isLevelDifficult(){
		return level == Level.DIFFICULT;
	}
	public boolean isLevelOption(){
		return level == Level.OPTION;
	}
	public boolean isTurnOffVolum(){
		return volum == false;
	}
	
	public boolean isTurnOnVolum(){
		return volum;
	}
	
	public void turnOnVolum(){
		volum = true;
	}
	
	public void turnOffVolum(){
		volum = false;
	}
	
	public void changeVolum(){
		if(isTurnOffVolum())
			turnOnVolum();
		else 
			turnOffVolum();
	}
//-----------------------------------------------
	public int getRow() {
		return row;
	}

	public int getColunm() {
		return colunm;
	}

	public int getPerHoney() {
		return perHoney;
	}

	public Level getLevel() {
		return level;
	}

	@Override
	public String toString() {
		return "OptionConfig [row=" + row + ", colunm=" + colunm
				+ ", perHoney=" + perHoney + ", level=" + level + "]";
	}
	
	
	public void Import(OptionConfig cf){
		row = cf.getRow();
		colunm = cf.getColunm();
		perHoney = cf.getPerHoney();
		level = cf.getLevel();
		volum = cf.volum;
	}
	
	public void saveConfig(){
		if(isTurnOffVolum())
			Sound.mySound.turnOffVolum();
		else if(isTurnOnVolum()){
			Sound.mySound.turnOnVolum();
			Sound.mySound.playMusicBackground(true);
		}
			
		myConfig.Import(this);
		
	}
}
