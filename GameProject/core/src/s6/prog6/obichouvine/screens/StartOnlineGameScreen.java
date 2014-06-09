package s6.prog6.obichouvine.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.utils.DefaultInputListener;

public class StartOnlineGameScreen extends AbstractScreen {

	private Table table;
	private TextField pseudoField;
	private TextField IpField;
	
	private TextButton validateButton;
	private TextButton backButton;
	
	public StartOnlineGameScreen(ObichouvineGame game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show()
	{
		super.show();
		
		this.table = this.getTable();
		this.table.add("Partie en reseau").spaceBottom(50).spaceLeft(30).colspan(2);
		this.table.row();
		
		this.table.add("Pseudonyme :").spaceBottom(30).expand();
		pseudoField = new TextField("", getSkin());
		table.add(pseudoField).uniform().spaceBottom(30).expand();
		table.row();
		
		this.table.add("Adresse IP du serveur :");
		IpField = new TextField("", getSkin());
		table.add(IpField).uniform().expand();
		table.row();
		
		
		backButton = new TextButton("Retour", getSkin());
		backButton.addListener(new DefaultInputListener() {
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
		validateButton = new TextButton("Valider", getSkin());
		validateButton.addListener(new DefaultInputListener() {
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
			}
		} );
		table.add(backButton).uniform().expand().spaceBottom(10).size(250, 60);
		table.add(validateButton).uniform().expand().spaceBottom(10).size(250, 60);
		
		table.row();
	}

}
