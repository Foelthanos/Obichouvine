package s6.prog6.obichouvine.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block implements Cloneable{
	
	public enum BlockState 
	{
		  BLANC,
		  ROUGE,
		  BLANCEXIT,
		  ROUGEEXIT,
		  TRONE,
		  FORTERESSE;
		  
	}

	Pawn pawn;
	BlockState state;
	boolean surbrillance;

	public static final float SIZE = 40f;
	
	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	
	
	
	public Block(Vector2 pos, BlockState e, Pawn p) 
	{
		this.position = pos;
		this.pawn = p;
		this.state = e;
		this.surbrillance = false;
	}

	public Pawn getPawn() {
		return pawn;
	}

	public void setPawn(Pawn pawn) {
		this.pawn = pawn;
	}

	public BlockState getState() {
		return state;
	}

	public void setState(BlockState state) {
		this.state = state;
	}

	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return this.position;
	}
	public boolean isSurbrillance() {
		return surbrillance;
	}

	public void setSurbrillance(boolean surbrillance) {
		this.surbrillance = surbrillance;
	}

}