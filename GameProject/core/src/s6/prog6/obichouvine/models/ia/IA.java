package s6.prog6.obichouvine.models.ia;

import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Player;



public class IA extends Player{
	int profondeur;
	int type;
	// 1 - Gogol
	// 2 - MinMax
	// 3 - AlphaBeta
	PawnType camp;
	PawnType adv;

	public enum IaType{
		MiniMax("Difficile"), Aggro("Facile");
		
		String label;
		
		private IaType(String label){
			this.label = label;
		}

		public String toString(){
			return label;
		}
	}
	
	public IA(int t, int p, PawnType c, String pseudo) {
		super(pseudo, c);
		type = t;
		profondeur = p;
		camp = c;
		if (camp == PawnType.SUEDOIS) {
			adv = PawnType.MOSCOVITE;
		} else {
			adv = PawnType.SUEDOIS;
		}
	}
	
	public IA(int t, int p, PawnType c) {
		this(t, p, c, "Bender");
	}

	

	
}
