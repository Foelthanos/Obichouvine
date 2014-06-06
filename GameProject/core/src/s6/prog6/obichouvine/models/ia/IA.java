package s6.prog6.obichouvine.models.ia;

import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Player;



public class IA extends Player{
	int profondeur;
	IaType type;
	PawnType camp;
	PawnType adv;
	int valManger;
	int valPerdre;
	int valRoiEntour1;
	boolean bougerRoi;

	public enum IaType{
		Difficile("Difficile"), Aggressive("Aggressive"), Defensive("Défensive"), Facile("Facile");
		
		String label;
		
		private IaType(String label){
			this.label = label;
		}

		public String toString(){
			return label;
		}
	}
	
	public IaType getType() {
		return this.type;
	}
	
	public IA(IaType t,  PawnType c, String pseudo) {
		super(pseudo, c);
		type = t;
		camp = c;
		if (camp == PawnType.SUEDOIS) {
			adv = PawnType.MOSCOVITE;
		} else {
			adv = PawnType.SUEDOIS;
		}
		if(t == IaType.Difficile) {
			profondeur = 5;
			valManger = 10;
			valPerdre = 10;
			bougerRoi = true;
			
		}
		else if (t == IaType.Aggressive) {
			profondeur = 4;
			valManger = 100;
			valPerdre = 5;
			bougerRoi = true;
		}
		else if(t == IaType.Defensive) {
			profondeur = 4;
			valManger = 5;
			valPerdre = 100;
			bougerRoi = false;
		}
		else if(t == IaType.Facile) {
			profondeur = 3;
			valManger = 10;
			valPerdre = 10;
			bougerRoi = false;
		}
	}
	
	public IA(IaType t, PawnType c) {
		this(t, c, "Bender");
	}

	

	
}
