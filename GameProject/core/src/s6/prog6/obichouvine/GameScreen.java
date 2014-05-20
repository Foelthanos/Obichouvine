package s6.prog6.obichouvine;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import s6.prog6.obichouvine.controllers.GameController;
import s6.prog6.obichouvine.controllers.GameRenderer;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.screens.AbstractScreen;
import s6.prog6.obichouvine.utils.DefaultInputListener;

public class GameScreen extends AbstractScreen{
	GameController gController;
	GameRenderer gRenderer;
	
	public GameScreen(ObichouvineGame game) {
		super(game);
		// TODO Auto-generated constructor stub
		this.gController = new GameController();
		this.gRenderer = new GameRenderer();
	}

	public void show(){
		super.show();
		
		Table table = super.getTable();
		table.top();
		table.add("Joueur 1 : Joueur 2").spaceBottom(50).colspan(20).expandX().fillY();
		table.row();
		
		table.add("Historique :").expandX().colspan(2);
		this.fillScreen(16, table);
		table.add("Status :").expandX().colspan(2);
		table.row();
		
		TextButton cancel = new TextButton("Annuler", this.getSkin());
		cancel.addListener(new DefaultInputListener() {
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
		table.add(cancel).fillX();
		
		TextButton redo = new TextButton("Refaire", this.getSkin());
		redo.addListener(new DefaultInputListener() {
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
				//game.setScreen(game.getGameScreen());
			}
		} );
		table.add(redo).fillX();
		
		this.fillScreen(16, table);
	
		TextButton save = new TextButton("Sauvegarder", this.getSkin());
		save.addListener(new DefaultInputListener() {
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
		table.add(save).fillX();
		
		TextButton surrend = new TextButton("Abandonner", this.getSkin());
		surrend.addListener(new DefaultInputListener() {
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
				//game.setScreen(game.getGameScreen());
			}
		} );
		table.add(surrend).fillX();
		table.row();
		
	}
	
	private void fillScreen(int fillSize, Table table){
		for(int i=0; i<fillSize; i++)
			table.add().expandX();
	}
	
}
