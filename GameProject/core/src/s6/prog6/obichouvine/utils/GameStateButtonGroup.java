package s6.prog6.obichouvine.utils;

import s6.prog6.obichouvine.models.Board;
import s6.prog6.obichouvine.models.GameState;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameStateButtonGroup extends Table{
	public TextButton save, load, quickStart;

	public GameStateButtonGroup(Skin skin){
		save = new TextButton("Sauvegarder", skin);
		//load = new TextButton("Charger", skin);
		quickStart = new TextButton("Recommencer", skin);
		
		
	
		this.add(save).size(150, 40);
		//this.add(load).size(150, 40);
		this.add(quickStart).size(150, 40);
		
	}
	
}
