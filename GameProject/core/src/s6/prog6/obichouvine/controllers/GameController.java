package s6.prog6.obichouvine.controllers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import s6.prog6.obichouvine.models.Block;
import s6.prog6.obichouvine.models.Board;
import s6.prog6.obichouvine.models.Move;
import s6.prog6.obichouvine.models.Pawn;
import s6.prog6.obichouvine.models.Block.BlockState;
import s6.prog6.obichouvine.models.Pawn.PawnType;

public class GameController {
	private Board board;

	private Vector2 cursorPos;
	private int button;

	private Block selectedPawn;

	private PawnType turn;

	enum Keys {
		CLICK
	}

	static Map<Keys, Boolean> keys = new HashMap<GameController.Keys, Boolean>();
	static {
		keys.put(Keys.CLICK, false);
	};

	public GameController(Board board, PawnType turn){
		this.board = board;
		this.turn = turn;
	}

	public void update(float delta) {
		processInput();
	}

	public void clickReleased(Vector2 cursorPos, int button) {
		this.button = button;
		this.cursorPos = cursorPos;
		this.refactorCursorPos();
		keys.get(keys.put(Keys.CLICK, true));
		System.out.println("RELEASED : "+cursorPos);

	}

	public void clickPressed(Vector2 cursorPos, int button) {
		this.button = button;
		this.cursorPos = cursorPos;
		this.refactorCursorPos();
		keys.get(keys.put(Keys.CLICK, false));
	}

	private void processInput() {
		// TODO Auto-generated method stub
		if(keys.get(Keys.CLICK)){
			if(this.selectedPawn==null){
				System.out.println("Test");
				if(this.board.board[(int)cursorPos.x][(int)cursorPos.y].getPawn().getType() != PawnType.VIDE &&
						this.board.board[(int)cursorPos.x][(int)cursorPos.y].getPawn().getType() == turn){
					System.out.println("Selected ["+cursorPos.x+","+cursorPos.y+"]");
					this.selectedPawn = this.board.board[(int)cursorPos.x][(int)cursorPos.y];
					this.board.highlightMoves((int)cursorPos.x, (int)cursorPos.y, true);
				}
			}
			else{
				int xStart = (int) ((this.selectedPawn.getPosition().x- board.offsetX)/Block.SIZE);
				int yStart = (int) ((this.selectedPawn.getPosition().y- board.offsetY)/Block.SIZE);
				board.deplacement(new Move(xStart,
						yStart,
						(int)cursorPos.x, 
						(int)cursorPos.y));
				this.selectedPawn = null;
				this.board.highlightMoves((int)cursorPos.x, (int)cursorPos.y, false);
				this.switchTurn();
			}
			keys.get(keys.put(Keys.CLICK, false));
		}
	}

	
	private void switchTurn() {
		// TODO Auto-generated method stub
		turn = (turn==PawnType.MOSCOVITE)? PawnType.SUEDOIS : PawnType.MOSCOVITE;
	}

	private void refactorCursorPos(){
		if(cursorPos.x < board.offsetX)
			cursorPos.x = 0;
		else if(cursorPos.x > board.offsetX + (board.xBoard)*Block.SIZE)
			cursorPos.x = board.xBoard-1;//(board.offsetX + (board.xBoard - 1)*Block.SIZE);
		else
			cursorPos.x = (int)((cursorPos.x - board.offsetX)/Block.SIZE);

		if(cursorPos.y < board.offsetY)
			cursorPos.y = board.yBoard-1;//board.offsetY;
		else if(cursorPos.y > board.offsetY + (board.yBoard)*Block.SIZE)
			cursorPos.y = 0;//board.offsetY + (board.yBoard)*Block.SIZE;
		else
			cursorPos.y = (int)(-((cursorPos.y - board.offsetY)/Block.SIZE)+(board.yBoard));

	}	
}
