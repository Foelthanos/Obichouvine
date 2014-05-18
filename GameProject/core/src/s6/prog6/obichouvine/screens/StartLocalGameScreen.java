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
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class StartLocalGameScreen extends AbstractScreen
{

	private final int buttonW = 100;
	private final int buttonH = 40;

	private SplitPane pane;

	private Table table;
	private TextButton spectatorModeButton;	
	private TextButton soloModeButton;
	private TextButton twoPModeButton;
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

		Table t1 = new Table(getSkin());
		t1.add("Partie en local").spaceBottom(50).spaceLeft(30);
		t1.top().left();
		t1.row();
		
		TextButton specModeButton = new TextButton( "IA vs IA", getSkin() );
		specModeButton.addListener( new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp( event, x, y, pointer, button );
				game.getSoundManager().play(ObiSound.CLICK);
				//Gdx.app.exit();
			}
		} );
		t1.add(specModeButton).uniform().expand().spaceBottom(10);
		t1.row();
		
		TextButton soloModeButton = new TextButton( "Joueur vs IA", getSkin() );
		soloModeButton.addListener( new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp( event, x, y, pointer, button );
				game.getSoundManager().play(ObiSound.CLICK);
				//Gdx.app.exit();
			}
		} );
		t1.add(soloModeButton).uniform().expand().spaceBottom(10).center();
		t1.row();
		
		TextButton multiModeButton = new TextButton( "Joueur vs Joueur", getSkin() );
		multiModeButton.addListener( new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp( event, x, y, pointer, button );
				game.getSoundManager().play(ObiSound.CLICK);
				//Gdx.app.exit();
			}
		} );
		t1.add(multiModeButton).uniform().expand().spaceBottom(100).center();
		t1.row();
		
		TextButton back = new TextButton( "Retour", getSkin() );
		back.addListener( new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp( event, x, y, pointer, button );
				game.getSoundManager().play(ObiSound.CLICK);
				game.setScreen(game.getMenuScreen());
			}
		} );
		t1.add(back).uniform().fill().spaceBottom(80);
		t1.row();
		
		Table t2 = new Table(getSkin());
		t2.add("Partie en local 2").spaceBottom(50).spaceLeft(30);
		t2.top().left();
		t2.row();
		
		pane = new SplitPane(t1, t2, false, getSkin());
		pane.setSize(GAME_VIEWPORT_WIDTH, GAME_VIEWPORT_HEIGHT);
		pane.setMinSplitAmount((float) 0.2);
		pane.setMaxSplitAmount((float) 0.2001);
		pane.setSplitAmount((float) 0.2);
		stage.addActor(pane);
		
		// retrieve the default table actor
		/*table = super.getTable();
		table.left().top();
		table.setBounds(0, 0, Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
		table.add("Partie en local").spaceBottom(50).spaceLeft(30);

		table.add(new Table(getSkin()));
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
		table.add(spectatorModeButton).size(this.buttonW, this.buttonH).uniform().spaceBottom(10);
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
		table.add(soloModeButton).size(this.buttonW, this.buttonH).uniform().spaceBottom(10);
		table.row();

		// register the button "new game"
		twoPModeButton = new TextButton("Joueur vs Joueur", this.getSkin());
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
		table.add(twoPModeButton).size(this.buttonW, this.buttonH).uniform().spaceBottom(10);
		table.row();*/


	}

	public void updateSubScreen(){

	}

}