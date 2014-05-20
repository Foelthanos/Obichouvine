package s6.prog6.obichouvine.models;

public class Block {
	
	public enum BlockType 
	{
		
		  BLANC(0),
		  ROUGE(1),
		  TRONE(2),
		  FORTERESSE(3);
	  
		  final int val;
		  
		  BlockType(int val){
			  this.val = val;
		  }
	}

	Pawn pion;
	BlockType state;

	
	public Block(BlockType e,Pawn p) 
	{
		pion = p;
		state = e;
	}

	public Pawn getPion() {
		return pion;
	}

	public void setPion(Pawn pion) {
		this.pion = pion;
	}

	public BlockType getState() {
		return state;
	}

	public void setState(BlockType state) {
		this.state = state;
	}
	


}