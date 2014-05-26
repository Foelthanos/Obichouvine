package s6.prog6.obichouvine.utils;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

public class PlayerSelection extends VerticalGroup{

	Label name, botL, diffL, pseudoL;
	CheckBox isBot;
	SelectBox<String> difficulty;
	TextField pseudo;
}
