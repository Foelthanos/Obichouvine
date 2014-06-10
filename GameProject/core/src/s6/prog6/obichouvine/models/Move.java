package s6.prog6.obichouvine.models;

import s6.prog6.obichouvine.models.Pawn.PawnType;

public class Move {
	int xDep;
	int yDep;
	int xArr;
	int yArr;
	PawnType turn;
	int turnNum;
	
	boolean eaten = false;
	int xEaten, yEaten;
	public Move(int x, int y, int x1, int y1)
	{
		xDep = x;
		yDep = y;
		xArr = x1;
		yArr = y1;
	}
	
	public PawnType getTurn() {
		return turn;
	}

	public void setTurn(PawnType turn) {
		this.turn = turn;
	}

	public int getTurnNum() {
		return turnNum;
	}

	public void setTurnNum(int turnNum) {
		this.turnNum = turnNum;
	}

	public Move(int x, int y, int x1, int y1, PawnType turn, int turnNum){
		this(x,y,x1,y1);
		this.turn = turn;
		this.turnNum = turnNum;
	}
	public int getxDep() {
		return xDep;
	}
	public void setxDep(int xDep) {
		this.xDep = xDep;
	}
	public int getyDep() {
		return yDep;
	}
	public void setyDep(int yDep) {
		this.yDep = yDep;
	}
	public int getxArr() {
		return xArr;
	}
	public void setxArr(int xArr) {
		this.xArr = xArr;
	}
	public int getyArr() {
		return yArr;
	}
	public void setyArr(int yArr) {
		this.yArr = yArr;
	}
	
	private String refactorX(int x){
		switch(x){
		case 0:
			return "A";
		case 1:
			return "B";
		case 2:
			return "C";
		case 3:
			return "D";
		case 4:
			return "E";
		case 5:
			return "F";
		case 6:
			return "G";
		case 7:
			return "H";
		case 8:
			return "I";
		}
		return "Error";
	}
	public String toHistory(){
		return this.refactorX(this.xDep)+(this.yDep+1)+"-"+this.refactorX(this.xArr)+(this.yArr+1);
	}
	public String toString() {
		// TODO Auto-generated method stub
		return ""+this.turnNum+".    "+((this.turn == PawnType.MOSCOVITE)?"Moscovites ":"Vikings        ")+" : "+this.toHistory();
	}
	
}
