package s6.prog6.obichouvine.utils;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.HumanPlayer;
import s6.prog6.obichouvine.models.Player;
import s6.prog6.obichouvine.models.ia.MiniMax;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;


public class PlayerSelection extends Table{

	private Label name, diffL, pseudoL;
	private CheckBox isBot;
	private SelectBox<String> difficulty;
	private TextField pseudo;

	public String getPseudo(){
		return this.pseudo.getText();
	}
	
	public PlayerSelection(String pseudo, String name, Skin skin){
		super(skin);
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

	public Player getPlayerParameters(){
		if(isBot.isChecked()){
			//if(this.difficulty.getSelected().equals(""))
			return new MiniMax(5, 5, (this.name.equals("Moscovites"))?PawnType.MOSCOVITE:PawnType.SUEDOIS);
		}
		else
			return new HumanPlayer(pseudo.getText(), (this.name.equals("Moscovites"))?PawnType.MOSCOVITE:PawnType.SUEDOIS);
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
