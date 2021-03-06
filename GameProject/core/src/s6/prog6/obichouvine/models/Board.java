package s6.prog6.obichouvine.models;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import s6.prog6.obichouvine.models.Block.BlockState;
import s6.prog6.obichouvine.models.Parameter.EscapeMethod;
import s6.prog6.obichouvine.models.Parameter.FirstStrike;
import s6.prog6.obichouvine.models.Parameter.KingCaptureMethod;
import s6.prog6.obichouvine.models.Parameter.KingMoveMethod;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Pawn.TypeSuedois;



public class Board {
	static Parameter parameter;
	Pawn p = null;
	public Block[][] board;
	private int offsetX, offsetY, xBoard, yBoard;
	private Historique histo;
	private boolean spiritPawn, surbriPawn;
	public boolean eaten = false;
	public Player p1, p2;
	public Board(int x ,int y, Parameter p)
	{
		Board.parameter =p;
		xBoard = x;
		yBoard = y;
		board = new Block[x][y];
		this.histo = new Historique("sauvegardeCoup.save");		
		offsetX = (int) (Gdx.graphics.getWidth()/2 - (x*Block.SIZE)/2);
		offsetY = (int) (Gdx.graphics.getHeight()/2 - (y*Block.SIZE)/2);
		spiritPawn =  false;
		surbriPawn = false;

		for(int i =0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				if(parameter.getEsc() == EscapeMethod.Corner)
				{
					if ((i == 0 && (j == 0 || j == y-1)) || (i == x-1 && (j == 0 || j == y-1)))
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)), BlockState.FORTERESSE,new Pawn(PawnType.VIDE));
					}
					else if((i == 0 &&(j == y/2 || j == (y/2)-1 || j == (y/2)+1) 
							||(i == 1 && j == y/2) 
							||(i == x-2 && j == y/2) 
							||(i == x-1 && (j == y/2 || j == (y/2)-1 || j == (y/2)+1))
							||(j == 0 && (i == y/2 || i == (y/2)-1 || i == (y/2)+1))
							||(j == y-1 && (i == x/2 || i == (x/2)-1 || i == (x/2)+1))
							||(j == 1 && i == x/2)
							||(j == y-2 && i == x/2)))
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)), BlockState.ROUGE,new Pawn(PawnType.MOSCOVITE));
					}
					else if((i == x/2 && j==y/2))
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)),BlockState.TRONE,new Pawn(PawnType.SUEDOIS,TypeSuedois.KING));
					}
					else if(i == x/2 || j == y/2)
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)),BlockState.BLANC,new Pawn(PawnType.SUEDOIS,TypeSuedois.PION));
					}
					else
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)),BlockState.BLANC,new Pawn(PawnType.VIDE));
					}
				}
				else if (parameter.getEsc() == EscapeMethod.Edge)
				{
					if ((i == 1 && j == y/2) 
							||(i == x-2 && j == y/2) 
							||(j == 1 && i == x/2)
							||(j == y-2 && i == x/2))
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)), BlockState.ROUGE,new Pawn(PawnType.MOSCOVITE));
					}
					else if((i == 0 &&(j == y/2 || j == (y/2)-1 || j == (y/2)+1) 
							||(i == x-1 && (j == y/2 || j == (y/2)-1 || j == (y/2)+1))
							||(j == 0 && (i == y/2 || i == (y/2)-1 || i == (y/2)+1))
							||(j == y-1 && (i == x/2 || i == (x/2)-1 || i == (x/2)+1))))
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)), BlockState.ROUGEEXIT,new Pawn(PawnType.MOSCOVITE));
					}
					else if((i == x/2 && j==y/2))
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)),BlockState.TRONE,new Pawn(PawnType.SUEDOIS,TypeSuedois.KING));
					}
					else if(i == x/2 || j == y/2)
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)),BlockState.BLANC,new Pawn(PawnType.SUEDOIS,TypeSuedois.PION));
					}
					else if (i == 0 || i == xBoard - 1 || j == 0 || j == yBoard - 1)
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)),BlockState.BLANCEXIT,new Pawn(PawnType.VIDE));
					}
					else
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)),BlockState.BLANC,new Pawn(PawnType.VIDE));
					}
				}
				else if (parameter.getEsc() == EscapeMethod.EdgeWithoutMosco)
				{
					if((i == 0 &&(j == y/2 || j == (y/2)-1 || j == (y/2)+1) 
							||(i == 1 && j == y/2) 
							||(i == x-2 && j == y/2) 
							||(i == x-1 && (j == y/2 || j == (y/2)-1 || j == (y/2)+1))
							||(j == 0 && (i == y/2 || i == (y/2)-1 || i == (y/2)+1))
							||(j == y-1 && (i == x/2 || i == (x/2)-1 || i == (x/2)+1))
							||(j == 1 && i == x/2)
							||(j == y-2 && i == x/2)))
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)), BlockState.ROUGE,new Pawn(PawnType.MOSCOVITE));
					}
					else if((i == x/2 && j==y/2))
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)),BlockState.TRONE,new Pawn(PawnType.SUEDOIS,TypeSuedois.KING));
					}
					else if(i == x/2 || j == y/2)
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)),BlockState.BLANC,new Pawn(PawnType.SUEDOIS,TypeSuedois.PION));
					}
					else if (i == 0 || i == xBoard - 1 || j == 0 || j == yBoard - 1)
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)),BlockState.BLANCEXIT,new Pawn(PawnType.VIDE));
					}
					else
					{
						board[i][j] = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)),BlockState.BLANC,new Pawn(PawnType.VIDE));
					}
				}
			}
		}	
	}

	private void surbrillance (Move[] moveArray, boolean b) {
		int i;
		for(i = 0; i < moveArray.length; i++) {
			board[moveArray[i].getxArr()][moveArray[i].getyArr()].setSurbrillance(b);
		}
	}

	public Move[] highlightMoves(int x, int y, boolean b){
		Move[] moves = deplacementsPossibles(x, y);
		surbrillance(moves, b);
		return moves;
	}

	public Move[] highlightMoves(Move[] moves,  boolean b){
		surbrillance(moves, b);
		return moves;
	}

	public void lightPawn (int x, int y) 
	{
		Pawn p = board[x][y].getPawn();
		if (p.getType() != PawnType.VIDE)
		{
			if (surbriPawn)
			{
				if (p.getSurbri())
				{
					p.setSurbri(false);	
					surbriPawn = false;
				}				
			}
			else
			{
				p.setSurbri(true);
				surbriPawn =  true;
			}
		}
	}

	public void ghostPawn (int x, int y,boolean b) 
	{
		Pawn p = board[x][y].getPawn();
		if (p.getType() != PawnType.VIDE)
		{
			if (spiritPawn)
			{
				if (board[x][y].getPawn().getType() == PawnType.MOSCGHOST
					|| board[x][y].getPawn().getType() == PawnType.VIKGHOST)
					board[x][y].setPawn(new Pawn(PawnType.VIDE));
				spiritPawn = false;	
				board[25][18].getPawn();
				
			}
			else
			{
				if (b)
					board[x][y].setPawn(new Pawn(PawnType.VIKGHOST));
				else
					board[x][y].setPawn(new Pawn(PawnType.MOSCGHOST));
				spiritPawn =  true;
			}
		}
	}
	public void AffichPlateau()
	{
		for (int i = 0; i < xBoard; i++)
		{
			for (int j = 0; j < yBoard; j++)
			{
				BlockState state = board[i][j].getState();
				PawnType typepion = board[i][j].getPawn().getType();
				TypeSuedois typesuede = board[i][j].getPawn().getTypesuede();

				if (state == BlockState.BLANC)
				{
					System.out.print("B");
				}else if (state == BlockState.ROUGE)
				{
					System.out.print("R");
				}else if (state == BlockState.FORTERESSE)
				{
					System.out.print("F");
				}else if (state == BlockState.TRONE)
				{
					System.out.print("T");
				}
				if (typepion == PawnType.MOSCOVITE)
				{
					System.out.print("M");
				}else if (typepion == PawnType.SUEDOIS && typesuede == TypeSuedois.PION)
				{
					System.out.print("S");
				}else if (typepion == PawnType.SUEDOIS && typesuede == TypeSuedois.KING)
				{
					System.out.print("K");
				}else if (typepion == PawnType.VIDE)
				{
					System.out.print(".");
				}
				System.out.print(" ");
			}
			System.out.println("");
		}
	}


	public int deplacement(Move c)
	{
		System.out.println("Verif : "+verifDeplacment(c));
		if (verifDeplacment(c))
		{
			
			board[c.getxArr()][c.getyArr()].setPawn(board[c.getxDep()][c.getyDep()].getPawn()); 
			//board[c.getxDep()][c.getyDep()].setPawn(new Pawn(
					//(board[c.getxDep()][c.getyDep()].getPawn().getType()==PawnType.MOSCOVITE)?PawnType.MOSCGHOST:PawnType.VIKGHOST));
			board[c.getxDep()][c.getyDep()].setPawn(new Pawn(PawnType.VIDE));
			if(manger(c)!=0)
				this.eaten = true;
			if (verifGagne(c))
			{
				return 4;
			}
			return verifRaishiTuishi (c);
		}
		return 3;

	}

	public void deplacementsansverif(Move c) //optimiser pour IA

	{	

		board[c.getxArr()][c.getyArr()].setPawn(board[c.getxDep()][c.getyDep()].getPawn()); 
		board[c.getxDep()][c.getyDep()].setPawn(new Pawn(PawnType.VIDE));

	}

	private boolean verifDeplacment(Move c) {
		int x = c.getxDep();
		int y = c.getyDep();
		int x1 = c.getxArr();
		int y1 = c.getyArr();
		//System.out.println("\n\n" + x1 + y1 + "\n\n" + x + y);
		if (board[x][y].getPawn().getType() != PawnType.VIDE
			&& board[x][y].getPawn().getType() != PawnType.VIKGHOST
			&& board[x][y].getPawn().getType() != PawnType.MOSCGHOST) {
			if (x == x1) {
				if (y < y1) {
					for (int i = y + 1; i < y1 + 1; i++) {
						if (board[x][i].getPawn().getType() != PawnType.VIDE 
								&& board[x][i].getPawn().getType() != PawnType.VIKGHOST
								&& board[x][i].getPawn().getType() != PawnType.MOSCGHOST
								|| (board[x][i].getState() == BlockState.TRONE
									&& ((board[x][y].getPawn().getType() == PawnType.SUEDOIS
											&& board[x][y].getPawn().getTypesuede() != TypeSuedois.KING)
										|| board[x][y].getPawn().getType() == PawnType.MOSCOVITE)))
							return false;
					}
					if(board[x1][y1].getState() == BlockState.FORTERESSE)
						if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING)
							if (parameter.getKingMove() == KingMoveMethod.FourBlock)	
								return y1 - y < 5;
							else 
								return true;
						else
							return false;
					else
					{
						if (parameter.getKingMove() == KingMoveMethod.FourBlock && (board[x][y].getPawn().getType() == PawnType.SUEDOIS
								&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING))	
							return y1 - y < 5;
						else 
							return true;
					}

				} else {
					for (int i = y1; i < y; i++) {
						if (board[x][i].getPawn().getType() != PawnType.VIDE
								&& board[x][i].getPawn().getType() != PawnType.VIKGHOST
								&& board[x][i].getPawn().getType() != PawnType.MOSCGHOST
								|| (board[x][i].getState() == BlockState.TRONE
								&& ((board[x][y].getPawn().getType() == PawnType.SUEDOIS
								&& board[x][y].getPawn().getTypesuede() != TypeSuedois.KING)
								|| board[x][y].getPawn().getType() == PawnType.MOSCOVITE)))
							return false;
					}
					if(board[x1][y1].getState() == BlockState.FORTERESSE)
						if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING)
							if (parameter.getKingMove() == KingMoveMethod.FourBlock)	
								return y - y1 < 5;
							else 
								return true;
						else
							return false;
					else
					{
						if (parameter.getKingMove() == KingMoveMethod.FourBlock && (board[x][y].getPawn().getType() == PawnType.SUEDOIS
								&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING))	
							return y - y1 < 5;
						else 
							return true;
					}
				}
			} else if (y == y1) {
				if (x < x1) {
					for (int i = x + 1; i < x1 + 1; i++) {
						if (board[i][y].getPawn().getType() != PawnType.VIDE
								&& board[i][y].getPawn().getType() != PawnType.VIKGHOST
								&& board[i][y].getPawn().getType() != PawnType.MOSCGHOST
								|| (board[i][y].getState() == BlockState.TRONE
								&& ((board[x][y].getPawn().getType() == PawnType.SUEDOIS
								&& board[x][y].getPawn().getTypesuede() != TypeSuedois.KING)
								|| board[x][y].getPawn().getType() == PawnType.MOSCOVITE)))
							return false;
					}
					if(board[x1][y1].getState() == BlockState.FORTERESSE)
						if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING)
							if (parameter.getKingMove() == KingMoveMethod.FourBlock)	
								return x1 - x < 5;
							else 
								return true;
						else
							return false;
					else
					{
						if (parameter.getKingMove() == KingMoveMethod.FourBlock && (board[x][y].getPawn().getType() == PawnType.SUEDOIS
								&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING))	
							return x1 - x < 5;
						else 
							return true;
					}
				} else {
					for (int i = x1; i < x; i++) {
						if (board[i][y].getPawn().getType() != PawnType.VIDE
								&& board[i][y].getPawn().getType() != PawnType.VIKGHOST
								&& board[i][y].getPawn().getType() != PawnType.MOSCGHOST
								|| (board[i][y].getState() == BlockState.TRONE
								&& ((board[x][y].getPawn().getType() == PawnType.SUEDOIS
								&& board[x][y].getPawn().getTypesuede() != TypeSuedois.KING)
								|| board[x][y].getPawn().getType() == PawnType.MOSCOVITE)))
							return false;
					}
					if(board[x1][y1].getState() == BlockState.FORTERESSE)
						if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING)
							if (parameter.getKingMove() == KingMoveMethod.FourBlock)	
								return x - x1 < 5;
							else 
								return true;
						else
							return false;
					else
					{
						if (parameter.getKingMove() == KingMoveMethod.FourBlock && (board[x][y].getPawn().getType() == PawnType.SUEDOIS
								&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING))	
							return x - x1 < 5;
						else 
							return true;
					}
				}
			}
		}
		return false;
	}

	public int verifRaishiTuishi (Move c){
		int posKing = GetPosKing();
		Move[] p  = deplacementsPossibles((posKing-posKing%10)/10, posKing%10);
		int j = 0;
		System.out.println("Possibilité : "+p);
		for(int i = 0; i <  p.length; i++)
		{
			System.out.println(p[i]);
			if(p[i] != null && board[p[i].getxArr()][p[i].getyArr()].getState() == BlockState.FORTERESSE)
				j++;
		}
		return j;
	}

	private Boolean verifGagne(Move c) {

		int posY = this.GetPosKing()%10;
		int posX = (this.GetPosKing() - posY)/10;
		if (posX == 0 
				|| ((posX > 0) && (board[posX-1][posY].getPawn().getType() == PawnType.MOSCOVITE
				|| board[posX-1][posY].getState() == BlockState.FORTERESSE 
				|| board[posX-1][posY].getState() == BlockState.TRONE )))
		{	
			if (posX == xBoard - 1 
					||((posX <  xBoard - 1 ) 
							&& (board[posX+1][posY].getPawn().getType() == PawnType.MOSCOVITE
							|| board[posX+1][posY].getState() == BlockState.FORTERESSE 
							|| board[posX+1][posY].getState() == BlockState.TRONE )))
			{
				if(posY == 0 
						||((posY > 0) &&(board[posX][posY-1].getPawn().getType() == PawnType.MOSCOVITE 
						|| board[posX][posY-1].getState() == BlockState.FORTERESSE 
						|| board[posX][posY-1].getState() == BlockState.TRONE)))
				{
					if(posY == yBoard - 1 
							|| ((posY < yBoard - 1) && (board[posX][posY+1].getPawn().getType() == PawnType.MOSCOVITE
							|| board[posX][posY+1].getState() == BlockState.FORTERESSE 
							|| board[posX][posY+1].getState() == BlockState.TRONE )))
					{
						return true;
					}

				}

			}
		}
		if(parameter.getEsc() == EscapeMethod.Corner)
		{
			if (board[posX][posY].getState() == BlockState.FORTERESSE)
				return true;
		}else if (parameter.getEsc() == EscapeMethod.Edge)
		{
			if (board[posX][posY].getState() == BlockState.BLANCEXIT 
					|| board[posX][posY].getState() == BlockState.ROUGEEXIT)
				return true;
		}else if (parameter.getEsc() == EscapeMethod.EdgeWithoutMosco)
		{
			if (board[posX][posY].getState() == BlockState.BLANCEXIT)
				return true;
		}

		Pawn pionActuel =  board[c.getxArr()][c.getyArr()].getPawn();
		Pawn pionAdverse = null;
		if (pionActuel.getType() == PawnType.SUEDOIS)
		{
			pionAdverse = new Pawn(PawnType.MOSCOVITE);
		}
		else
		{
			pionAdverse = new Pawn(PawnType.SUEDOIS, TypeSuedois.PION);
		}
		for(int i =0; i < xBoard; i++)
		{
			for(int j = 0; j < yBoard; j++)
			{
				if (board[i][j].getPawn().getType() == pionAdverse.getType() )
					return false;
			}

		}
		return false;
	}

	public boolean verifCoupGagnant(Move c) {
		if (board[c.getxDep()][c.getyDep()].getPawn().getType() == PawnType.SUEDOIS
				&& board[c.getxDep()][c.getyDep()].getPawn().getTypesuede() == TypeSuedois.KING) {
			if (parameter.getEsc() == EscapeMethod.Corner) {
				if (board[c.getxArr()][c.getyArr()].getState() == BlockState.FORTERESSE)
					return true;
			} else if (parameter.getEsc() == EscapeMethod.Edge) {
				if (board[c.getxArr()][c.getyArr()].getState() == BlockState.BLANCEXIT
						|| board[c.getxArr()][c.getyArr()].getState() == BlockState.ROUGEEXIT)
					return true;
			} else if (parameter.getEsc() == EscapeMethod.EdgeWithoutMosco) {
				if (board[c.getxArr()][c.getyArr()].getState() == BlockState.BLANCEXIT)
					return true;
			}
		} else {
			int tmpX = c.getxArr();
			int tmpY = c.getyArr();
			int roiX = 0;
			int roiY = 0;
			if (tmpX != 0
					&& board[tmpX - 1][tmpY].getPawn().getTypesuede() == TypeSuedois.KING) {
				roiX = tmpX - 1;
				roiY = tmpY;
			} else if (tmpY != 0
					&& board[tmpX][tmpY - 1].getPawn().getTypesuede() == TypeSuedois.KING) {
				roiX = tmpX;
				roiY = tmpY - 1;
			} else if (tmpX != xBoard - 1
					&& board[tmpX + 1][tmpY].getPawn().getTypesuede() == TypeSuedois.KING) {
				roiX = tmpX + 1;
				roiY = tmpY;
			} else if (tmpY != yBoard - 1
					&& board[tmpX][tmpY + 1].getPawn().getTypesuede() == TypeSuedois.KING) {
				roiX = tmpX;
				roiY = tmpY + 1;
			}
			if (roiX != 0 && roiY != 0) {
				if (roiX == 0
						|| (roiX > 0 && (board[roiX - 1][roiY].getPawn()
								.getType() == PawnType.MOSCOVITE || board[roiX - 1][roiY]
										.getState() == BlockState.FORTERESSE))) {
					if (roiY == 0
							|| (roiY > 0 && (board[roiX][roiY - 1].getPawn()
									.getType() == PawnType.MOSCOVITE || board[roiX][roiY - 1]
											.getState() == BlockState.FORTERESSE))) {
						if (roiX == xBoard
								|| (roiX < xBoard - 1 && (board[roiX + 1][roiY]
										.getPawn().getType() == PawnType.MOSCOVITE || board[roiX + 1][roiY]
												.getState() == BlockState.FORTERESSE))) {
							if (roiX == yBoard
									|| (roiY < yBoard - 1 && (board[roiX][roiY + 1]
											.getPawn().getType() == PawnType.MOSCOVITE || board[roiX][roiY + 1]
													.getState() == BlockState.FORTERESSE))) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public int manger(Move c) {

		boolean isKing = false;
		int total = 0;
		int x1 = c.getxArr();
		int y1 = c.getyArr();
		Pawn pionActuel = board[x1][y1].getPawn();
		Pawn pionAdverse = null;

		if (parameter.getKingCap() == KingCaptureMethod.Can)
		{
			isKing = true;
		}

		if (pionActuel.getType() == PawnType.SUEDOIS )
		{
			pionAdverse = new Pawn(PawnType.MOSCOVITE);
			if(pionActuel.getTypesuede() == TypeSuedois.KING)
				isKing = true;
		}
		else
		{
			pionAdverse = new Pawn(PawnType.SUEDOIS, TypeSuedois.PION);
		}
		
		if (parameter.getKingCap() == KingCaptureMethod.Cannot && pionActuel.getType() == PawnType.SUEDOIS)

		{
			Pawn oppositePawn = null;
			if (isKing)
				return 0;
			else
			{
				if (x1 + 1 < xBoard && (board[x1+1][y1].getPawn().getType() == PawnType.MOSCOVITE))
				{
					if(x1 + 2 < xBoard)
					{
						oppositePawn = board[x1+2][y1].getPawn();
						if ((oppositePawn.getType() == PawnType.SUEDOIS  && oppositePawn.getTypesuede() == TypeSuedois.PION) 
								|| (board[x1+2][y1].getState() == BlockState.FORTERESSE) 
								|| (board[x1+2][y1].getState() == BlockState.TRONE))
						{
							total += 4;
							board[x1+1][y1].setPawn(new Pawn(PawnType.VIDE));
						}
					}
				}

		
				if (y1 + 1 < yBoard && board[x1][y1 + 1].getPawn().getType() == PawnType.MOSCOVITE)

				{
					if (y1 + 2 < yBoard)
					{
						oppositePawn = board[x1][y1 + 2].getPawn();
						if ((oppositePawn.getType() == PawnType.SUEDOIS && oppositePawn.getTypesuede() == TypeSuedois.PION) 
								|| (board[x1][y1 + 2].getState() == BlockState.FORTERESSE)
								|| (board[x1][y1 + 2].getState() == BlockState.TRONE))
						{
							total +=  8;
							board[x1][y1 + 1].setPawn(new Pawn(PawnType.VIDE));
						}
					}
				}
	 
				if (x1 - 1 > - 1 && board[x1 - 1][y1].getPawn().getType() == PawnType.MOSCOVITE)
				{
					if(x1 - 2 > -1)
					{
						oppositePawn = board[x1 - 2][y1].getPawn();
						if ((oppositePawn.getType() == PawnType.SUEDOIS && oppositePawn.getTypesuede() == TypeSuedois.PION) 
								|| (board[x1 - 2][y1].getState() == BlockState.FORTERESSE)
								|| (board[x1 - 2][y1].getState() == BlockState.TRONE))
						{
							total += 1;
							board[x1 - 1][y1].setPawn(new Pawn(PawnType.VIDE));
						}
					}
				}
				if (y1 - 1 > - 1 && board[x1][y1 - 1].getPawn().getType() == PawnType.MOSCOVITE)
				{
					if (y1 - 2 > -1)
					{
						oppositePawn = board[x1][y1 - 2].getPawn();
						if ((oppositePawn.getType() == PawnType.SUEDOIS && oppositePawn.getTypesuede() == TypeSuedois.PION) 
								|| (board[x1][y1 - 2].getState() == BlockState.FORTERESSE)
								|| (board[x1][y1 - 2].getState() == BlockState.TRONE))
						{
							total += 2;
							board[x1][y1 - 1].setPawn(new Pawn(PawnType.VIDE));
						}
					}
				}
			}
		}
		if (parameter.getKingCap() == KingCaptureMethod.NotAPillar)
		{
			Pawn oppositePawn = null;
			if (isKing)	 
			{
				if (x1 + 1 < xBoard && (board[x1+1][y1].getPawn().getType() == PawnType.MOSCOVITE))
				{
					if(x1 + 2 < xBoard)
					{
						oppositePawn = board[x1+2][y1].getPawn();
						if ((oppositePawn.getType() == PawnType.SUEDOIS) 
								|| (board[x1+2][y1].getState() == BlockState.FORTERESSE) 
								|| (board[x1+2][y1].getState() == BlockState.TRONE))
						{
							total += 4;
							board[x1+1][y1].setPawn(new Pawn(PawnType.VIDE));
						}
					}
				}
		
				if (y1 + 1 < yBoard && board[x1][y1 + 1].getPawn().getType() == PawnType.MOSCOVITE)
				{
					if (y1 + 2 < yBoard)
					{
						oppositePawn = board[x1][y1 + 2].getPawn();
						if ((oppositePawn.getType() == PawnType.SUEDOIS) 
								|| (board[x1][y1 + 2].getState() == BlockState.FORTERESSE)
								|| (board[x1][y1 + 2].getState() == BlockState.TRONE))
						{
							total +=  8;
							board[x1][y1 + 1].setPawn(new Pawn(PawnType.VIDE));
						}
					}
				}
	 
				if (x1 - 1 > - 1 && board[x1 - 1][y1].getPawn().getType() == PawnType.MOSCOVITE)
				{
					if(x1 - 2 > -1)
					{
						oppositePawn = board[x1 - 2][y1].getPawn();
						if ((oppositePawn.getType() == PawnType.SUEDOIS) 
								|| (board[x1 - 2][y1].getState() == BlockState.FORTERESSE)
								|| (board[x1 - 2][y1].getState() == BlockState.TRONE))
						{
							total += 1;
							board[x1 - 1][y1].setPawn(new Pawn(PawnType.VIDE));
						}
					}
				}
				if (y1 - 1 > - 1 && board[x1][y1 - 1].getPawn().getType() == PawnType.MOSCOVITE)
				{
					if (y1 - 2 > -1)
					{
						oppositePawn = board[x1][y1 - 2].getPawn();
						if ((oppositePawn.getType() == PawnType.SUEDOIS) 
								|| (board[x1][y1 - 2].getState() == BlockState.FORTERESSE)
								|| (board[x1][y1 - 2].getState() == BlockState.TRONE))
						{
							total += 2;
							board[x1][y1 - 1].setPawn(new Pawn(PawnType.VIDE));
						}
					}
				}
			}else
			{
				if (x1 + 1 < xBoard && (board[x1+1][y1].getPawn().getType() == PawnType.MOSCOVITE))
				{
					if(x1 + 2 < xBoard)
					{
						oppositePawn = board[x1+2][y1].getPawn();
						if ((oppositePawn.getType() == PawnType.SUEDOIS  && oppositePawn.getTypesuede() == TypeSuedois.PION) 
								|| (board[x1+2][y1].getState() == BlockState.FORTERESSE)
								|| (board[x1+2][y1].getState() == BlockState.TRONE))
						{
							total += 4;
							board[x1+1][y1].setPawn(new Pawn(PawnType.VIDE));
						}
					}
				}

				if (y1 + 1 < yBoard && board[x1][y1 + 1].getPawn().getType() == PawnType.MOSCOVITE)
				{
					if (y1 + 2 < yBoard)
					{
						oppositePawn = board[x1][y1 + 2].getPawn();
						if ((oppositePawn.getType() == PawnType.SUEDOIS && oppositePawn.getTypesuede() == TypeSuedois.PION) 
								|| (board[x1][y1 + 2].getState() == BlockState.FORTERESSE)
								|| (board[x1][y1 + 2].getState() == BlockState.TRONE))
						{
							total +=  8;
							board[x1][y1 + 1].setPawn(new Pawn(PawnType.VIDE));
						}
					}
				}

				if (x1 - 1 > - 1 && board[x1 - 1][y1].getPawn().getType() == PawnType.MOSCOVITE)
				{
					if(x1 - 2 > -1)
					{
						oppositePawn = board[x1 - 2][y1].getPawn();
						if ((oppositePawn.getType() == PawnType.SUEDOIS && oppositePawn.getTypesuede() == TypeSuedois.PION) 
								|| (board[x1 - 2][y1].getState() == BlockState.FORTERESSE)
								|| (board[x1 - 2][y1].getState() == BlockState.TRONE))
						{
							total += 1;
							board[x1 - 1][y1].setPawn(new Pawn(PawnType.VIDE));
						}
					}
				}
				if (y1 - 1 > - 1 && board[x1][y1 - 1].getPawn().getType() == PawnType.MOSCOVITE)
				{
					if (y1 - 2 > -1)
					{
						oppositePawn = board[x1][y1 - 2].getPawn();
						if ((oppositePawn.getType() == PawnType.SUEDOIS && oppositePawn.getTypesuede() == TypeSuedois.PION) 
								|| (board[x1][y1 - 2].getState() == BlockState.FORTERESSE)
								|| (board[x1][y1 - 2].getState() == BlockState.TRONE))
						{
							total += 2;
							board[x1][y1 - 1].setPawn(new Pawn(PawnType.VIDE));
						}
					}
				}
			}

		}

		if (parameter.getKingCap() == KingCaptureMethod.Can || pionActuel.getType() == PawnType.MOSCOVITE)
		{
			if (x1 + 1 < xBoard && (board[x1+1][y1].getPawn().getType() == pionAdverse.getType()))
			{
				
				pionAdverse = board[x1+1][y1].getPawn();
				if (x1 + 2 < xBoard 
						&& ((board[x1+2][y1].getPawn().getType() == pionActuel.getType()) 
						|| (board[x1+2][y1].getState() == BlockState.FORTERESSE)))
				{
					if ((pionAdverse.getType() == PawnType.SUEDOIS 
							&& pionAdverse.getTypesuede() != TypeSuedois.KING) 
							|| pionAdverse.getType() == PawnType.MOSCOVITE)
					{
						total += 4;
						board[x1+1][y1].setPawn(new Pawn(PawnType.VIDE));
					}
				}
			}
	
			if (y1 + 1 < yBoard && board[x1][y1 + 1].getPawn().getType() == pionAdverse.getType())
			{
				 
				 pionAdverse = board[x1][y1 + 1].getPawn();
				if (y1 + 2 < yBoard && ((board[x1][y1 + 2].getPawn().getType() == pionActuel.getType()) 
						|| (board[x1][y1 + 2].getState() == BlockState.FORTERESSE)))
				{
					if ((pionAdverse.getType() == PawnType.SUEDOIS 
							&& pionAdverse.getTypesuede() != TypeSuedois.KING) 
							|| pionAdverse.getType() == PawnType.MOSCOVITE)
					{
						total +=  8;
						board[x1][y1 + 1].setPawn(new Pawn(PawnType.VIDE));
					}
				}
			}
 
			if (x1 - 1 > - 1 && board[x1 - 1][y1].getPawn().getType() == pionAdverse.getType())
			{
				 
				 pionAdverse = board[x1 - 1][y1].getPawn();
				if (x1 - 2 > - 1 
						&& ((board[x1 - 2][y1].getPawn().getType() == pionActuel.getType()) 
						|| (board[x1 - 2][y1].getState() == BlockState.FORTERESSE)))
				{
					if ((pionAdverse.getType() == PawnType.SUEDOIS 
							&& pionAdverse.getTypesuede() != TypeSuedois.KING) 
							|| pionAdverse.getType() == PawnType.MOSCOVITE)
					{
						total += 1;
						board[x1 - 1][y1].setPawn(new Pawn(PawnType.VIDE));
					}
				}
			}
			if (y1 - 1 > - 1 && board[x1][y1 - 1].getPawn().getType() == pionAdverse.getType())
			{
				 
				 pionAdverse = board[x1][y1 - 1].getPawn();
				if (y1 - 2 > - 1 
						&& ((board[x1][y1 - 2].getPawn().getType() == pionActuel.getType()) 
						|| (board[x1][y1 - 2].getState() == BlockState.FORTERESSE)))
				{
					if ((pionAdverse.getType() == PawnType.SUEDOIS 
							&& pionAdverse.getTypesuede() != TypeSuedois.KING) 
							|| pionAdverse.getType() == PawnType.MOSCOVITE)
					{
						total += 2;
						board[x1][y1 - 1].setPawn(new Pawn(PawnType.VIDE));
					}
				}
			}
		}
		
		return total;

	}

	public Move[] deplacementsPossibles(int x,int y) {

		Move[] coups= new Move[xBoard+yBoard];

		int l =0;

		int i = x + 1;
		while (i < xBoard && (board[i][y].getPawn().getType() == Pawn.PawnType.VIDE 
				|| board[i][y].getPawn().getType() == Pawn.PawnType.MOSCGHOST 
				|| board[i][y].getPawn().getType() == Pawn.PawnType.VIKGHOST))
		{
			if (board[i][y].getState() == BlockState.FORTERESSE
					|| board[i][y].getState() == BlockState.TRONE) {
				if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING) {
					if (parameter.getKingMove() == KingMoveMethod.Unlimited)
					{
						coups[l] = new Move(x, y, i, y);
						l++;
					}else if (parameter.getKingMove() == KingMoveMethod.FourBlock)
					{
						if (i - x < 5)
						{
							coups[l] = new Move(x, y, i, y);
							l++;
						}
					}else if (parameter.getKingMove() == KingMoveMethod.WithoutMosco)
					{
						if(y == 1)
						{
							if (x < 4 && i > 4 || x > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(y == 0)
						{
							if (x < 3 && i > 3 || x > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(y == xBoard - 1)
						{
							if (x < 4 && i > 4 || x > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(y == xBoard)
						{
							if (x < 3 && i > 3 || x > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}
					}
				}
				else
				{
					break;
				}
			}else
			{
				if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING) 
				{
					if (parameter.getKingMove() == KingMoveMethod.Unlimited)
					{
						coups[l] = new Move(x, y, i, y);
						l++;
					}else if (parameter.getKingMove() == KingMoveMethod.FourBlock)
					{
						if (i - x < 5)
						{
							coups[l] = new Move(x, y, i, y);
							l++;
						}
					}else if (parameter.getKingMove() == KingMoveMethod.WithoutMosco)
					{
						if(y == 1)
						{
							if (x < 4 && i > 4 || x > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(y == 0)
						{
							if (x < 3 && i > 3 || x > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(y == xBoard - 1)
						{
							if (x < 4 && i > 4 || x > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(y == xBoard)
						{
							if (x < 3 && i > 3 || x > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}
					}

				}else
				{
					coups[l] = new Move(x, y, i, y);
					l++;
				}
			}
			i++;
		}

		i = x - 1;

		while (i > -1 && (board[i][y].getPawn().getType() == Pawn.PawnType.VIDE 
				|| board[i][y].getPawn().getType() == Pawn.PawnType.MOSCGHOST 
				|| board[i][y].getPawn().getType() == Pawn.PawnType.VIKGHOST))
		{
			if (board[i][y].getState() == BlockState.FORTERESSE
					|| board[i][y].getState() == BlockState.TRONE) {
				if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING) {
					if (parameter.getKingMove() == KingMoveMethod.Unlimited)
					{
						coups[l] = new Move(x, y, i, y);
						l++;
					}else if (parameter.getKingMove() == KingMoveMethod.FourBlock)
					{
						if (x - i < 5)
						{
							coups[l] = new Move(x, y, i, y);
							l++;
						}
					}else if (parameter.getKingMove() == KingMoveMethod.WithoutMosco)
					{
						if(y == 1)
						{
							if (x < 4 && i > 4 || x > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(y == 0)
						{
							if (x < 3 && i > 3 || x > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(y == xBoard - 1)
						{
							if (x < 4 && i > 4 || x > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(y == xBoard)
						{
							if (x < 3 && i > 3 || x > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}
					}
				}
				else
				{
					break;
				}
			}else
			{
				if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING) 
				{
					if (parameter.getKingMove() == KingMoveMethod.Unlimited)
					{
						coups[l] = new Move(x, y, i, y);
						l++;
					}else if (parameter.getKingMove() == KingMoveMethod.FourBlock)
					{
						if (x - i < 5)
						{
							coups[l] = new Move(x, y, i, y);
							l++;
						}
					}else if (parameter.getKingMove() == KingMoveMethod.WithoutMosco)
					{
						if(y == 1)
						{
							if (x < 4 && i > 4 || x > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(y == 0)
						{
							if (x < 3 && i > 3 || x > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(y == xBoard - 1)
						{
							if (x < 4 && i > 4 || x > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(y == xBoard)
						{
							if (x < 3 && i > 3 || x > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, i, y);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}
					}

				}else
				{
					coups[l] = new Move(x, y, i, y);
					l++;
				}
			}
			i--;
		}

		i = y + 1;

		while (i < yBoard && (board[x][i].getPawn().getType() == Pawn.PawnType.VIDE 
				|| board[x][i].getPawn().getType() == Pawn.PawnType.MOSCGHOST 
				|| board[x][i].getPawn().getType() == Pawn.PawnType.VIKGHOST))
		{
			if (board[x][i].getState() == BlockState.FORTERESSE
					|| board[x][i].getState() == BlockState.TRONE) 
			{
				if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING) 
				{
					if (parameter.getKingMove() == KingMoveMethod.Unlimited)
					{
						coups[l] = new Move(x, y, x, i);
						l++;
					}else if (parameter.getKingMove() == KingMoveMethod.FourBlock)
					{
						if (i - y < 5)
						{
							coups[l] = new Move(x, y, x, i);
							l++;
						}
					}else if (parameter.getKingMove() == KingMoveMethod.WithoutMosco)
					{
						if(x == 1)
						{
							if (y < 4 && i > 4 || y > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(x == 0)
						{
							if (y < 3 && i > 3 || y > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(x == xBoard - 1)
						{
							if (y < 4 && i > 4 || y > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(x == xBoard)
						{
							if (y < 3 && i > 3 || y > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}
					}
				}
				else
				{
					break;
				}
			}else
			{
				if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING) 
				{
					if (parameter.getKingMove() == KingMoveMethod.Unlimited)
					{
						coups[l] = new Move(x, y, x, i);
						l++;
					}else if (parameter.getKingMove() == KingMoveMethod.FourBlock)
					{
						if (i - y< 5)
						{
							coups[l] = new Move(x, y, x, i);
							l++;
						}
					}else if (parameter.getKingMove() == KingMoveMethod.WithoutMosco)
					{
						if(x == 1)
						{
							if (y < 4 && i > 4 || y > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(x == 0)
						{
							if (y < 3 && i > 3 || y > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(x == xBoard - 1)
						{
							if (y < 4 && i > 4 || y > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(x == xBoard)
						{
							if (y < 3 && i > 3 || y > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}
					}

				}else
				{
					coups[l] = new Move(x, y, x, i);
					l++;
				}
			}
			i++;
		}

		i = y - 1;

		while (i > - 1 && (board[x][i].getPawn().getType() == Pawn.PawnType.VIDE 
				|| board[x][i].getPawn().getType() == Pawn.PawnType.MOSCGHOST 
				|| board[x][i].getPawn().getType() == Pawn.PawnType.VIKGHOST))
		{
			if (board[x][i].getState() == BlockState.FORTERESSE
					|| board[x][i].getState() == BlockState.TRONE) {
				if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING) {
					if (parameter.getKingMove() == KingMoveMethod.Unlimited)
					{
						coups[l] = new Move(x, y, x, i);
						l++;
					}else if (parameter.getKingMove() == KingMoveMethod.FourBlock)
					{
						if (y - i < 5)
						{
							coups[l] = new Move(x, y, x, i);
							l++;
						}
					}else if (parameter.getKingMove() == KingMoveMethod.WithoutMosco)
					{
						if(x == 1)
						{
							if (y < 4 && i > 4 || y > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(x == 0)
						{
							if (y < 3 && i > 3 || y > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(x == xBoard - 1)
						{
							if (y < 4 && i > 4 || y > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(x == xBoard)
						{
							if (y < 3 && i > 3 || y > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}
					}

				}
				else
				{
					break;
				}
			}else
			{
				if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING) 
				{
					if (parameter.getKingMove() == KingMoveMethod.Unlimited)
					{
						coups[l] = new Move(x, y, x, i);
						l++;
					}else if (parameter.getKingMove() == KingMoveMethod.FourBlock)
					{
						if (y - i< 5)
						{
							coups[l] = new Move(x, y, x, i);
							l++;
						}
					}else if (parameter.getKingMove() == KingMoveMethod.WithoutMosco)
					{
						if(x == 1)
						{
							if (y < 4 && i > 4 || y > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(x == 0)
						{
							if (y < 3 && i > 3 || y > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(x == xBoard - 1)
						{
							if (y < 4 && i > 4 || y > 4 && i < 4)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}else if(x == xBoard)
						{
							if (y < 3 && i > 3 || y > 5 && i < 5)
							{
								Move aVerif = new Move(x, y, x, i);
								if (verifRouge(aVerif)){
									coups[l] = aVerif;
									l++;
								}
							}
						}
					}

				}else
				{
					coups[l] = new Move(x, y, x, i);
					l++;
				}
			}
			i--;
		}
		Move[] coupss = new Move[l];
		for (int k = 0; k < l; k++) 
		{
			coupss[k] = new Move(coups[k].getxDep(), coups[k].getyDep(),coups[k].getxArr(), coups[k].getyArr());

		}

		return coupss;
	}

	public boolean verifRouge(Move aVerif) 
	{
		if (aVerif.getxDep() == aVerif.getxArr())
		{
			if (aVerif.getxDep() == 1 || aVerif.getxDep() == 0)
			{
				if (board[0][3].getPawn().getType() == PawnType.MOSCOVITE 
						|| board[0][4].getPawn().getType() == PawnType.MOSCOVITE 
						|| board[0][5].getPawn().getType() == PawnType.MOSCOVITE 
						|| board[1][4].getPawn().getType() == PawnType.MOSCOVITE)
				{
					return false;
				}else 
				{
					return true;
				}
			}else if (aVerif.getxDep() == xBoard || aVerif.getxDep() == xBoard - 1)
			{

				if (board[xBoard][3].getPawn().getType() == PawnType.MOSCOVITE 
						|| board[xBoard][4].getPawn().getType() == PawnType.MOSCOVITE 
						|| board[xBoard][5].getPawn().getType() == PawnType.MOSCOVITE 
						|| board[xBoard - 1][4].getPawn().getType() == PawnType.MOSCOVITE)
				{
					return false;
				}else 
				{
					return true;
				}
			}
		}else if (aVerif.getyDep() == aVerif.getyArr())
		{
			if (aVerif.getyDep() == 1 || aVerif.getyDep() == 0)
			{
				if (board[3][0].getPawn().getType() == PawnType.MOSCOVITE 
						|| board[4][0].getPawn().getType() == PawnType.MOSCOVITE 
						|| board[5][0].getPawn().getType() == PawnType.MOSCOVITE 
						|| board[4][1].getPawn().getType() == PawnType.MOSCOVITE)
				{
					return false;
				}else 
				{
					return true;
				}
			}else if (aVerif.getxDep() == xBoard || aVerif.getxDep() == xBoard - 1)
			{

				if (board[3][0].getPawn().getType() == PawnType.MOSCOVITE 
						|| board[4][0].getPawn().getType() == PawnType.MOSCOVITE 
						|| board[5][0].getPawn().getType() == PawnType.MOSCOVITE 
						|| board[4][1].getPawn().getType() == PawnType.MOSCOVITE)
				{
					return false;
				}else 
				{
					return true;
				}
			}
		}
		return false;
	}

	public Block[][] cpy() {
		Block[][] plat = board.clone();
		for (int i = 0; i < xBoard; i++) {
			plat[i] = board[i].clone();
		}
		return plat;
	}

	public int getxBoard()
	{
		return xBoard;	
	}

	public int getyBoard()
	{
		return yBoard;	
	}

	public void setXBoard(int x) {
		this.xBoard = x;
	}

	public void setYBoard(int y) {
		this.yBoard = y;
	}

	public Parameter GetParameter()
	{
		return parameter;	
	}

	public void SetParameter(Parameter p)
	{
		Board.parameter=p;	
	}

	public void SetBlocks(Block[][] b)
	{
		this.board = b;
	}

	public Array<Block> getBlocks() {
		Array<Block> res = new Array<Block>();
		for(int i =0; i < xBoard; i++)
		{
			for(int j = 0; j < yBoard; j++)
			{
				res.add(board[i][j]);
			}
		}
		return res;
	}

	public Block[][] getBlock() {
		return board;

	}

	public int GetPosKing()
	{


		for(int i =0; i < xBoard; i++)
		{
			for(int j = 0; j < yBoard; j++)
			{
				if (board[i][j].getPawn().getType() == PawnType.SUEDOIS 
						&& board[i][j].getPawn().getTypesuede() == TypeSuedois.KING)
					return (i*10+j);
			}

		}
		return 0;

	}

	public static Parameter getParameter() {
		return parameter;
	}

	public static void setParameter(Parameter parameter) {
		Board.parameter = parameter;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public Historique getHisto() {
		return histo;
	}


}
