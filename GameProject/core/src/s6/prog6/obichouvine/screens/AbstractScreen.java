package s6.prog6.obichouvine.screens;

import s6.prog6.obichouvine.ObichouvineGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class AbstractScreen implements Screen{

	public static final int GAME_VIEWPORT_WIDTH = 800, GAME_VIEWPORT_HEIGHT = 480;
	public static final int MENU_VIEWPORT_WIDTH = 800, MENU_VIEWPORT_HEIGHT = 480;

	protected final ObichouvineGame game;
	
	protected final Stage stage;
	
	private BitmapFont font;
    private SpriteBatch batch;
    private Skin skin;
    private TextureAtlas atlas;
    private Table table;


	public AbstractScreen(ObichouvineGame game ){
		this.game = game;
		/* width = ( isGameScreen() ? GAME_VIEWPORT_WIDTH : MENU_VIEWPORT_WIDTH );
		int height = ( isGameScreen() ? GAME_VIEWPORT_HEIGHT : MENU_VIEWPORT_HEIGHT );*/
		this.stage = new Stage();
	}

	protected boolean isGameScreen()
    {
        return false;
    }
	
	protected String getName()
    {
        return getClass().getSimpleName();
    }

	public SpriteBatch getBatch()
    {
        if( batch == null ) {
            batch = new SpriteBatch();
        }
        return batch;
    }
	
	public BitmapFont getFont()
    {
        if( font == null ) {
            font = new BitmapFont();
        }
        return font;
    }
	
	protected Skin getSkin()
    {
        if( skin == null ) {
            FileHandle skinFile = Gdx.files.internal( "skin2/uiskin.json" );
            skin = new Skin( skinFile );
        }
        return skin;
    }
	
	protected Table getTable()
    {
        if( table == null ) {
            table = new Table(getSkin());
            table.setFillParent(true);
            if( ObichouvineGame.DEV_MODE ) {
                table.debug();
            }
            stage.addActor(table);
        }
        return table;
    }
	
	public TextureAtlas getAtlas(){
        if( atlas == null ) {
            atlas = new TextureAtlas( Gdx.files.internal( "images-atlases/pages.atlas" ) );
        }
        return atlas;
    }
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
        // (1) process the game logic

        // update the actors
        stage.act(delta);

        // (2) draw the result

        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // draw the actors
        stage.draw();

        // draw the table debug lines
        Table.drawDebug(stage);

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		 Gdx.app.log( ObichouvineGame.LOG, "Resizing screen: " + getName() + " to: " + width + " x " + height );
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.app.log( ObichouvineGame.LOG, "Showing screen: " + getName());

        // set the stage as the input processor
        Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Gdx.app.log( ObichouvineGame.LOG, "Hiding screen: " + getName());

        // dispose the screen when leaving the screen;
        // note that the dipose() method is not called automatically by the
        // framework, so we must figure out when it's appropriate to call it
        this.dispose();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Gdx.app.log( ObichouvineGame.LOG, "Pausing screen: " + getName());
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		Gdx.app.log( ObichouvineGame.LOG, "Resuming screen: " + getName());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		Gdx.app.log( ObichouvineGame.LOG, "Disposing screen: " + getName());

        // the following call disposes the screen's stage, but on my computer it
        // crashes the game so I commented it out; more info can be found at:
        // http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=3624
        // stage.dispose();

        // as the collaborators are lazily loaded, they may be null
        if( font != null ) font.dispose();
        if( batch != null ) batch.dispose();
        if( skin != null ) skin.dispose();
        if( atlas != null ) atlas.dispose();
	}

}
