package s6.prog6.obichouvine.utils;

import s6.prog6.obichouvine.models.Player;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameStatusWidget extends Table{

	private Label p1Pseudo, p1Status, p2Pseudo, p2Status;
	
	public GameStatusWidget(Skin skin, Player p1, Player p2){
		super(skin);
		
		p1Pseudo = new Label(p1.getPseudo(), skin);
		p2Pseudo = new Label(p2.getPseudo(), skin);
		
		p1Status = new Label("16", skin);
		p2Status = new Label("9", skin);
		
		this.add(p1Status).left().expandX().fill();
		this.row();
		this.add(p1Pseudo).left().expandX().fill();
		this.row();
		this.add(p2Pseudo).left().expandX().fill();
		this.row();
		this.add(p2Status).left().expandX().fill();
		this.row();
	}
	
}
