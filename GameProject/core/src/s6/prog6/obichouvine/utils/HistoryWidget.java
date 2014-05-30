package s6.prog6.obichouvine.utils;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.models.Block;
import s6.prog6.obichouvine.models.Historique;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class HistoryWidget extends Table{

	private TextArea moveArea;
	private Historique history;
	private TextButton cancel, redo;
	
	public HistoryWidget(Skin skin){
		super();
		if( ObichouvineGame.DEV_MODE ) {
            this.debug();
        }
		
		
		this.left();
		this.moveArea = new TextArea("", skin);
		this.moveArea.setDisabled(true);
		this.history = new Historique("Historiques");
		this.cancel = new TextButton("Annuler", skin);
		this.redo = new TextButton("Refaire", skin);
		
		this.add(cancel).expandX().fill();
		this.add(redo).expandX().fill();
		this.row();
		this.add(moveArea).expand().fill().colspan(2);
		this.row();
		
	}
	
	public void show(){
		
	}
}
