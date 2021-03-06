package s6.prog6.obichouvine.screens;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.models.Player;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.utils.DefaultInputListener;
import s6.prog6.obichouvine.utils.OptionPane;
import s6.prog6.obichouvine.utils.PlayerSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class StartLocalGameScreen extends AbstractScreen
{
	private SplitPane paramPane, mainPane;
	
	private Table mainTable, buttonTable;
	private OptionPane oPane;

	private PlayerSelection p1,p2;


	public StartLocalGameScreen(ObichouvineGame game)
	{
		super(game);
	}

	@Override
	public void show()
	{
		super.show();
		oPane = new OptionPane(this.getSkin());
		mainTable = new Table(getSkin());
		if(ObichouvineGame.DEV_MODE)
			mainTable.debug();
		
		p1 = new PlayerSelection(game.getPreferencesManager().getPseudo(), "Moscovites", this.getSkin(), false);
		p2 = new PlayerSelection(game.getPreferencesManager().getPseudoP2(), "Vikings", this.getSkin(), true);
		
		mainTable.add("Paramètres des joueurs");
		mainTable.top();
		mainTable.row();
		mainTable.add(p1).fillX().expand();
		mainTable.row();
		mainTable.add(p2).fillX().expand();
		mainTable.row();


		this.buttonTable = new Table();
		if( ObichouvineGame.DEV_MODE ) {
			buttonTable.debug();
		}
		TextButton back = new TextButton("Retour", getSkin());
		back.addListener(new DefaultInputListener() {
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
				game.setScreen(game.getMenuScreen());
			}
		} );
		buttonTable.add(back).height(60).expand().width(250);

		TextButton validate = new TextButton("Jouer", getSkin());
		validate.addListener(new DefaultInputListener() {
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
				game.setScreen(game.getGameScreen(oPane.generateParameter(), 
						p1.getPlayerParameters(), 
						p2.getPlayerParameters()));
			}
		} );
		buttonTable.add(validate).height(60).expand().width(250);
		
		paramPane = new SplitPane(mainTable, oPane, false, getSkin());

		paramPane.setMinSplitAmount((float) 0.25);
		paramPane.setMaxSplitAmount((float) 0.2501);
		paramPane.setSplitAmount((float) 0.25);

		mainPane = new SplitPane(paramPane, buttonTable, true, getSkin());
		mainPane.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mainPane.setMinSplitAmount((float) 0.87);
		mainPane.setMaxSplitAmount((float) 0.8701);
		mainPane.setSplitAmount((float) 0.87);
		stage.addActor(mainPane);
	}

}