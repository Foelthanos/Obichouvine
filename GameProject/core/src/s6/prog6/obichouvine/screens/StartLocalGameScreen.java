package s6.prog6.obichouvine.screens;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.utils.DefaultInputListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class StartLocalGameScreen extends AbstractScreen
{

	private Table table;
	private TextButton spectatorModeButton;	
	private TextButton soloModeButton;
	private TextButton episode3Button;
	private SelectBox teamSelectBox;
	private SelectBox frontGunSelectBox;
	private SelectBox shieldSelectBox;
	private Label creditsLabel;

	private Image pawnTeam;
	private Image frontGunImage;
	private Image shieldImage;


	public StartLocalGameScreen(ObichouvineGame game)
	{
		super(game);
	}

	@Override
	public void show()
	{
		super.show();

		// retrieve the default table actor
		table = super.getTable();
		table.left().top();
		table.add("Partie en local").spaceBottom(50);
		table.row();

		// register the button "new game"
		spectatorModeButton = new TextButton("IA vs IA", this.getSkin());
		spectatorModeButton.addListener( new DefaultInputListener() {
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
				game.getStartLocalGameScreen().updateSubScreen();
			}
		} );
		table.add(spectatorModeButton).size(300, 60).uniform().spaceBottom(10);
		table.row();

		// register the button "new game"
		soloModeButton = new TextButton("Joueur vs IA", this.getSkin());
		soloModeButton.addListener(new DefaultInputListener() {
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
				game.getStartLocalGameScreen().updateSubScreen();
			}
		} );
		table.add(soloModeButton).size(300, 60).uniform().spaceBottom(10);
		table.row();

		// register the button "new game"
		TextButton twoPModeButton = new TextButton("Joueur vs Joueur", this.getSkin());
		twoPModeButton.addListener(new DefaultInputListener() {
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
				game.getStartLocalGameScreen().updateSubScreen();
			}
		} );
		table.add(twoPModeButton).size(300, 60).uniform().spaceBottom(10);
		table.row();


	}

	public void updateSubScreen(){

	}

}