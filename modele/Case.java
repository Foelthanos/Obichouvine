package modele;

public class Case {
	
	public enum TypeCase 
	{
		
		  BLANC(0),
		  ROUGE(1),
		  TRONE(2),
		  FORTERESSE(3);
	  
		  final int val;
		  
		  TypeCase(int val){
			  this.val = val;
		  }
	}

	Pion pion;
	TypeCase state;

	
	public Case(TypeCase e,Pion p) 
	{
		pion = p;
		state = e;
	}

	public Pion getPion() {
		return pion;
	}

	public void setPion(Pion pion) {
		this.pion = pion;
	}

	public TypeCase getState() {
		return state;
	}

	public void setState(TypeCase state) {
		this.state = state;
	}
	


}