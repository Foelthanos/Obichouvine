package s6.prog6.obichouvine.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.GameController;
import s6.prog6.obichouvine.controllers.GameRenderer;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.models.Block;
import s6.prog6.obichouvine.models.Board;
import s6.prog6.obichouvine.models.HumanPlayer;
import s6.prog6.obichouvine.models.Move;
import s6.prog6.obichouvine.models.Parameter;
import s6.prog6.obichouvine.models.Parameter.FirstStrike;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Player;
import s6.prog6.obichouvine.models.ia.IA;
import s6.prog6.obichouvine.screens.AbstractScreen;
import s6.prog6.obichouvine.utils.DefaultInputListener;
import s6.prog6.obichouvine.utils.GameStateButtonGroup;
import s6.prog6.obichouvine.utils.GameStatusWidget;
import s6.prog6.obichouvine.utils.HistoryWidget;

public class GameScreen extends AbstractScreen implements InputProcessor{
	private GameController gController;
	private GameRenderer gRenderer;
	
	private Label headMessage;
	private TextButton quit;
	
	private GameStateButtonGroup gameStateButtons;
	
	private HistoryWidget history;
	
	private Board board;
	private GameStatusWidget status;
	
	private char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M'};
	private char[] number = {'1','2','3','4','5','6','7','8','9','0','1','1'};
	
	public GameScreen(ObichouvineGame game, Parameter param, Player p1, Player p2) {
		super(game);
		// TODO Auto-generated constructor stub
		this.board = new Board(9, 9, param);
		this.gRenderer = new GameRenderer(board);
		this.gController = new GameController(board, 
				(param.getfStrike()==FirstStrike.Moscovite)?PawnType.MOSCOVITE:PawnType.SUEDOIS,
						p1, p2);
		
		
		headMessage = new Label("Placeholder Placeholder", getSkin());
		quit = new TextButton("Quitter", this.getSkin());
		
		gameStateButtons = new GameStateButtonGroup(getSkin());
		
		history = new HistoryWidget(this.getSkin());
		
		status = new GameStatusWidget(this.getSkin(), p1, p2);
	}

	
	public void show(){
		super.show();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
		
		quit.addListener(new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp(event, x, y, pointer, button);
				game.getSoundManager().play( ObiSound.CLICK );
				game.setScreen(game.getMenuScreen());
			}
		} );
		
		Table table = super.getTable();
		table.top();
		
		table.add(headMessage).expandX().colspan(4).spaceTop(20);
		table.row();
		table.add(status).expand().fill().left().width(Block.SIZE*5).height(Block.SIZE*9);
		table.add(history).expand().fill().right().width(Block.SIZE*5).height(Block.SIZE*9);
		table.row();
		table.add(quit).left().expandX();
		table.add(gameStateButtons).right().expandX();
		table.row();
		Gdx.input.setInputProcessor(multiplexer);
		gController.gameStarted = true;
	}
	
	
	public void render(float delta) {
		
	
		Move c = gController.update(delta);
		status.updateWidget(gController.mosc, gController.vik);

		if(c != null)
			history.add(c);
		super.render(delta);
		gRenderer.render();
	}

	public void dispose(){
		super.dispose();
		gRenderer.dispose();
		
        Gdx.input.setInputProcessor(null);
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
		gController.clickPressed(new Vector2(screenX, screenY), button);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		gController.clickReleased(new Vector2(screenX, screenY), button);
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
