package s6.prog6.obichouvine.utils;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.models.Board;
import s6.prog6.obichouvine.models.Parameter.EscapeMethod;
import s6.prog6.obichouvine.models.Parameter.FirstStrike;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.HumanPlayer;
import s6.prog6.obichouvine.models.Player;
import s6.prog6.obichouvine.models.ia.AlphaBeta;
import s6.prog6.obichouvine.models.ia.IA.IaType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;


public class PlayerSelection extends Table{
	
	private String funnyNames[] = {"TheSecond", "The Bad", "Player2", "Challenger", "Hank Bot", "Bender", "CARL 500"};
	
	private Label name, diffL, pseudoL;
	private CheckBox isBot;
	private SelectBox<IaType> difficulty;
	private Image logo;
	private TextField pseudo;

	private Array<IaType> iaType;
	private String savedPseudo;
	

	
	public String getPseudo(){
		return this.pseudo.getText();
	}

	public PlayerSelection(String pseudo, String name, Skin skin, boolean botIsChecked){
		super(skin);
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images-atlases/pages.atlas"));
		
	
		this.logo = new Image(new TextureRegion(
				(name.equals("Moscovites"))?atlas.findRegion("russianBlock"):atlas.findRegion("vikingBlock")));
		
		this.name = new Label(name, skin);
		this.pseudoL = new Label("Pseudonyme", skin);
		this.diffL = new Label("Difficulté", skin);

		this.isBot = new CheckBox("Activer IA", skin);
		this.difficulty = new SelectBox<IaType>(skin);
		
		this.savedPseudo = pseudo;
		iaType = new Array<IaType>();
		for (IaType first : IaType.values()) {
			// do what you want
			iaType.add(first);
		}
		difficulty.setItems(iaType);
		this.isBot.setChecked(botIsChecked);
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
			return new AlphaBeta(difficulty.getSelected(), (this.name.textEquals("Moscovites"))?PawnType.MOSCOVITE:PawnType.SUEDOIS, pseudo.getText());
		}
		else
			return new HumanPlayer(pseudo.getText(), (this.name.textEquals("Moscovites"))?PawnType.MOSCOVITE:PawnType.SUEDOIS);
	}

	private void printWidget(){
		this.clear();
		this.difficulty.setDisabled(!this.isBot.isChecked());
		if(this.isBot.isChecked()){
			this.pseudo.setText(this.funnyNames[(int)(Math.random() * (this.funnyNames.length-1) + 1)]);
			this.pseudo.setDisabled(true);
		}
		else{
			this.pseudo.setText(this.savedPseudo);
			this.pseudo.setDisabled(false);
		}
		
		this.add(logo).size(50, 50);
		this.row();
		this.add(name).expand();
		this.row();
		this.add(pseudo).fill().height(20);
		this.row();
		this.add(isBot);
		this.row();
		this.add(difficulty).fill().height(20);
		this.row();
	}
}
