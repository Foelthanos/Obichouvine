package s6.prog6.obichouvine.utils;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

public class PlayerSelection extends Table{

	Label name, diffL, pseudoL;
	CheckBox isBot;
	SelectBox<String> difficulty;
	TextField pseudo;

	public PlayerSelection(String pseudo, String name, Skin skin){
		this.name = new Label(name, skin);
		this.pseudoL = new Label("Pseudonyme", skin);
		this.diffL = new Label("Difficulté", skin);

		this.isBot = new CheckBox("Activer IA", skin);
		this.difficulty = new SelectBox<String>(skin);
		this.difficulty.setItems("Facile","Normal","Difficile");

		 if( ObichouvineGame.DEV_MODE ) {
             this.debug();
         }
		
		this.isBot.addListener(new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp(event, x, y, pointer, button);
				printWidget();
			}
		} );
		this.pseudo = new TextField(pseudo, skin);
		this.printWidget();
	}

	private void printWidget(){
		this.clear();
		this.add(name).expand();
		this.row();
		this.add(isBot);
		this.row();
		if(this.isBot.isChecked())
			this.add(difficulty).fill().height(20);
		else
			this.add(pseudo).fill().height(20);
		this.row();
	}
}
