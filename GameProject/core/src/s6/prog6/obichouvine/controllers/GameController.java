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
import s6.prog6.obichouvine.models.Player;
import s6.prog6.obichouvine.models.ia.AlphaBeta;
import s6.prog6.obichouvine.models.ia.IA;

public class GameController {
	private Board board;

	private Move moves[];

	private Vector2 cursorPos;
	private int button;
	private int resMove;

	public int mosc,vik;

	private Block selectedPawn;

	public boolean gameEnded;

	public boolean gameStarted = false;
	public PawnType turn;
	public float turnNum;

	private boolean isIATurn = false;
	private Player p1, p2;
	enum Keys {
		CLICK
	}

	static Map<Keys, Boolean> keys = new HashMap<GameController.Keys, Boolean>();
	static {
		keys.put(Keys.CLICK, false);
	};
	
	public GameController(Board board, PawnType turn, Player p1, Player p2){
		this.board = board;
		this.turn = turn;
		this.p1 = p1;
		this.p2 = p2;

		this.gameEnded = false;
		
		this.mosc = 16;
		this.vik = 9;
		System.out.println(p1.getTeam());
		System.out.println(p2.getTeam());
		System.out.println(turn);
		this.isIATurn = this.nextTurnIa();
		this.turnNum = (float) 1;

	}

	public Move update(float delta) {
		return (gameStarted)?processInput():null;
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

	private void processStatus(){
		if(this.board.eaten)
			if(this.turn == PawnType.SUEDOIS)
				mosc = mosc -1;
			else
				vik = vik -1;
		this.board.eaten = false;
	}

	private Move processInput() {
		// TODO Auto-generated method stub
		Move res = null;
		if(this.gameEnded){
			return null;
		}
		if(this.isIATurn){
			/*try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			if(turn.equals(p1.getTeam()) && p1 instanceof IA){
				AlphaBeta ia = (AlphaBeta)p1;
				res = ia.jouer(board);
			}
			else if(turn.equals(p2.getTeam()) && p2 instanceof IA){
				AlphaBeta ia = (AlphaBeta)p2;
				res = ia.jouer(board); 
			}
			res.setTurn(this.turn);
			res.setTurnNum((int)this.turnNum);
			resMove = this.board.deplacement(res);
			if(resMove == 3 ){
				System.out.println("Ca marche !!");
			}
			else if(resMove == 4){
				System.out.println("Victoire");
				this.gameEnded = true;
			}
			this.processStatus();
			this.board.AffichPlateau();
			this.turnNum += 0.5;
			this.switchTurn();
			this.isIATurn = this.nextTurnIa();

		}
		else if(keys.get(Keys.CLICK)){
			if(this.selectedPawn==null){
				System.out.println("Test");
				if(this.board.board[(int)cursorPos.x][(int)cursorPos.y].getPawn().getType() != PawnType.VIDE &&
						this.board.board[(int)cursorPos.x][(int)cursorPos.y].getPawn().getType() == turn){
					System.out.println("Selected ["+cursorPos.x+","+cursorPos.y+"]");
					this.selectedPawn = this.board.board[(int)cursorPos.x][(int)cursorPos.y];
					this.moves = this.board.highlightMoves((int)cursorPos.x, (int)cursorPos.y, true);
				}
			}
			else{
				int xStart = (int) ((this.selectedPawn.getPosition().x- board.offsetX)/Block.SIZE);
				int yStart = (int) ((this.selectedPawn.getPosition().y- board.offsetY)/Block.SIZE);

				if((xStart == (int)cursorPos.x) && (yStart == (int)cursorPos.y)){
					this.selectedPawn = null;
					this.board.highlightMoves(this.moves, false);
				}
				else {
					this.resMove = board.deplacement(new Move(xStart,
							yStart,
							(int)cursorPos.x, 
							(int)cursorPos.y));
					this.processStatus();
					if(resMove == 3 ){
						System.out.println("Ca marche !!");
					}
					else{
						if(resMove == 4){
							System.out.println("Victoire");
							this.gameEnded = true;
						}
						this.selectedPawn = null;
						res = new Move(xStart,
								yStart,
								(int)cursorPos.x, 
								(int)cursorPos.y, this.turn, (int)this.turnNum);
						this.board.highlightMoves(this.moves, false);
						this.switchTurn();
						this.isIATurn = this.nextTurnIa();
						this.turnNum += 0.5;
					}
				}
			}
			keys.get(keys.put(Keys.CLICK, false));
		}
		return res;
	}


	private boolean nextTurnIa() {
		// TODO Auto-generated method stub
		if(this.turn==PawnType.MOSCOVITE)
			return p1 instanceof IA;
		else if (this.turn==PawnType.SUEDOIS)
			return p2 instanceof IA;
		return false;
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
