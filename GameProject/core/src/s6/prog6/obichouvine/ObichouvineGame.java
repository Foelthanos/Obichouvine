package s6.prog6.obichouvine;

import s6.prog6.obichouvine.screens.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ObichouvineGame extends Game {
	SpriteBatch batch;
	private FPSLogger fps;
	
	public static final String LOG = ObichouvineGame.class.getSimpleName();
	public static final boolean DEV_MODE = true;
	
	@Override
	public void create () {
		Gdx.app.log(ObichouvineGame.LOG, "Created");
		fps = new FPSLogger();
		batch = new SpriteBatch();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		
	}
}
