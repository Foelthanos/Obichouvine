package s6.prog6.obichouvine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import s6.prog6.obichouvine.controllers.GameController;
import s6.prog6.obichouvine.controllers.GameRenderer;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.models.Board;
import s6.prog6.obichouvine.screens.AbstractScreen;
import s6.prog6.obichouvine.utils.DefaultInputListener;

public class GameScreen extends AbstractScreen implements InputProcessor{
	GameController gController;
	GameRenderer gRenderer;
	
	public GameScreen(ObichouvineGame game) {
		super(game);
		// TODO Auto-generated constructor stub
		this.gController = new GameController();
		this.gRenderer = new GameRenderer(new Board(9, 9));
	}

	public void show(){
		super.show();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
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
	
		TextButton save = new TextButton("Save", this.getSkin());
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
		
		TextButton surrend = new TextButton("Surrend", this.getSkin());
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
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	private void fillScreen(int fillSize, Table table){
		for(int i=0; i<fillSize; i++)
			table.add().expandX();
	}
	
	public void render(float delta) {
		super.render(delta);
		gRenderer.render();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		System.out.println("C'est bon !");
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
