package s6.prog6.obichouvine.utils;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameStateButtonGroup extends HorizontalGroup{
	TextButton save, load, quickStart;
	
	public GameStateButtonGroup(Skin skin){
		save = new TextButton("Sauvegarder", skin);
		load = new TextButton("Charger", skin);
		quickStart = new TextButton("Recommencer", skin);
		
		
		this.addActor(save);
		this.addActor(load);
		this.addActor(quickStart);
	}
}
