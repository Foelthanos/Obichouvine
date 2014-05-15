package s6.prog6.obichouvine.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.MusicManager.ObiMusic;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.utils.DefaultInputListener;

public class MenuScreen extends AbstractScreen {

	public MenuScreen(ObichouvineGame game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show()
	{
		super.show();
		// start playing the menu music
		if( ObichouvineGame.DEV_MODE )
			game.getMusicManager().play( ObiMusic.MENU );
		// retrieve the default table actor
		Table table = super.getTable();
		table.add( "Obichouvine "+ObichouvineGame.VER ).spaceBottom( 50 );
		table.row();

		// register the button "new game"
		TextButton startGameButton = new TextButton( "Nouvelle partie", this.getSkin() );
		startGameButton.addListener( new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp( event, x, y, pointer, button );
				game.getSoundManager().play( ObiSound.CLICK );
				//game.setScreen( new StartGameScreen( game ) );
			}
		} );
		table.add( startGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
		table.row();

		// register the button "options"
		TextButton optionsButton = new TextButton( "Options", getSkin() );
		optionsButton.addListener( new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp( event, x, y, pointer, button );
				game.getSoundManager().play( ObiSound.CLICK );
				//game.setScreen( new OptionsScreen( game ) );
			}
		} );
		table.add( optionsButton ).uniform().fill().spaceBottom( 10 );
		table.row();

		// register the button "quitter"
		TextButton highScoresButton = new TextButton( "Quitter", getSkin() );
		highScoresButton.addListener( new DefaultInputListener() {
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
		table.add( highScoresButton ).uniform().fill();
	}

}
