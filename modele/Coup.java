package modele;

public class Coup {
	int xDep;
	int yDep;
	int xArr;
	int yArr;
	
	public Coup(int x, int y, int x1, int y1)
	{
		xDep = x;
		yDep = y;
		xArr = x1;
		yArr = y;
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

	
}
