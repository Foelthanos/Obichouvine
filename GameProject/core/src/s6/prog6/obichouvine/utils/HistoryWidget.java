package s6.prog6.obichouvine.utils;

import java.util.Iterator;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.GameController;
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
	
	public GameController gCon;
	
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

		this.history = new Historique("Historiques");
		this.cancel = new TextButton("Annuler", skin);
		this.redo = new TextButton("Refaire", skin);
		
		if(this.history.lRefaire.isEmpty()){
			this.redo.setDisabled(true);
			this.redo.setText("");
		}
		else{
			this.redo.setDisabled(false);
			this.redo.setText("Refaire");
		}
		if(this.history.l.isEmpty()){
			this.cancel.setDisabled(true);
			this.cancel.setText("");
		}
		else{
			this.cancel.setDisabled(false);
			this.cancel.setText("Annuler");
		}
		
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
				if(!history.l.isEmpty())
					cancel();
				refreshWidget();
			}
		} );
		
		this.redo.addListener(new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp(event, x, y, pointer, button);
				if(!history.lRefaire.isEmpty())
					redo();
				refreshWidget();
			}
		} );
		
		this.add(cancel).expandX().fill();
		this.add(redo).expandX().fill();
		this.row();
		this.add(mainPane).expand().fill().colspan(2);
		this.row();
		
	}
	
	public void redo(){
		Move c = history.lRefaire.getLast();
		gCon.board.deplacementsansverif(c);
		history.refaire();
		gCon.switchTurn();
		gCon.turnNum+= 0.5;
	}
	
	public void cancel(){
		history.annuler();
		Move c = history.lRefaire.getLast();
		gCon.board.deplacementsansverif(new Move(c.getxArr(), c.getyArr(), c.getxDep(), c.getyDep()));
		gCon.switchTurn();
		gCon.turnNum-= 0.5;
		
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
		
		if(this.history.lRefaire.isEmpty()){
			this.redo.setDisabled(true);
			this.redo.setText("");
		}
		else{
			this.redo.setDisabled(false);
			this.redo.setText("Refaire");
		}
		if(this.history.l.isEmpty()){
			this.cancel.setDisabled(true);
			this.cancel.setText("");
		}
		else{
			this.cancel.setDisabled(false);
			this.cancel.setText("Annuler");
		}
	}
	
	public void add(Move c){
		System.out.println("Ajout historique");
		this.history.ajouter(c);
		this.refreshWidget();
	}
	
	public void show(){
		
	}
	
}
