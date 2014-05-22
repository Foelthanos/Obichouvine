package s6.prog6.obichouvine.controllers;

import s6.prog6.obichouvine.models.Block;
import s6.prog6.obichouvine.models.Board;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Pawn.TypeSuedois;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameRenderer {

	private TextureRegion normalBlock;
	private TextureRegion escapeBlock;
	private TextureRegion throneBlock;

	private SpriteBatch spriteBatch;

	private TextureRegion moscoPawn;
	private TextureRegion vikingSoldier;
	private TextureRegion viKing;

	private int ppuX, ppuY;

	private Board board;

	public GameRenderer(Board b){
		this.board = b;
		spriteBatch = new SpriteBatch();
		loadTextures();

		ppuX = 1;
		ppuY = 1;
	}

	public void loadTextures(){
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images-atlases/pages.atlas"));
		normalBlock = atlas.findRegion("Standard-case");
		escapeBlock = atlas.findRegion("Escape-case");
		throneBlock = atlas.findRegion("Throne-case");
		moscoPawn = atlas.findRegion("Moscovit");
		vikingSoldier = atlas.findRegion("Suedois");
		viKing = atlas.findRegion("Roi");
	}

	public void render() {
		spriteBatch.begin();
		this.drawBlocks();
		spriteBatch.end();
	}

	private void drawBlocks() {
		for (Block block : board.getBlocks()) {
			if(block.getState()==Block.BlockState.TRONE)
				spriteBatch.draw(throneBlock, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else if(block.getState()==Block.BlockState.ROUGE)
				spriteBatch.draw(escapeBlock, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else
				spriteBatch.draw(normalBlock, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			
			if(block.getPion().getType()==PawnType.MOSCOVITE)
				spriteBatch.draw(moscoPawn, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else if(block.getPion().getType()==PawnType.SUEDOIS)
				if(block.getPion().getTypesuede()==TypeSuedois.KING)
					spriteBatch.draw(viKing, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
				else if(block.getPion().getTypesuede()==TypeSuedois.PION)
					spriteBatch.draw(vikingSoldier, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
		}
	}

}
