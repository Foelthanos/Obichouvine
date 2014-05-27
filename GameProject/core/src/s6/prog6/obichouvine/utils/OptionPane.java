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
		
		this.game = game;		
		esc= new SelectBox<String>(skin);
		kingCap = new SelectBox<String>(skin);
		kingMove = new SelectBox<String>(skin);  
		 if( ObichouvineGame.DEV_MODE ) {
             this.debug();
         }
		 //this.updateContent(Content.PvsP);
		 this.printCommonContent();
	}
	
	private void printCommonContent(){
		this.add("Parametres de la partie").spaceBottom(50).colspan(2);
		this.row();
		tmpArray = new Array<String>();
		for (EscapeMethod esc : EscapeMethod.values()) {
			  // do what you want
			tmpArray.add(esc.text);
			}
		esc.setItems(tmpArray);
		
		this.add("Escape  :").fillX().center().expandY();
		this.add(esc).fillX().center().expandY();
		this.row();
		tmpArray = new Array<String>();
		for (KingCaptureMethod esc : KingCaptureMethod.values()) {
			  // do what you want
			tmpArray.add(esc.text);
			}
		kingCap.setItems(tmpArray);
		this.add("Capture  :").fillX().center().expand();
		this.add(kingCap).fillX().center().expand();
		this.row();
	}
	
	public Parameter generateParameter(){
		
		
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
