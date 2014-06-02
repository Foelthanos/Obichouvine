package s6.prog6.obichouvine.models;

import s6.prog6.obichouvine.models.Pawn.PawnType;

public class Move {
	int xDep;
	int yDep;
	int xArr;
	int yArr;
	PawnType turn;
	int turnNum;
	
	public Move(int x, int y, int x1, int y1)
	{
		xDep = x;
		yDep = y;
		xArr = x1;
		yArr = y1;
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

	public String toString(){
		return "("+this.xDep+","+this.yDep+") : ("+this.xArr+","+this.yArr+")";
	}
	public String toHistory() {
		// TODO Auto-generated method stub
		return ""+this.turnNum+"|"+((this.turn == PawnType.MOSCOVITE)?"Moscovites":"Vikings")+"|"+this.toString();
	}
	
}
