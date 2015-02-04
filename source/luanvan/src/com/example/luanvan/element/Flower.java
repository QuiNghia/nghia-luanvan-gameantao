package com.example.luanvan.element;


public class Flower {
	
	static public enum StateFlower {CLOSE, OPEN};
	static public enum TypeFlower {GOOD, BAD};
	
	private StateFlower state;
	private int number;
	private TypeFlower type;
	private BoundElement bound = new BoundElement();
	
	public Flower() {
		state = StateFlower.CLOSE;
		number = 0;
		type = TypeFlower.GOOD;
	}
	
	public Flower(int num, StateFlower st) {
		state = st;
		number = num;
	}
	
	public Flower(int num) {
		state = StateFlower.CLOSE;
		number = num;
	}

	public StateFlower getState() {
		return state;
	}

	public void setState(StateFlower state) {
		this.state = state;
	}

	//get number
	public int getNumber() {
		return number;
	}

	//set number
	public void setNumber(int number) {
		this.number = number;
	}

	public TypeFlower getType() {
		return type;
	}

	public void setType(TypeFlower type) {
		this.type = type;
	}
	
	public boolean hasHoney(){
		return type.equals(TypeFlower.GOOD);
	}
	
	public boolean hasNotHoney(){
		return type.equals(TypeFlower.BAD);
	}
	public boolean isClose(){
		return state.equals(StateFlower.CLOSE);
	}
	public boolean isOpen(){
		return state.equals(StateFlower.OPEN);
	}

	public BoundElement getBound() {
		return bound;
	}

	public void setBound(BoundElement bound) {
		this.bound = bound;
	}
	
}