package s6.prog6.obichouvine.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.utils.DefaultInputListener;

public class RulesScreen extends AbstractScreen {

	private int index = 0;
	
	private ArrayList<TextureRegion> rules = new ArrayList<TextureRegion>();
	
	private Image rule;

	private TextButton backButton, rightB, leftB;

	private Table table;
	public RulesScreen(ObichouvineGame game) {
		super(game);
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images-atlases/pages.atlas"));
		rules.add(new TextureRegion(atlas.findRegion("rules1")));
		rules.add(new TextureRegion(atlas.findRegion("rules2")));
		rules.add(new TextureRegion(atlas.findRegion("rules3")));
		rules.add(new TextureRegion(atlas.findRegion("rules5")));
		rules.add(new TextureRegion(atlas.findRegion("rules6")));
		rules.add(new TextureRegion(atlas.findRegion("rules7")));
		rules.add(new TextureRegion(atlas.findRegion("rules8")));
		
		rule = new Image(rules.get(index));
		
		backButton = new TextButton( "Retour au menu principal", getSkin() );
		rightB = new TextButton( "Suivant", getSkin() );
		leftB = new TextButton( "Précédent", getSkin() );
	}
	
	public void loadWidget(){
		leftB.addListener( new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp( event, x, y, pointer, button );
				index --;
				if(index<0)
					index = 0;
				rule.setDrawable(new TextureRegionDrawable(rules.get(index)));
				updateWidgets();
			}
		} );
		rightB.addListener( new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp( event, x, y, pointer, button );
				index ++;
				if(index>6)
					index = 6;
				rule.setDrawable(new TextureRegionDrawable(rules.get(index)));
				updateWidgets();
				
			}
		} );
		backButton.addListener( new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp( event, x, y, pointer, button );
				game.getSoundManager().play( ObiSound.CLICK );
				game.setScreen( new MenuScreen( game ) );
			}
		} );
	}
	
	public void printRule(){
		table.clear();
		
		table.add(leftB).size(60,60);

		
		table.add(rule);
		

		table.add(rightB).size(60,60);
		
		table.row();

		
		table.add(backButton).size(250,60).colspan(3).padTop(-50);
	}

	public void updateWidgets(){
		if(index==0){
			leftB.setDisabled(true);
			leftB.setText("");
		}
		else{
			leftB.setDisabled(false);
			leftB.setText("Précédent");
		}
		if(index==6){
			rightB.setDisabled(true);
			rightB.setText("");
		}
		else{
			rightB.setDisabled(false);
			rightB.setText("Suivant");
		}
	}
	
	@Override
	public void show()
	{
		super.show();

		// retrieve the default table actor
		this.table = super.getTable();
		
		this.loadWidget();
		this.printRule();
		this.updateWidgets();
	}


}
