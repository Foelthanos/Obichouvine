package s6.prog6.obichouvine.utils;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;

public class OptionPane extends Table{
	private Skin skin;
	private ObichouvineGame game;
	private TextArea pseudo1, pseudo2;

	
	public enum Content{
		IAvsIA, PvsIA, PvsP
	}
	
	public OptionPane(Content content, Skin skin, ObichouvineGame game){
		super(skin);
		this.skin = skin;
		this.updateContent(content);
		this.game = game;
		pseudo1 = new TextArea("pseudo j1", skin);
		pseudo2 = new TextArea("pseudo j2", skin);
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
		this.add("Parametres de la partie : Joueur vs Joueur").spaceBottom(50).spaceLeft(30).colspan(2);
		this.row();
		this.add("joueur 1 :").spaceBottom(30).fillX().center().expandY();
		this.add(pseudo1).spaceBottom(30).fillX().center().expandY();
		this.row();
		this.add("Joueur 2 :").spaceBottom(30).fillX().center().expandY();
		this.add(pseudo2).spaceBottom(30).fillX().center().expandY();
		this.row();
		this.printValidate();
		
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
		
		
		
		ia1.setItems(algo);
		ia2.setItems(algo);
		
		this.add("Parametres de la partie : IA vs IA").spaceBottom(50).spaceLeft(30).expandX().colspan(2);
		this.row();
		
		this.add("Difficult� IA 1 :").spaceBottom(30).fillX().center().expandY();
		this.add(ia1).uniform().spaceBottom(30).fillX().expandY();
		this.row();
		
		this.add("Difficult� IA 2 :").spaceBottom(30).fillX().center().expandY();
		this.add(ia2).uniform().spaceBottom(30).fillX().expandY();
		this.row();
		
		this.printValidate();
	}
	
	private void printValidate(){
		TextButton validate = new TextButton("Valider", skin);
		validate.addListener(new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp(event, x, y, pointer, button);
				game.getSoundManager().play(ObiSound.CLICK);
				game.setScreen(game.getGameScreen());
			}
		} );
		this.add(validate).colspan(2).fillX();
		this.row();
	}
	
}
