package s6.prog6.obichouvine.utils;

import java.util.Iterator;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.models.Block;
import s6.prog6.obichouvine.models.Board;
import s6.prog6.obichouvine.models.Historique;
import s6.prog6.obichouvine.models.Move;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class HistoryWidget extends Table{

	private ScrollPane mainPane;
	private TextArea moveArea;
	private Historique history;
	private TextButton cancel, redo;
	
	public Board board;
	
	public HistoryWidget(Skin skin){
		super();
		if( ObichouvineGame.DEV_MODE ) {
            this.debug();
        }
		
		this.left();
		this.moveArea = new TextArea("", skin);
		this.moveArea.setDisabled(true);
		this.mainPane = new ScrollPane(moveArea);
		this.mainPane.setScrollbarsOnTop(true);
		//this.mainPane.set
		this.history = new Historique("Historiques");
		this.cancel = new TextButton("Annuler", skin);
		this.redo = new TextButton("Refaire", skin);
		this.redo.setDisabled(this.history.lRefaire.isEmpty());
		
		
		this.cancel.addListener(new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp(event, x, y, pointer, button);
				history.annuler();
				refreshWidget();
			}
		} );
		
		this.add(cancel).expandX().fill();
		this.add(redo).expandX().fill();
		this.row();
		this.add(mainPane).expand().fill().colspan(2);
		this.row();
		
	}
	
	public Move cancel(){
		history.annuler();
		
		return null;
	}
	
	public void refreshWidget(){
		String res = "";
		Move c;
		Iterator<Move> it = history.l.iterator();
		while(it.hasNext()){
			c = it.next();
			//System.out.println(c);
			res = c+"\n"+res;
		}
		this.moveArea.setText(res);
	}
	
	public void add(Move c){
		System.out.println("Ajout historique");
		this.history.l.add(c);
		this.refreshWidget();
	}
	
	public void show(){
		
	}
	
}
