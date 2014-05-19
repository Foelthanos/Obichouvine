package s6.prog6.obichouvine;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import s6.prog6.obichouvine.controllers.GameController;
import s6.prog6.obichouvine.controllers.GameRenderer;
import s6.prog6.obichouvine.screens.AbstractScreen;

public class GameScreen extends AbstractScreen{
	GameController gController;
	GameRenderer gRenderer;
	
	public GameScreen(ObichouvineGame game) {
		super(game);
		// TODO Auto-generated constructor stub
		this.gController = new GameController();
		this.gRenderer = new GameRenderer();
	}

	public void show(){
		super.show();
		
		Table table = super.getTable();
		table.add("Joueur 1 : Joueur 2").spaceBottom( 50 ).colspan(4);
		table.row();
		
		
	}
	
}
