package s6.prog6.obichouvine.models;

import s6.prog6.obichouvine.models.Pawn.PawnType;

public class Player {
	private String pseudo;
	private PawnType team;
	
	
	public Player(String name, PawnType team) {
		pseudo = name;
		this.team = team;
		System.out.println("Joueur : "+this.pseudo+" est "+team.toString());
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public PawnType getTeam() {
		return team;
	}

	public void setTeam(PawnType team) {
		this.team = team;
	}

}
