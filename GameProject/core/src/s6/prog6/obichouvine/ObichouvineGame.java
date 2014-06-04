package s6.prog6.obichouvine;

import s6.prog6.obichouvine.controllers.LevelManager;
import s6.prog6.obichouvine.controllers.MusicManager;
import s6.prog6.obichouvine.controllers.PreferencesManager;
import s6.prog6.obichouvine.controllers.SoundManager;
import s6.prog6.obichouvine.models.Parameter;
import s6.prog6.obichouvine.models.Player;
import s6.prog6.obichouvine.screens.GameScreen;
import s6.prog6.obichouvine.screens.MenuScreen;
import s6.prog6.obichouvine.screens.OptionsScreen;
import s6.prog6.obichouvine.screens.RulesScreen;
import s6.prog6.obichouvine.screens.SplashScreen;
import s6.prog6.obichouvine.screens.StartLocalGameScreen;
import s6.prog6.obichouvine.screens.StartOnlineGameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;

public class ObichouvineGame extends Game {
	private FPSLogger fps;

	public static final String LOG = ObichouvineGame.class.getSimpleName();
	public static final boolean DEV_MODE = true;
	public static final String VER = "v0.2.2";

	private PreferencesManager preferencesManager;
	private LevelManager levelManager;
	private MusicManager musicManager;
	private SoundManager soundManager;

	public PreferencesManager getPreferencesManager()
	{
		return preferencesManager;
	}

	public LevelManager getLevelManager()
	{
		return levelManager;
	}

	public MusicManager getMusicManager()
	{
		return musicManager;
	}
	
	public SoundManager getSoundManager()
    {
        return soundManager;
    }
	
	// Screen methods

	public RulesScreen getRulesScreen()
    {
        return new RulesScreen(this);
    }
	
    public SplashScreen getSplashScreen()
    {
        return new SplashScreen(this);
    }
    
    public OptionsScreen getOptionsScreen()
    {
        return new OptionsScreen(this);
    }
    
    public MenuScreen getMenuScreen()
    {
        return new MenuScreen(this);
    }

    public StartLocalGameScreen getStartLocalGameScreen()
    {
        return new StartLocalGameScreen(this);
    }
    
    public StartOnlineGameScreen getStartOnlineGameScreen()
    {
        return new StartOnlineGameScreen(this);
    }
    
    public GameScreen getGameScreen(Parameter param, Player p1, Player p2)
    {
        return new GameScreen(this, param, p1, p2);
    }
    
	@Override
	public void create () {
		Gdx.app.log(ObichouvineGame.LOG, "Creating game on " + Gdx.app.getType());
		
		// create the preferences manager
		preferencesManager = new PreferencesManager();
		
		// create the music manager
		musicManager = new MusicManager();
		musicManager.setVolume( preferencesManager.getVolume());
		musicManager.setEnabled( preferencesManager.isMusicEnabled());

		// create the sound manager
        soundManager = new SoundManager();
        soundManager.setVolume( preferencesManager.getVolume());
        soundManager.setEnabled( preferencesManager.isSoundEnabled());

		// create the level manager
		levelManager = new LevelManager();

		fps = new FPSLogger();	
	}


	@Override
	public void dispose() {
		super.dispose();
		// You can use this function to print stuff to the console. 
		// It's very useful to use to track what's happening in your game.
		Gdx.app.log(ObichouvineGame.LOG, "Disposed");
	}

	@Override
	public void resize(
			int width,
			int height )
	{
		super.resize( width, height );
		Gdx.app.log( ObichouvineGame.LOG, "Resizing game to: " + width + " x " + height );

		// show the splash screen when the game is resized for the first time;
		// this approach avoids calling the screen's resize method repeatedly
		if( getScreen() == null ) {
			if( DEV_MODE ) {
				setScreen( new MenuScreen( this ) );
			} else {
				setScreen( new SplashScreen( this ) );
			}
		}
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
		super.setScreen(screen);
		Gdx.app.log( ObichouvineGame.LOG, "Setting screen: " + screen.getClass().getSimpleName() );
	}


	public void pause(){
		super.pause();
		Gdx.app.log( ObichouvineGame.LOG, "Pausing game" );

	}

}
