package s6.prog6.obichouvine.utils;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.models.Parameter;
import s6.prog6.obichouvine.models.Parameter.EscapeMethod;
import s6.prog6.obichouvine.models.Parameter.FirstStrike;
import s6.prog6.obichouvine.models.Parameter.KingCaptureMethod;
import s6.prog6.obichouvine.models.Parameter.KingMoveMethod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class OptionPane extends Table{
	private SelectBox<EscapeMethod> esc;
	private SelectBox<KingMoveMethod> kingMove; 
	private SelectBox<KingCaptureMethod> kingCap ;
	private SelectBox<FirstStrike> firstStrike ;
	private Array<EscapeMethod> escArray;
	private Array<KingMoveMethod> kingMoveArray; 
	private Array<KingCaptureMethod> kingCapArray;
	private Array<FirstStrike> firstStrikeArray;

	private Image escIcon;
	private Image kingMoveIcon;
	private Image kingCapIcon;
	private Image firstStrikeIcon;
	
	public OptionPane(Skin skin){
		super(skin);
			
		esc= new SelectBox<EscapeMethod>(skin);
		kingCap = new SelectBox<KingCaptureMethod>(skin);
		kingMove = new SelectBox<KingMoveMethod>(skin);  
		firstStrike = new SelectBox<Parameter.FirstStrike>(skin);
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images-atlases/pages.atlas"));
		
		escIcon = new Image(atlas.findRegion("escapeBlock"));
		kingMoveIcon = new Image(atlas.findRegion("Roi"));
		kingCapIcon = new Image(atlas.findRegion("vikingBlock"));
		firstStrikeIcon = new Image(atlas.findRegion("escapeBlock"));
		
		 if( ObichouvineGame.DEV_MODE ) {
             this.debug();
         }
		 this.printCommonContent();
	}
	
	private void printCommonContent(){
		this.defaults().spaceBottom(15);
		this.columnDefaults(0).padRight(5);
		this.columnDefaults(1).padRight(20);
		this.add("Paramètres de la partie").spaceBottom(50).colspan(3).padRight(0);
		this.row();
		firstStrikeArray = new Array<FirstStrike>();
		for (FirstStrike first : FirstStrike.values()) {
			  // do what you want
			firstStrikeArray.add(first);
			}
		firstStrike.setItems(firstStrikeArray);
		
		this.add(firstStrikeIcon).size(40, 40);
		this.add("Initiative :").fillX().center().expandY();
		this.add(firstStrike).fillX().center().expandY();
		this.row();
		escArray = new Array<EscapeMethod>();
		for (EscapeMethod esc : EscapeMethod.values()) {
			  // do what you want
			escArray.add(esc);
			}
		esc.setItems(escArray);
		
		this.add(escIcon).size(40, 40);
		this.add("Méthodes de fuite :").fillX().center().expandY();
		this.add(esc).fillX().center().expandY();
		this.row();
		
		kingCapArray = new Array<KingCaptureMethod>();
		for (KingCaptureMethod kcap : KingCaptureMethod.values()) {
			  // do what you want
			kingCapArray.add(kcap);
			}
		kingCap.setItems(kingCapArray);
		
		this.add(kingCapIcon).size(40, 40);
		this.add("Capture par le roi :").fillX().center().expand();
		this.add(kingCap).fillX().center().expand();
		this.row();
		
		kingMoveArray = new Array<KingMoveMethod>();
		for (KingMoveMethod kmove : KingMoveMethod.values()) {
			  // do what you want
			kingMoveArray.add(kmove);
			}
		kingMove.setItems(kingMoveArray);
		
		this.add(kingMoveIcon).size(40, 40);
		this.add("Mouvements du roi :").fillX().center().expand();
		this.add(kingMove).fillX().center().expand();
		this.row();
	}
	
	public Parameter generateParameter(){
		return new Parameter(esc.getSelected(), kingCap.getSelected(), kingMove.getSelected(), firstStrike.getSelected());
	}	
}
