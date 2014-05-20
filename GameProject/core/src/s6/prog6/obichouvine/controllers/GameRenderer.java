package s6.prog6.obichouvine.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameRenderer {

	private TextureRegion normalBlock;
	private TextureRegion escapeBlock;
	private TextureRegion throneBlock;
	
	private SpriteBatch spriteBatch;
	
	private int ppuX, ppuY;
	
	public GameRenderer(){
		
	}
	
	public void loadTextures(){
		
	}
	
	public void render() {
		spriteBatch.begin();
		
		spriteBatch.end();
	}
	
}
