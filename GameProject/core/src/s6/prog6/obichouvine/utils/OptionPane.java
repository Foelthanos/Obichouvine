package s6.prog6.obichouvine.utils;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;

public class OptionPane extends Table{
	private Skin skin;
	
	public enum Content{
		IAvsIA, PvsIA, PvsP
	}
	
	public OptionPane(Content content, Skin skin){
		super(skin);
		this.skin = skin;
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
		
		
		this.add("Parametres de la partie : Joueur vs Joueur").spaceBottom(50).spaceLeft(30).colspan(2);
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
		List<String> ia1 = new List<String>(skin);
		List<String> ia2 = new List<String>(skin);
		Array<String> algo= new Array<String>();
		algo.add("Facile");
		algo.add("Moyen");
		
		TextButton validate = new TextButton("Valider", skin);
		
		ia1.setItems(algo);
		ia2.setItems(algo);
		
		this.add("Parametres de la partie : IA vs IA").spaceBottom(50).spaceLeft(30).expandX().colspan(2);
		this.row();
		
		this.add("Difficulté IA 1 :").spaceBottom(30).fillX().center().expandY();
		this.add(ia1).uniform().spaceBottom(30).fillX().expandY();
		this.row();
		
		this.add("Difficulté IA 2 :").spaceBottom(30).fillX().center().expandY();
		this.add(ia2).uniform().spaceBottom(30).fillX().expandY();
		this.row();
		
		this.add(validate).colspan(2).fillX();
		this.row();
		
		
	}
	
}
