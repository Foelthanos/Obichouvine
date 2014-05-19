package s6.prog6.obichouvine.screens;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.utils.DefaultInputListener;
import s6.prog6.obichouvine.utils.OptionPane;
import s6.prog6.obichouvine.utils.OptionPane.Content;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class StartLocalGameScreen extends AbstractScreen
{

	private final int buttonW = 150;
	//private final int buttonH = 40;

	private SplitPane pane;

	private Table t1;
	private OptionPane oPane;
	
	private TextButton specModeButton;	
	private TextButton soloModeButton;
	private TextButton multiModeButton;


	public StartLocalGameScreen(ObichouvineGame game)
	{
		super(game);
	}

	@Override
	public void show()
	{
		super.show();
		oPane = new OptionPane(Content.PvsIA, this.getSkin(), this.game);
		t1 = new Table(getSkin());
		t1.add("Partie en local").spaceBottom(50).spaceLeft(30);
		t1.top().left();
		t1.row();

		specModeButton = new TextButton("IA vs IA", getSkin());
		specModeButton.addListener(new DefaultInputListener() {
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
				oPane.updateContent(Content.IAvsIA);
			}
		} );
		t1.add(specModeButton).uniform().expand().spaceBottom(10).width(buttonW);
		t1.row();

		soloModeButton = new TextButton("Joueur vs IA", getSkin());
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
				oPane.updateContent(Content.PvsIA);
			}
		} );
		t1.add(soloModeButton).uniform().expand().spaceBottom(10).width(buttonW);
		t1.row();

		multiModeButton = new TextButton("Joueur vs Joueur", getSkin());
		multiModeButton.addListener(new DefaultInputListener() {
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
				oPane.updateContent(Content.PvsP);
			}
		} );
		t1.add(multiModeButton).uniform().expand().spaceBottom(100).width(buttonW);
		t1.row();

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
		t1.add(back).uniform().fill().spaceBottom(80);
		t1.row();

		

		pane = new SplitPane(t1, oPane, false, getSkin());
		pane.setSize(GAME_VIEWPORT_WIDTH, GAME_VIEWPORT_HEIGHT);
		pane.setMinSplitAmount((float) 0.2);
		pane.setMaxSplitAmount((float) 0.2001);
		pane.setSplitAmount((float) 0.2);
		stage.addActor(pane);
	}
 
}