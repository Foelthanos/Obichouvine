package s6.prog6.obichouvine.utils;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

public class PlayerSelection extends VerticalGroup{

	Label name, diffL, pseudoL;
	CheckBox isBot;
	SelectBox<String> difficulty;
	TextField pseudo;
	
	public PlayerSelection(String pseudo, String name, Skin skin){
		this.name = new Label(name, skin);
		this.pseudoL = new Label(pseudo, skin);
		this.diffL = new Label("Difficulté", skin);
		
		this.isBot = new CheckBox("Activer IA", skin);
		
		this.difficulty = new SelectBox<String>(skin);
		this.difficulty.setItems("Facile","Normal","Difficile");
		
		//this.
	}
}
