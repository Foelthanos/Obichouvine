package s6.prog6.obichouvine.utils;

import java.util.EnumSet;
import java.util.Set;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.models.Parameter;
import s6.prog6.obichouvine.models.Parameter.EscapeMethod;
import s6.prog6.obichouvine.models.Parameter.KingCaptureMethod;
import s6.prog6.obichouvine.models.Parameter.KingMoveMethod;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;

public class OptionPane extends Table{
	private Skin skin;
	private ObichouvineGame game;
	private TextArea pseudo1, pseudo2;
	private SelectBox<String> esc, kingMove, kingCap ;
	private Array<String> tmpArray;
	
	public enum Content{
		IAvsIA, PvsIA, PvsP
	}
	
	public OptionPane(Content content, Skin skin, ObichouvineGame game){
		super(skin);
		this.skin = skin;
		this.updateContent(Content.IAvsIA);
		this.game = game;		
		esc= new SelectBox<String>(skin);
		//esc.setCullingArea(new Rectangle(0, 0, esc.getPrefWidth(), esc.getItemHeight()));
		kingCap = new SelectBox<String>(skin);
		kingMove = new SelectBox<String>(skin);                                                
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

	private void printCommonContent(){
		tmpArray = new Array<String>();
		for (EscapeMethod esc : EscapeMethod.values()) {
			  // do what you want
			tmpArray.add(esc.text);
			}
		esc.setItems(tmpArray);
		
		this.add("Escape  :").spaceBottom(30).fillX().center().expandY();
		this.add(esc).spaceBottom(30).fillX().center().expandY();
		this.row();
		tmpArray = new Array<String>();
		for (KingCaptureMethod esc : KingCaptureMethod.values()) {
			  // do what you want
			tmpArray.add(esc.text);
			}
		kingCap.setItems(tmpArray);
		this.add("Capture  :").spaceBottom(30).fillX().center().expandY();
		this.add(kingCap).spaceBottom(30).fillX().center().expandY();
		this.row();
	}
	private void printPvsP() {
		pseudo1 = new TextArea("pseudo j1", skin);
		pseudo2 = new TextArea("pseudo j2", skin);
		this.add("Parametres de la partie : Joueur vs Joueur").spaceBottom(50).spaceLeft(30).colspan(2);
		this.row();
		this.printCommonContent();
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
		tmpArray = new Array<String>();
		tmpArray.add("Facile");
		tmpArray.add("Moyen");
		
		ia1.setItems(tmpArray);
		ia2.setItems(tmpArray);
		
		this.add("Parametres de la partie : IA vs IA").spaceBottom(50).spaceLeft(30).expandX().colspan(2);
		this.row();
		
		this.add("Difficulté IA 1 :").spaceBottom(30).fillX().center().expandY();
		this.add(ia1).uniform().spaceBottom(30).fillX().expandY();
		this.row();
		
		this.add("Difficulté IA 2 :").spaceBottom(30).fillX().center().expandY();
		this.add(ia2).uniform().spaceBottom(30).fillX().expandY();
		this.row();
		
		this.printValidate();
	}
	
	private Parameter generateParameter(){
		
		
		return null;
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
				game.setScreen(game.getGameScreen(new Parameter(EscapeMethod.Corner, KingCaptureMethod.Can, KingMoveMethod.Unlimited )));
			}
		} );
		this.add(validate).colspan(2).fillX();
		this.row();
	}
	
}
