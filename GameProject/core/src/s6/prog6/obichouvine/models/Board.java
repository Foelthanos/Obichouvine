package s6.prog6.obichouvine.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import s6.prog6.obichouvine.models.Block.BlockState;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Pawn.TypeSuedois;



public class Board {
	Pawn p = null;
	public Block[][] board;
	public int offsetX, offsetY, xBoard, yBoard;
	
	public Board(int x ,int y)
	{
		xBoard = x;
		yBoard = y;
		board = new Block[x][y];
				
		offsetX = (int) (Gdx.graphics.getWidth()/2 - (x*Block.SIZE)/2);
		offsetY = (int) (Gdx.graphics.getHeight()/2 - (y*Block.SIZE)/2);
		
		
		for(int i =0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
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
		}	
	}
	
	public void AffichPlateau()
	{
		for (int i = 0; i < xBoard; i++)
		{
			for (int j = 0; j < yBoard; j++)
			{
				BlockState state = board[i][j].getState();
				PawnType typepion = board[i][j].getPion().getType();
				TypeSuedois typesuede = board[i][j].getPion().getTypesuede();
				
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
	
	
	public int Deplacement(Move c)
	{
		if (verifDeplacment(c))
		{
			board[c.getxArr()][c.getyArr()].setPion(board[c.getxDep()][c.getyDep()].getPion()); 				
			board[c.getxDep()][c.getyDep()].setPion(new Pawn(PawnType.VIDE));
			verifManger(c);
			if (verifGagne(c))
			{
				return 1;
			}
			return 2;
		}
		return 0;
		
	}

	public Array<Block> getBlocks() {
		// TODO Auto-generated method stub
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
	
	private Boolean verifGagne(Move c) {
		
		int posY = this.GetPosKing()%10;
		int posX = (this.GetPosKing() - posY)/10;
		if ((posX > 0) && (board[posX+1][posY].getPion().getType() == PawnType.MOSCOVITE
				|| board[posX+1][posY].getState() == BlockState.FORTERESSE 
				|| board[posX+1][posY].getState() == BlockState.TRONE ))
		{	
			if ((posX <  xBoard - 1 ) && (board[posX+1][posY].getPion().getType() == PawnType.MOSCOVITE
					|| board[posX+1][posY].getState() == BlockState.FORTERESSE 
					|| board[posX+1][posY].getState() == BlockState.TRONE ))
			{
				if((posY > 0) &&(board[posX][posY-1].getPion().getType() == PawnType.MOSCOVITE 
						|| board[posX][posY-1].getState() == BlockState.FORTERESSE 
						|| board[posX][posY-1].getState() == BlockState.TRONE) )
				{
					if((posY < yBoard - 1) && (board[posX][posY+1].getPion().getType() == PawnType.MOSCOVITE
							|| board[posX][posY+1].getState() == BlockState.FORTERESSE 
							|| board[posX][posY+1].getState() == BlockState.TRONE ))
					{
						return true;
					}
	
				}
	
			}
		}
		if (board[posX][posY].getState() == BlockState.FORTERESSE)
			return true;
		Pawn pionActuel =  board[c.getxArr()][c.getyArr()].getPion();
		Pawn pionAdverse = null;
		if (pionActuel.getType() == PawnType.SUEDOIS )
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
				if (board[i][j].getPion().getType() == pionAdverse.getType() )
					return false;
			}
			
		}
		return false;
}

	
	
	private int GetPosKing()
	{
		

		for(int i =0; i < xBoard; i++)
		{
			for(int j = 0; j < yBoard; j++)
			{
				if (board[i][j].getPion().getType() == PawnType.SUEDOIS 
						&& board[i][j].getPion().getTypesuede() == TypeSuedois.KING)
					return (i*10+j);
			}
			
		}
		return 0;
		
	}

	private void verifManger(Move c) {
		
		int x1 = c.getxArr();
		int y1 = c.getyArr();
		Pawn pionActuel = board[x1][y1].getPion();
		Pawn pionAdverse = null;

		if (pionActuel.getType() == PawnType.SUEDOIS )
		{
			pionAdverse = new Pawn(PawnType.MOSCOVITE);
		}
		else
		{
			pionAdverse = new Pawn(PawnType.SUEDOIS, TypeSuedois.PION);
		}
		
		
		try{
				if ((board[x1+1][y1].getPion().getType() == pionAdverse.getType()) )
				{
					pionAdverse = board[x1+1][y1].getPion();
					if ((board[x1+2][y1].getPion().getType() == pionActuel.getType()) 
							|| (board[x1+2][y1].getState() == BlockState.FORTERESSE) )
					{
						if ((pionAdverse.getType() == PawnType.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
								|| pionAdverse.getType() == PawnType.MOSCOVITE)
							board[x1+1][y1].setPion(new Pawn(PawnType.VIDE));
					}
				}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			
		}
		
		try{
				 if (board[x1][y1+1].getPion().getType() == pionAdverse.getType())
				{
					 pionAdverse = board[x1+1][y1].getPion();
					if ((board[x1][y1+2].getPion().getType() == pionActuel.getType()) 
							|| (board[x1][y1+2].getState() == BlockState.FORTERESSE) )
					{
						if ((pionAdverse.getType() == PawnType.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
								|| pionAdverse.getType() == PawnType.MOSCOVITE)
							board[x1][y1+1].setPion(new Pawn(PawnType.VIDE));
					}
				}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			
		}
		
		try{
				 if (board[x1-1][y1].getPion().getType() == pionAdverse.getType())
				{
					 pionAdverse = board[x1+1][y1].getPion();
					if ((board[x1-2][y1].getPion().getType() == pionActuel.getType()) 
							|| (board[x1-2][y1].getState() == BlockState.FORTERESSE) )
					{
						if ((pionAdverse.getType() == PawnType.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
								|| pionAdverse.getType() == PawnType.MOSCOVITE)
							board[x1-1][y1].setPion(new Pawn(PawnType.VIDE));
					}
				}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
						
		}
		
		try{
				 if (board[x1][y1-1].getPion().getType() == pionAdverse.getType())
				{
					 pionAdverse = board[x1+1][y1].getPion();
					if ((board[x1][y1-2].getPion().getType() == pionActuel.getType()) 
							|| (board[x1][y1-2].getState() == BlockState.FORTERESSE) )
					{
						if ((pionAdverse.getType() == PawnType.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
								|| pionAdverse.getType() == PawnType.MOSCOVITE)
							board[x1][y1-1].setPion(new Pawn(PawnType.VIDE));
					}
				}
			}
		catch(ArrayIndexOutOfBoundsException e)
		{
						
		}
		
	}
	
	private Move[] DeplacementsPossibles(int x,int y) {

		Move[] coups= new Move[xBoard+yBoard];
			
		int i = x;
		
		int l =0;
		
		
		while (i < xBoard && board[i][y].getPion().getType() == Pawn.PawnType.VIDE)
		{
				coups[l].setxDep(x);
				coups[l].setyDep(y);
				coups[l].setxArr(i);
				coups[l].setyArr(y);
				l++;
				i++;
		}
		
		i=x;
		
		while (i > 0 && board[i][y].getPion().getType() == Pawn.PawnType.VIDE)
		{
				coups[l].setxDep(x);
				coups[l].setyDep(y);
				coups[l].setxArr(i);
				coups[l].setyArr(y);
				l++;
				i--;
		}
		
		i=x;

		while (i < yBoard && board[x][i].getPion().getType() == Pawn.PawnType.VIDE)
		{
				coups[l].setxDep(x);
				coups[l].setyDep(y);
				coups[l].setxArr(x);
				coups[l].setyArr(i);
				l++;
				i++;
		}
		
		i=x;

		while (i < yBoard && board[x][i].getPion().getType() == Pawn.PawnType.VIDE)
		{
				coups[l].setxDep(x);
				coups[l].setyDep(y);
				coups[l].setxArr(x);
				coups[l].setyArr(i);
				l++;
				i--;
		}
		
		
		return coups;
		
	}

	

	private boolean verifDeplacment(Move c) {
		int x = c.getxDep();
		int y = c.getyDep();
		int x1 = c.getxArr();
		int y1 = c.getyArr();
		if (board[x][y].getPion().getType() != PawnType.VIDE)
		{
			if (x == x1)
			{
				if (y < y1)
				{
					for (int i = y+1; i < y1+1; i++)
					{
						if(board[x][i].getPion().getType() != PawnType.VIDE)
							return false;
					}
					return true;
				}else
				{
					for (int i = y1; i < y; i++)
					{
						if(board[x][i].getPion().getType() != PawnType.VIDE)
							return false;
					}
					return true;
				}
				
			}else if (y == y1)
			{
				if (x < x1)
				{
					for (int i = x+1; i < x1+1; i++)
					{
						if(board[i][y].getPion().getType() != PawnType.VIDE)
							return false;
					}
					return true;
				}else
				{
					for (int i = x1; i < x; i++)
					{
						if(board[i][y].getPion().getType() != PawnType.VIDE)
							return false;
					}
					return true;
				}
			}
		}
		return false;
	}

	public static void main (String args[])
	{
		Board plat = new Board(9,9);
		plat.AffichPlateau();
		System.out.println("\n\n\n");
		int test = plat.Deplacement(new Move(0,2,0,1));
		System.out.println("test : " + test +"\n\n\n");
		plat.AffichPlateau();
	}
}