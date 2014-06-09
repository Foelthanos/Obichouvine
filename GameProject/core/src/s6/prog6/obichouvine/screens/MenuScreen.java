package s6.prog6.obichouvine.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.MusicManager.ObiMusic;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.models.Block;
import s6.prog6.obichouvine.utils.DefaultInputListener;

public class MenuScreen extends AbstractScreen {

	private final int BUTTONW = 150; 
	private final int BUTTONH = 30; 
	
	private SpriteBatch batch;
	private TextureRegion menuImage;
	
	private Label title = new Label("Obichouvine "+ObichouvineGame.VER, this.getSkin());
	private Label subtitle = new Label("THE END RULES UPDATE", this.getSkin());
	public MenuScreen(ObichouvineGame game) {
		super(game);
		// TODO Auto-generated constructor stub
		Label.LabelStyle realtitleStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skin2/vikingFont.fnt")), Color.WHITE);
		Label.LabelStyle titleStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skin2/titleFont.fnt")), Color.WHITE);
		this.title.setStyle(realtitleStyle);
		this.subtitle.setStyle(titleStyle);
	}

	@Override
	public void show()
	{
		super.show();
		// start playing the menu music
		if( ObichouvineGame.DEV_MODE )
			game.getMusicManager().play( ObiMusic.MENU );
		// retrieve the default table actor
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images-atlases/pages.atlas"));
        menuImage = atlas.findRegion("titleScreenImage");
        batch = this.getBatch();
        
		Table table = super.getTable();
		table.right().padRight(30).top().padTop(50);
		table.add(title).spaceBottom(0);
		table.row();
		table.add(subtitle).spaceBottom(50).right();
		table.row();
		// register the button "new game"
		TextButton startGameButton = new TextButton("Partie locale", this.getSkin());
		startGameButton.addListener(new DefaultInputListener() {
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
				game.setScreen(game.getStartLocalGameScreen());
			}
		} );
		table.add(startGameButton).size(this.BUTTONW, this.BUTTONH).uniform().spaceBottom(10).right();
		table.row();
		// register the button "new game"
		TextButton startOnlineGameButton = new TextButton("Partie en réseau", this.getSkin());
		startOnlineGameButton.addListener(new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button)
			{
				super.touchUp(event, x, y, pointer, button);
				game.getSoundManager().play( ObiSound.CLICK );
				game.setScreen(game.getStartOnlineGameScreen());
			}
		} );
		table.add(startOnlineGameButton).size(this.BUTTONW, this.BUTTONH).uniform().spaceBottom(10).right();
		table.row();
		// register the button "options"
		TextButton optionsButton = new TextButton("Options", getSkin());
		optionsButton.addListener(new DefaultInputListener() {
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
				game.setScreen(game.getOptionsScreen());
			}
		} );
		table.add(optionsButton).size(this.BUTTONW, this.BUTTONH).uniform().spaceBottom(10).right();
		table.row();

		// register the button "quitter"
		TextButton highScoresButton = new TextButton("Règles", getSkin() );
		highScoresButton.addListener(new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp(event, x, y, pointer, button);
				game.setScreen(game.getRulesScreen());
			}
		} );
		table.add(highScoresButton).size(this.BUTTONW, this.BUTTONH).uniform().spaceBottom(10).right();
		table.row();

		// register the button "quitter"
		TextButton quit = new TextButton("Quitter", getSkin());
		quit.addListener( new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp( event, x, y, pointer, button );
				Gdx.app.exit();
			}
		} );
		table.add(quit).size(this.BUTTONW, this.BUTTONH).uniform().spaceBottom(10).right();
	}
	
	public void render(float delta) {
		super.render(delta);
		batch.begin();
		batch.draw(menuImage, 0, 0);
		batch.end();
		this.stage.draw();
	}

}
