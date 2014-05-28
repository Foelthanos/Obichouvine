package s6.prog6.obichouvine.utils;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.models.Parameter;
import s6.prog6.obichouvine.models.Parameter.EscapeMethod;
import s6.prog6.obichouvine.models.Parameter.KingCaptureMethod;
import s6.prog6.obichouvine.models.Parameter.KingMoveMethod;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class OptionPane extends Table{
	private SelectBox<EscapeMethod> esc;
	private SelectBox<KingMoveMethod> kingMove; 
	private SelectBox<KingCaptureMethod> kingCap ;
	private Array<EscapeMethod> escArray;
	private Array<KingMoveMethod> kingMoveArray; 
	private Array<KingCaptureMethod> kingCapArray;

	
	public OptionPane(Skin skin){
		super(skin);
			
		esc= new SelectBox<EscapeMethod>(skin);
		kingCap = new SelectBox<KingCaptureMethod>(skin);
		kingMove = new SelectBox<KingMoveMethod>(skin);  
		 if( ObichouvineGame.DEV_MODE ) {
             this.debug();
         }
		 this.printCommonContent();
	}
	
	private void printCommonContent(){
		this.add("Parametres de la partie").spaceBottom(50).colspan(2);
		this.row();
		escArray = new Array<EscapeMethod>();
		for (EscapeMethod esc : EscapeMethod.values()) {
			  // do what you want
			escArray.add(esc);
			}
		esc.setItems(escArray);
		
		this.add("Escape  :").fillX().center().expandY();
		this.add(esc).fillX().center().expandY();
		this.row();
		
		kingCapArray = new Array<KingCaptureMethod>();
		for (KingCaptureMethod kcap : KingCaptureMethod.values()) {
			  // do what you want
			kingCapArray.add(kcap);
			}
		kingCap.setItems(kingCapArray);
		this.add("Capture  :").fillX().center().expand();
		this.add(kingCap).fillX().center().expand();
		this.row();
		
		kingMoveArray = new Array<KingMoveMethod>();
		for (KingMoveMethod kmove : KingMoveMethod.values()) {
			  // do what you want
			kingMoveArray.add(kmove);
			}
		kingMove.setItems(kingMoveArray);
		this.add("Mouvement du roi :").fillX().center().expand();
		this.add(kingMove).fillX().center().expand();
		this.row();
	}
	
	public Parameter generateParameter(){
		return new Parameter(esc.getSelected(), kingCap.getSelected(), kingMove.getSelected());
	}	
}
