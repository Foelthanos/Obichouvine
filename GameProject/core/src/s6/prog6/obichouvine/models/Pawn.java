package s6.prog6.obichouvine.models;

public class Pawn {
	
	public enum PawnType 
	{
		VIDE(0),
		SUEDOIS(1),
		MOSCOVITE(2);
		  
		final int val;
			  
		PawnType(int val){
			this.val = val;
		}
	}
	
	public enum TypeSuedois
	{
		VIDE(0),
		KING(1),
		PION(2);
		  
		final int val;
			  
		TypeSuedois(int val){
			this.val = val;
		}
	}
	TypeSuedois typesuede;
	PawnType type;
	boolean surbri;
	boolean spirit;

	public Pawn(PawnType type, TypeSuedois k){
		typesuede = k;
		this.type = type;
	}
	public Pawn(PawnType type){
		typesuede = TypeSuedois.VIDE;
		this.type = type;
	}

	public TypeSuedois getTypesuede() {
		return typesuede;
	}

	public void setTypesuede(TypeSuedois king) {
		this.typesuede = king;
	}

	public PawnType getType()
	{
		return type;
	}

	public void setType(PawnType type) {
		this.type = type;
	}
	
	public String toString(){
		return ""+this.type;
	}

	public boolean getSurbri() {
		return surbri;
	}
	
	public void setSurbri(boolean subri) {
		this.surbri = subri;
	}
	
	public boolean getSpirit() {
		return spirit;
	}
	
	public void setSpirit(boolean spirit) {
		this.spirit = spirit;
	}
}
