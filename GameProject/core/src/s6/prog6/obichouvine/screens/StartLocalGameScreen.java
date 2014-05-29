package s6.prog6.obichouvine.screens;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.models.Player;
import s6.prog6.obichouvine.utils.DefaultInputListener;
import s6.prog6.obichouvine.utils.OptionPane;
import s6.prog6.obichouvine.utils.PlayerSelection;

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
		if( ObichouvineGame.DEV_MODE )
			mainTable.debug();
		
		p1 = new PlayerSelection(game.getPreferencesManager().getPseudo(), "Moscovites", this.getSkin());
		p2 = new PlayerSelection("Hank Bot", "Suedois", this.getSkin());
		
		mainTable.add("Partie locale");
		mainTable.top();
		mainTable.row();
		mainTable.add(p1).fillX().expandY();
		mainTable.row();
		mainTable.add(p2).fillX().expandY();
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
		buttonTable.add(back).fillY().expand();

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
				game.setScreen(game.getGameScreen(oPane.generateParameter(), new Player(p1.getPseudo()), new Player(p2.getPseudo())));
			}
		} );
		buttonTable.add(validate).fillY().expand().uniform();



		paramPane = new SplitPane(mainTable, oPane, false, getSkin());

		paramPane.setMinSplitAmount((float) 0.2);
		paramPane.setMaxSplitAmount((float) 0.2001);
		paramPane.setSplitAmount((float) 0.2);

		mainPane = new SplitPane(paramPane, buttonTable, true, getSkin());
		mainPane.setSize(GAME_VIEWPORT_WIDTH, GAME_VIEWPORT_HEIGHT);
		mainPane.setMinSplitAmount((float) 0.9);
		mainPane.setMaxSplitAmount((float) 0.9001);
		mainPane.setSplitAmount((float) 0.9);
		stage.addActor(mainPane);
	}

}