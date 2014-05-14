package s6.prog6.obichouvine;

import s6.prog6.obichouvine.screens.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ObichouvineGame extends Game {
	SpriteBatch batch;
	private FPSLogger fps;
	
	public static final String LOG = ObichouvineGame.class.getSimpleName();
	public static final boolean DEV_MODE = true;
	public static final String VER = "v0.0.2";
	
	@Override
	public void create () {
		Gdx.app.log(ObichouvineGame.LOG, "Created");
		fps = new FPSLogger();
		batch = new SpriteBatch();
		setScreen(new SplashScreen(this));
	}

	public SpriteBatch getBatch() { return batch; }
	
	@Override
	public void dispose() {
		// remember dispose the current screen
		getScreen().dispose();
		
		batch.dispose();
		super.dispose();
		
		// You can use this function to print stuff to the console. 
		// It's very useful to use to track what's happening in your game.
		Gdx.app.log(ObichouvineGame.LOG, "Disposed");
	}
	
	@Override
	public void render () {
		super.render();
		if( DEV_MODE ) fps.log();
	}
	
	@Override
    public void resume(){
        super.resume();
        Gdx.app.log( ObichouvineGame.LOG, "Resuming game" );
    }
	@Override
    public void setScreen(Screen screen ){
        super.setScreen( screen );
        Gdx.app.log( ObichouvineGame.LOG, "Setting screen: " + screen.getClass().getSimpleName() );
    }

	
	public void pause(){
        super.pause();
        Gdx.app.log( ObichouvineGame.LOG, "Pausing game" );

    }

}
