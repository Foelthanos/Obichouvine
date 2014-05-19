package s6.prog6.obichouvine.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class OptionPane extends Table{

	
	public enum Content{
		IAvsIA, PvsIA, PvsP
	}
	
	public OptionPane(Content content, Skin skin){
		super(skin);
		this.updateContent(content);
	}
	
	public void updateContent(Content content){
		this.clear();
		if(content == Content.IAvsIA)
			this.printIAvsIA();
		else if(content == Content.PvsIA)
			this.printPvsIA();
		else if(content == Content.PvsP)
			this.printPvsP();
	}

	private void printPvsP() {
		// TODO Auto-generated method stub
		this.add("Parametres de la partie : Joueur vs Joueur").spaceBottom(50).spaceLeft(30);
		this.top().left();
		this.row();
	}

	private void printPvsIA() {
		// TODO Auto-generated method stub
		this.add("Parametres de la partie : Joueur vs IA").spaceBottom(50).spaceLeft(30);
		this.top().left();
		this.row();
	}

	private void printIAvsIA() {
		// TODO Auto-generated method stub
		this.add("Parametres de la partie : IA vs IA").spaceBottom(50).spaceLeft(30);
		this.top().left();
		this.row();
	}
	
}
