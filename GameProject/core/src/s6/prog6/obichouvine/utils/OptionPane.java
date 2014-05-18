package s6.prog6.obichouvine.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class OptionPane extends Table{

	@SuppressWarnings("unused")
	private Content content;
	
	public enum Content{
		IAvIA, PvsIA, PvsP
	}
	public OptionPane(Content content, Skin skin){
		super(skin);
		this.content = content;

		this.add("Parametres de la partie : test").spaceBottom(50).spaceLeft(30);
		this.top().left();
		this.row();
	}
	
}
