package com.example.luanvan.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import com.example.luanvan.element.Flower.StateFlower;

import android.graphics.Point;




public class GameData {
	
	private enum StateStep {VISITTED,UNKNOW};
	public  enum FlagGame  {UNKNOWN , VICTORY, GAMEOVER};
	
	public static StateStep step[][];
	
	private FlagGame flagGame = FlagGame.UNKNOWN;
	private int cost = 0; // luu diem
	private int numRow = 0, numCol = 0; // sl hang, sl cot
	private float percentBad = 20; // ty le tao hu
	private Flower matrixFlower[][];
	private static int di[] = {-1,-1,-1,0,0,1,1,1};
	private static int dj[] = {-1,0,1,-1,1,-1,0,1};
	
	public GameData() {
		// TODO Auto-generated constructor stub
	}
	
	//****************get/set**********************8
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getNumRow() {
		return numRow;
	}

	public int getNumCol() {
		return numCol;
	}

	public float getPercentBad() {
		return percentBad;
	}

	public Flower[][] getMatrixFlower() {
		return matrixFlower;
	}

	public Flower getFlowerAt(int row, int col){
		if(row >=0 && row<numRow && col>=0 && col < numCol)
			return matrixFlower[row][col];
		else
			return null;
	}
	
	public int countFlower(){
		return  numCol * numRow ;
	}
	
	public int countFlowerHasHoney(){
		return countFlower() - countFlowerHasNotHoney();
	}
	
	public int countFlowerHasNotHoney(){
		return (int) ( (numCol * numRow)*percentBad/100f );
	}
	//*****************funtion*************************
	public void refesh(){
		cost = 0; //reset so diem
		flagGame = FlagGame.UNKNOWN;
		//reset state matrix
		for(int i = 0 ; i < numRow ; i ++){
			for(int j = 0 ; j < numCol ; j ++)
				matrixFlower[i][j].setState(StateFlower.CLOSE);
		}
	}
	
	public void createNew(int row, int col, int percent){
		flagGame = FlagGame.UNKNOWN;
		numCol = col;
		numRow = row;
		percentBad = percent;
		matrixFlower = new Flower[row][col];
		
		for(int i = 0 ; i < row ; i ++){
			for(int j = 0 ; j < col ; j ++)
				matrixFlower[i][j] = new Flower(0,StateFlower.CLOSE);
		}
		randomMatrix();
	}
	
	//random matrixFlower
	private void randomMatrix(){
		int slTao = countFlower();
		int slSau = countFlowerHasNotHoney();
		
		Vector lsVitri = new Vector();
		for(int i = 0 ; i < slTao ; i ++)
			lsVitri.add(i);
		
		int vtr;
		Random rd = new Random();
		for(int i = 0 ; i < slSau ; i ++){
			int index = rd.nextInt(lsVitri.size());
			vtr = (Integer) lsVitri.get( index );
			matrixFlower[vtr/numCol][vtr%numCol].setType(Flower.TypeFlower.BAD);
			lsVitri.removeElementAt(index);
		}
		for(int i = 0 ; i < lsVitri.size() ; i ++){
			vtr = (Integer) lsVitri.get(i);
			matrixFlower[vtr/numCol][vtr%numCol].setType(Flower.TypeFlower.GOOD);
		}
		// set matrix number
		int ii,jj;
		for(int i = 0 ; i < numRow ; i ++){
			for( int j = 0 ; j < numCol ; j ++){
				if(getFlowerAt(i, j).hasNotHoney()){
					getFlowerAt(i, j).setNumber(-1);
					for(int t = 0 ; t < 8 ; t ++){
						ii = i + di[t];
						jj = j + dj[t];
						if(kt(ii,jj) && getFlowerAt(ii, jj).hasHoney()){
							getFlowerAt(ii, jj).setNumber(getFlowerAt(ii, jj).getNumber() + 1);
						}
					}
				}
			}
		}
		
		
	}
	
	private boolean kt(int i,int j){
		return i>=0 && i<numRow && j >= 0 && j < numCol;
	}
	
	public boolean isVictory(){
		return flagGame.equals(FlagGame.VICTORY);
	}
	
	public boolean isGameOver(){
		return flagGame.equals(FlagGame.GAMEOVER);
	}
	
	public boolean isUnknow(){
		return flagGame.equals(FlagGame.UNKNOWN);
	}
	
	public ArrayList<Point> takeHoneyAt(int row, int col){
		if(getFlowerAt(row, col).isOpen()) return null;
		ArrayList<Point> lsPoint = new ArrayList<Point>();
		
		
		if(getFlowerAt(row, col).hasNotHoney()){
			flagGame = FlagGame.GAMEOVER;
			lsPoint.add(new Point(row,col));
			return lsPoint;
		}
		// reset step
		step = new StateStep[numRow][numCol];
		for(int i = 0 ; i < numRow ; i ++){
			for(int j = 0 ; j < numCol ; j ++){
				step[i][j] = StateStep.UNKNOW;
			}
		}
		DFS(row,col,lsPoint);
		step = null;
		//update scost
		int len =  lsPoint.size() ;
		Flower fl;
		for(int i = 0 ; i <len; i++){
			fl = getFlowerAt(lsPoint.get(i).y, lsPoint.get(i).x);
			if(fl.hasHoney())
				cost++;
		}
		//kt lai coi thang chua
		int cnt = 0;
		for(int i = 0 ; i < numRow ; i ++)
			for(int j = 0 ; j < numCol ; j ++)
				if(getFlowerAt(i, j).isOpen() && getFlowerAt(i, j).hasHoney())
					cnt++;
		if(cnt == countFlowerHasHoney() )
			flagGame = FlagGame.VICTORY;
		
				
		return lsPoint;
	}
	
	void DFS(int row, int col, ArrayList<Point> ls){
		if(getFlowerAt(row, col).hasNotHoney()) return;
		ls.add(new Point(col,row));
		step[row][col] = StateStep.VISITTED;
		matrixFlower[row][col].setState(StateFlower.OPEN);
		
		if(getFlowerAt(row, col).getNumber() >0 ) return;
		
		for(int i = 0 ; i < 8 ; i ++){
			int drow = row + di[i];
			int dcol = col + dj[i];
			if(drow >= 0 && drow < numRow && dcol >=0 && dcol < numCol && step[drow][dcol] == StateStep.UNKNOW)
				DFS(drow,dcol,ls);
		}
	}
}
