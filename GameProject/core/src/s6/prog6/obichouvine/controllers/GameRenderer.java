package s6.prog6.obichouvine.controllers;

import s6.prog6.obichouvine.models.Block;
import s6.prog6.obichouvine.models.Board;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Pawn.TypeSuedois;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameRenderer {

	private TextureRegion normalBlock;
	private TextureRegion escapeBlock;
	private TextureRegion escapeRussianBlock;
	private TextureRegion throneBlock;
	private TextureRegion moscovitBlock;

	private TextureRegion normalBlockHighlight;
	private TextureRegion escapeBlockHighlight;
	private TextureRegion escapeRussianBlockHighlight;
	private TextureRegion throneBlockHighlight;
	private TextureRegion moscovitBlockHighlight;

	private TextureAtlas atlas;

	private SpriteBatch spriteBatch;

	private BitmapFont font;

	private TextureRegion moscoPawn;
	private TextureRegion vikingSoldier;
	private TextureRegion viKing;
	private TextureRegion moscoPawnSelect;
	private TextureRegion vikingSoldierSelect;
	private TextureRegion viKingSelect;
	private TextureRegion moscoPawnOldPos;
	private TextureRegion vikingSoldierOldPos;
	private TextureRegion viKingOldPos;

	private int ppuX, ppuY;

	private Board board;

	private char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M'};
	private char[] number = {'1','2','3','4','5','6','7','8','9','0','1','1'};

	public GameRenderer(Board b){
		this.board = b;
		this.spriteBatch = new SpriteBatch();

		font = new BitmapFont();
		loadTextures();
		ppuX = 1;
		ppuY = 1;
	}

	public void loadTextures(){
		atlas = new TextureAtlas(Gdx.files.internal("images-atlases/pages.atlas"));

		normalBlock = atlas.findRegion("normalBlock");
		escapeBlock = atlas.findRegion("escapeBlock");
		throneBlock = atlas.findRegion("vikingBlock");
		moscovitBlock = atlas.findRegion("russianBlock");
		escapeRussianBlock = atlas.findRegion("escapeRussianBlock");

		normalBlockHighlight = atlas.findRegion("normalBlockHighlight");
		escapeBlockHighlight = atlas.findRegion("escapeBlockHighlight");
		throneBlockHighlight = atlas.findRegion("vikingBlockHighlight");
		moscovitBlockHighlight = atlas.findRegion("russianBlockHighlight");
		escapeRussianBlockHighlight = atlas.findRegion("escapeRussianBlockHightlight");

		moscoPawn = atlas.findRegion("Moscovit");
		moscoPawnSelect = atlas.findRegion("MoscovitSelect");
		moscoPawnOldPos = atlas.findRegion("MoscovitOldPos");
		vikingSoldier = atlas.findRegion("Suedois");
		vikingSoldierSelect = atlas.findRegion("SuedoisSelect");
		vikingSoldierOldPos = atlas.findRegion("SuedoisOldPos");
		viKing = atlas.findRegion("Roi");
		viKingSelect = atlas.findRegion("RoiSelect");
		viKingOldPos = atlas.findRegion("RoiOldPos");
	}

	public void render() {
		spriteBatch.begin();
		this.drawGrid();
		this.drawBlocks();
		spriteBatch.end();
	}

	private void drawGrid(){
		for(int i = 0; i<board.getxBoard();i++){
			font.draw(spriteBatch, alphabet[i]+"", board.getOffsetX()+(Block.SIZE*i)+(Block.SIZE/2)-5, 
					board.getOffsetY()+(board.getyBoard()*Block.SIZE)+(Block.SIZE/2));
		}

		for(int i = 0; i<board.getyBoard();i++){
			font.draw(spriteBatch, number[i]+"", board.getOffsetX()-board.getxBoard(), 
					board.getOffsetY()+(Block.SIZE*i)+(Block.SIZE/2)+10);
		}
	}

	private void drawBlocks() {
		for (Block block : board.getBlocks()) {
			if(block.getState()==Block.BlockState.TRONE)
				spriteBatch.draw((!block.isSurbrillance())?throneBlock:throneBlockHighlight, 
						block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else if(block.getState()==Block.BlockState.FORTERESSE)
				spriteBatch.draw((!block.isSurbrillance())?escapeBlock:escapeBlockHighlight, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else if(block.getState()==Block.BlockState.ROUGE)
				spriteBatch.draw((!block.isSurbrillance())?moscovitBlock:moscovitBlockHighlight, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else if(block.getState()==Block.BlockState.ROUGEEXIT)
				spriteBatch.draw((!block.isSurbrillance())?escapeRussianBlock:escapeRussianBlockHighlight, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else if(block.getState()==Block.BlockState.BLANC)
				spriteBatch.draw((!block.isSurbrillance())?normalBlock:normalBlockHighlight, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else if(block.getState()==Block.BlockState.BLANCEXIT)
				spriteBatch.draw((!block.isSurbrillance())?escapeBlock:escapeBlockHighlight, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);

			if(block.getPawn().getType()==PawnType.MOSCOVITE)
				spriteBatch.draw((!block.getPawn().getSurbri())?(!block.getPawn().getSpirit())?moscoPawn:moscoPawnOldPos:moscoPawnSelect, 
						block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else if(block.getPawn().getType()==PawnType.SUEDOIS)
				if(block.getPawn().getTypesuede()==TypeSuedois.KING)
					spriteBatch.draw((!block.getPawn().getSurbri())?(!block.getPawn().getSpirit())?viKing:viKingOldPos:viKingSelect, 
							block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
				else if(block.getPawn().getTypesuede()==TypeSuedois.PION)
					spriteBatch.draw((!block.getPawn().getSurbri())?(!block.getPawn().getSpirit())?vikingSoldier:vikingSoldierOldPos:vikingSoldierSelect, 
							block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
		}
	}

	public void dispose() {
		// TODO Auto-generated method stub
		if( font != null ) font.dispose();
		if( spriteBatch != null ) spriteBatch.dispose();
		if( atlas != null ) atlas.dispose();

	}

}
