package s6.prog6.obichouvine.models;

public class Player {
	String pseudo;
	
	public Player(String name) {
		pseudo = name;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
}
