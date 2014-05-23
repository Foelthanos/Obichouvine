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
		System.out.println(verifDeplacment(c));
		if (verifDeplacment(c))
		{
			board[c.getxArr()][c.getyArr()].setPawn(board[c.getxDep()][c.getyDep()].getPawn()); 				
			board[c.getxDep()][c.getyDep()].setPawn(new Pawn(PawnType.VIDE));
			manger(c);
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
		if (board[x][y].getPawn().getType() != PawnType.VIDE)
		{
			if (x == x1)
			{
				if (y < y1)
				{
					for (int i = y+1; i < y1+1; i++)
					{
						if(board[x][i].getPawn().getType() != PawnType.VIDE)
							return false;
					}
					return true;
				}else
				{
					for (int i = y1; i < y; i++)
					{
						if(board[x][i].getPawn().getType() != PawnType.VIDE)
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
						if(board[i][y].getPawn().getType() != PawnType.VIDE)
							return false;
					}
					return true;
				}else
				{
					for (int i = x1; i < x; i++)
					{
						if(board[i][y].getPawn().getType() != PawnType.VIDE)
							return false;
					}
					return true;
				}
			}
		}
		return false;
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
	
	public int verifRaishiTuishi (Move c){
		int posKing = GetPosKing();
		Move[] p  = deplacementsPossibles((posKing-posKing%10)/10, posKing%10);
		int j = 0;
		for(int i = 0; i <  p.length; i++)
		{
			if(board[p[i].getxArr()][p[i].getyArr()].getState() == BlockState.FORTERESSE)
				j++;
		}
		return j;
	}
	
	private Boolean verifGagne(Move c) {
		
		int posY = this.GetPosKing()%10;
		int posX = (this.GetPosKing() - posY)/10;
		if (posX == 0 
				|| ((posX > 0) && (board[posX+1][posY].getPawn().getType() == PawnType.MOSCOVITE
				|| board[posX+1][posY].getState() == BlockState.FORTERESSE 
				|| board[posX+1][posY].getState() == BlockState.TRONE )))
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
		if (board[posX][posY].getState() == BlockState.FORTERESSE)
			return true;
		Pawn pionActuel =  board[c.getxArr()][c.getyArr()].getPawn();
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
				if (board[i][j].getPawn().getType() == pionAdverse.getType() )
					return false;
			}
			
		}
		return false;
	}

	public boolean verifCoupGagnant(Move c) {
		if(board[c.getxDep()][c.getyDep()].getPawn().getType() == PawnType.SUEDOIS && board[c.getxDep()][c.getyDep()].getPawn().getTypesuede() == TypeSuedois.KING) {
			if(board[c.getxArr()][c.getyArr()].getState() == BlockState.FORTERESSE)
				return true;
		}
		else {
			int tmpX = c.getxArr();
			int tmpY = c.getyArr();
			int roiX = 0;
			int roiY = 0; 
			if(tmpX !=0 && board[tmpX-1][tmpY].getPawn().getTypesuede() == TypeSuedois.KING) {
				roiX = tmpX-1;
				roiY = tmpY;
			}
			else if(tmpY !=0 && board[tmpX][tmpY-1].getPawn().getTypesuede() == TypeSuedois.KING) {
				roiX = tmpX;
				roiY = tmpY-1;
			}
			else if(tmpX !=xBoard -1 && board[tmpX+1][tmpY].getPawn().getTypesuede() == TypeSuedois.KING) {
				roiX = tmpX+1;
				roiY = tmpY;
			}
			else if(tmpY != yBoard - 1 && board[tmpX][tmpY+1].getPawn().getTypesuede() == TypeSuedois.KING) {
				roiX = tmpX;
				roiY = tmpY+1;
			}
			if(roiX != 0 && roiY != 0){
				if(roiX ==0 ||board[roiX-1][roiY].getPawn().getType() == PawnType.MOSCOVITE 
						|| board[roiX-1][roiY].getState() == BlockState.FORTERESSE){
					if(roiY ==0 
							||board[roiX][roiY-1].getPawn().getType() == PawnType.MOSCOVITE 
							|| board[roiX][roiY-1].getState() == BlockState.FORTERESSE){
						if(roiX ==xBoard 
								||board[roiX+1][roiY].getPawn().getType() == PawnType.MOSCOVITE 
								|| board[roiX+1][roiY].getState() == BlockState.FORTERESSE){
							if(roiX ==yBoard 
									||board[roiX][roiY+1].getPawn().getType() == PawnType.MOSCOVITE 
									|| board[roiX][roiY+1].getState() == BlockState.FORTERESSE){
								return true;
							}
						}
					}
				}
			}
		}
		return false;
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

	public int manger(Move c) {
		
		int total = 0;
		int x1 = c.getxArr();
		int y1 = c.getyArr();
		Pawn pionActuel = board[x1][y1].getPawn();
		Pawn pionAdverse = null;

		if (pionActuel.getType() == PawnType.SUEDOIS )
		{
			pionAdverse = new Pawn(PawnType.MOSCOVITE);
		}
		else
		{
			pionAdverse = new Pawn(PawnType.SUEDOIS, TypeSuedois.PION);
		}
		
		if (x1 + 1 < xBoard && (board[x1+1][y1].getPawn().getType() == pionAdverse.getType()))
		{
			
			pionAdverse = board[x1+1][y1].getPawn();
			if (x1 + 2 < xBoard && (board[x1+2][y1].getPawn().getType() == pionActuel.getType()) 
					|| (board[x1+2][y1].getState() == BlockState.FORTERESSE) )
			{
				if ((pionAdverse.getType() == PawnType.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
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
			if (y1 + 2 < yBoard && (board[x1][y1 + 2].getPawn().getType() == pionActuel.getType()) 
					|| (board[x1][y1 + 2].getState() == BlockState.FORTERESSE) )
			{
				if ((pionAdverse.getType() == PawnType.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
						|| pionAdverse.getType() == PawnType.MOSCOVITE)
				{
					total +=  8;
					board[x1][y1 + 1].setPawn(new Pawn(PawnType.VIDE));
				}
			}
		}
		 
		 if (x1 - 1 > 0 && board[x1 - 1][y1].getPawn().getType() == pionAdverse.getType())
		{
			 
			 pionAdverse = board[x1 - 1][y1].getPawn();
			if (x1 - 2 > 0 && (board[x1 - 2][y1].getPawn().getType() == pionActuel.getType()) 
					|| (board[x1 - 2][y1].getState() == BlockState.FORTERESSE) )
			{
				if ((pionAdverse.getType() == PawnType.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
						|| pionAdverse.getType() == PawnType.MOSCOVITE)
				{
					total += 1;
					board[x1 - 1][y1].setPawn(new Pawn(PawnType.VIDE));
				}
			}
		}
		 if (y1 - 1 > 0 && board[x1][y1 - 1].getPawn().getType() == pionAdverse.getType())
		{
			 
			 pionAdverse = board[x1][y1 - 1].getPawn();
			if (y1 - 2 > 0 && (board[x1][y1 - 2].getPawn().getType() == pionActuel.getType()) 
					|| (board[x1][y1 - 2].getState() == BlockState.FORTERESSE) )
			{
				if ((pionAdverse.getType() == PawnType.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
						|| pionAdverse.getType() == PawnType.MOSCOVITE)
				{
					total += 2;
					board[x1][y1 - 1].setPawn(new Pawn(PawnType.VIDE));
				}
			}
		}
		 
		 
		
		return total;
		
	}
	
	private Move[] deplacementsPossibles(int x,int y) {

		Move[] coups= new Move[xBoard+yBoard];
			
	
		
		int l =0;
		
		int i = x + 1;
		while (i < xBoard && board[i][y].getPawn().getType() == Pawn.PawnType.VIDE)
		{
			if (board[i][y].getState() == BlockState.FORTERESSE
					|| board[i][y].getState() == BlockState.TRONE) {
				if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING) {
					coups[l] = new Move(x, y, i, y);
					l++;
				}
			}else
			{
				coups[l] = new Move(x, y, i, y);
				l++;
			}
			i++;
		}
		
		i = x - 1;
		
		while (i > -1 && board[i][y].getPawn().getType() == Pawn.PawnType.VIDE)
		{
			if (board[i][y].getState() == BlockState.FORTERESSE
					|| board[i][y].getState() == BlockState.TRONE) {
				if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING) {
					coups[l] = new Move(x, y, i, y);
					l++;
				}
			}else
			{
				coups[l] = new Move(x, y, i, y);
				l++;
			}
			i--;
		}
		
		i = y + 1;

		while (i < yBoard && board[x][i].getPawn().getType() == Pawn.PawnType.VIDE)
		{
			if (board[x][i].getState() == BlockState.FORTERESSE
					|| board[x][i].getState() == BlockState.TRONE) {
				if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING) {
					coups[l] = new Move(x, y, x, i);
					l++;
				}
			}else
			{
				coups[l] = new Move(x, y, x, i);
				l++;
			}
			i++;
		}
		
		i = y - 1;

		while (i > - 1 && board[x][i].getPawn().getType() == Pawn.PawnType.VIDE)
		{
			if (board[x][i].getState() == BlockState.FORTERESSE
					|| board[x][i].getState() == BlockState.TRONE) {
				if (board[x][y].getPawn().getType() == PawnType.SUEDOIS
						&& board[x][y].getPawn().getTypesuede() == TypeSuedois.KING) {
					coups[l] = new Move(x, y, x, i);
					l++;
				}
			}else
			{
				coups[l] = new Move(x, y, x, i);
				l++;
			}
			i--;
		}
		Move[] coupss = new Move[l];
		for (int k = 0; k < l; k++) {
				coupss[k] = new Move(coups[k].getxDep(), coups[k].getyDep(),coups[k].getxArr(), coups[k].getyArr());

		 		}
		
		return coupss;
	}
	
	public Block[][] cpy() {
		Block[][] plat = board.clone();
		for (int i = 0; i < xBoard; i++) {
			plat[i] = board[i].clone();
 		}
 		return plat;
	}
	public static void main (String args[])
	{
		Board plat = new Board(9,9);
		plat.AffichPlateau();
		System.out.println("\n\n\n");
		int test = plat.deplacement(new Move(0,2,0,1));
		System.out.println("test : " + test +"\n\n\n");
		plat.AffichPlateau();
	}
}