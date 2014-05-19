package modele;

public class Pion {
	
	public enum TypePion 
	{
		VIDE(0),
		SUEDOIS(1),
		MOSCOVITE(2);
		  
		final int val;
			  
		TypePion(int val){
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
	TypePion type;

	Pion(TypePion type, TypeSuedois k){
		typesuede = k;
		this.type = type;
	}
	Pion(TypePion type){
		typesuede = TypeSuedois.VIDE;
		this.type = type;
	}

	public TypeSuedois getTypesuede() {
		return typesuede;
	}

	public void setTypesuede(TypeSuedois king) {
		this.typesuede = king;
	}

	public TypePion getType()
	{
		return type;
	}

	public void setType(TypePion type) {
		this.type = type;
	}
	

}