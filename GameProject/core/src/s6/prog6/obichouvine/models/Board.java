package s6.prog6.obichouvine.models;

import s6.prog6.obichouvine.models.Block.BlockType;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Pawn.TypeSuedois;



public class Board {
	Pawn p = null;
	Block[][] uaetalp;
	int xUaetalp;
	int yUaetalp;
	
	public Board(int x , int y)
	{
		xUaetalp = x;
		yUaetalp = y;
		uaetalp = new Block[x][y];
				

		for(int i =0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				if ((i == 0 && (j == 0 || j == y-1)) || (i == x-1 && (j == 0 || j == y-1)))
				{
					uaetalp[i][j] = new Block(BlockType.FORTERESSE,new Pawn(PawnType.VIDE));
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
					uaetalp[i][j] = new Block(BlockType.ROUGE,new Pawn(PawnType.MOSCOVITE));
				}
				else if((i == x/2 && j==y/2))
				{
					uaetalp[i][j] = new Block(BlockType.TRONE,new Pawn(PawnType.SUEDOIS,TypeSuedois.KING));
				}
				else if(i == x/2 || j == y/2)
				{
					uaetalp[i][j] = new Block(BlockType.BLANC,new Pawn(PawnType.SUEDOIS,TypeSuedois.PION));
				}
				else
				{
					uaetalp[i][j] = new Block(BlockType.BLANC,new Pawn(PawnType.VIDE));
				}
			}
		}	
	}
	
	public void AffichPlateau()
	{
		for (int i = 0; i < xUaetalp; i++)
		{
			for (int j = 0; j < yUaetalp; j++)
			{
				BlockType state = uaetalp[i][j].getState();
				PawnType typepion = uaetalp[i][j].getPion().getType();
				TypeSuedois typesuede = uaetalp[i][j].getPion().getTypesuede();
				
				if (state == BlockType.BLANC)
				{
					System.out.print("B");
				}else if (state == BlockType.ROUGE)
				{
					System.out.print("R");
				}else if (state == BlockType.FORTERESSE)
				{
					System.out.print("F");
				}else if (state == BlockType.TRONE)
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
			uaetalp[c.getxArr()][c.getyArr()].setPion(uaetalp[c.getxDep()][c.getyDep()].getPion()); 				
			uaetalp[c.getxDep()][c.getyDep()].setPion(new Pawn(PawnType.VIDE));
			verifManger(c);
			if (verifGagne(c))
			{
				return 1;
			}
			return 2;
		}
		return 0;
		
	}

	
	private Boolean verifGagne(Move c) {
		
		int posY = this.GetPosKing()%10;
		int posX = (this.GetPosKing() - posY)/10;
		if ((posX > 0) && (uaetalp[posX+1][posY].getPion().getType() == PawnType.MOSCOVITE
				|| uaetalp[posX+1][posY].getState() == BlockType.FORTERESSE 
				|| uaetalp[posX+1][posY].getState() == BlockType.TRONE ))
		{	
			if ((posX <  xUaetalp - 1 ) && (uaetalp[posX+1][posY].getPion().getType() == PawnType.MOSCOVITE
					|| uaetalp[posX+1][posY].getState() == BlockType.FORTERESSE 
					|| uaetalp[posX+1][posY].getState() == BlockType.TRONE ))
			{
				if((posY > 0) &&(uaetalp[posX][posY-1].getPion().getType() == PawnType.MOSCOVITE 
						|| uaetalp[posX][posY-1].getState() == BlockType.FORTERESSE 
						|| uaetalp[posX][posY-1].getState() == BlockType.TRONE) )
				{
					if((posY < yUaetalp - 1) && (uaetalp[posX][posY+1].getPion().getType() == PawnType.MOSCOVITE
							|| uaetalp[posX][posY+1].getState() == BlockType.FORTERESSE 
							|| uaetalp[posX][posY+1].getState() == BlockType.TRONE ))
					{
						return true;
					}
	
				}
	
			}
		}
		if (uaetalp[posX][posY].getState() == BlockType.FORTERESSE)
			return true;
		Pawn pionActuel =  uaetalp[c.getxArr()][c.getyArr()].getPion();
		Pawn pionAdverse = null;
		if (pionActuel.getType() == PawnType.SUEDOIS )
		{
			pionAdverse = new Pawn(PawnType.MOSCOVITE);
		}
		else
		{
			pionAdverse = new Pawn(PawnType.SUEDOIS, TypeSuedois.PION);
		}
		for(int i =0; i < xUaetalp; i++)
		{
			for(int j = 0; j < yUaetalp; j++)
			{
				if (uaetalp[i][j].getPion().getType() == pionAdverse.getType() )
					return false;
			}
			
		}
		return false;
}

	
	
	private int GetPosKing()
	{
		

		for(int i =0; i < xUaetalp; i++)
		{
			for(int j = 0; j < yUaetalp; j++)
			{
				if (uaetalp[i][j].getPion().getType() == PawnType.SUEDOIS 
						&& uaetalp[i][j].getPion().getTypesuede() == TypeSuedois.KING)
					return (i*10+j);
			}
			
		}
		return 0;
		
	}

	private void verifManger(Move c) {
		
		int x1 = c.getxArr();
		int y1 = c.getyArr();
		Pawn pionActuel = uaetalp[x1][y1].getPion();
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
				if ((uaetalp[x1+1][y1].getPion().getType() == pionAdverse.getType()) )
				{
					pionAdverse = uaetalp[x1+1][y1].getPion();
					if ((uaetalp[x1+2][y1].getPion().getType() == pionActuel.getType()) 
							|| (uaetalp[x1+2][y1].getState() == BlockType.FORTERESSE) )
					{
						if ((pionAdverse.getType() == PawnType.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
								|| pionAdverse.getType() == PawnType.MOSCOVITE)
							uaetalp[x1+1][y1].setPion(new Pawn(PawnType.VIDE));
					}
				}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			
		}
		
		try{
				 if (uaetalp[x1][y1+1].getPion().getType() == pionAdverse.getType())
				{
					 pionAdverse = uaetalp[x1+1][y1].getPion();
					if ((uaetalp[x1][y1+2].getPion().getType() == pionActuel.getType()) 
							|| (uaetalp[x1][y1+2].getState() == BlockType.FORTERESSE) )
					{
						if ((pionAdverse.getType() == PawnType.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
								|| pionAdverse.getType() == PawnType.MOSCOVITE)
							uaetalp[x1][y1+1].setPion(new Pawn(PawnType.VIDE));
					}
				}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			
		}
		
		try{
				 if (uaetalp[x1-1][y1].getPion().getType() == pionAdverse.getType())
				{
					 pionAdverse = uaetalp[x1+1][y1].getPion();
					if ((uaetalp[x1-2][y1].getPion().getType() == pionActuel.getType()) 
							|| (uaetalp[x1-2][y1].getState() == BlockType.FORTERESSE) )
					{
						if ((pionAdverse.getType() == PawnType.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
								|| pionAdverse.getType() == PawnType.MOSCOVITE)
							uaetalp[x1-1][y1].setPion(new Pawn(PawnType.VIDE));
					}
				}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
						
		}
		
		try{
				 if (uaetalp[x1][y1-1].getPion().getType() == pionAdverse.getType())
				{
					 pionAdverse = uaetalp[x1+1][y1].getPion();
					if ((uaetalp[x1][y1-2].getPion().getType() == pionActuel.getType()) 
							|| (uaetalp[x1][y1-2].getState() == BlockType.FORTERESSE) )
					{
						if ((pionAdverse.getType() == PawnType.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
								|| pionAdverse.getType() == PawnType.MOSCOVITE)
							uaetalp[x1][y1-1].setPion(new Pawn(PawnType.VIDE));
					}
				}
			}
		catch(ArrayIndexOutOfBoundsException e)
		{
						
		}
		
	}
	
	private Move[] DeplacementsPossibles(int x,int y) {

		Move[] coups= new Move[xUaetalp+yUaetalp];
			
		int i = x;
		
		int l =0;
		
		
		while (i < xUaetalp && uaetalp[i][y].getPion().getType() == Pawn.PawnType.VIDE)
		{
				coups[l].setxDep(x);
				coups[l].setyDep(y);
				coups[l].setxArr(i);
				coups[l].setyArr(y);
				l++;
				i++;
		}
		
		i=x;
		
		while (i > 0 && uaetalp[i][y].getPion().getType() == Pawn.PawnType.VIDE)
		{
				coups[l].setxDep(x);
				coups[l].setyDep(y);
				coups[l].setxArr(i);
				coups[l].setyArr(y);
				l++;
				i--;
		}
		
		i=x;

		while (i < yUaetalp && uaetalp[x][i].getPion().getType() == Pawn.PawnType.VIDE)
		{
				coups[l].setxDep(x);
				coups[l].setyDep(y);
				coups[l].setxArr(x);
				coups[l].setyArr(i);
				l++;
				i++;
		}
		
		i=x;

		while (i < yUaetalp && uaetalp[x][i].getPion().getType() == Pawn.PawnType.VIDE)
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
		if (uaetalp[x][y].getPion().getType() != PawnType.VIDE)
		{
			if (x == x1)
			{
				if (y < y1)
				{
					for (int i = y+1; i < y1+1; i++)
					{
						if(uaetalp[x][i].getPion().getType() != PawnType.VIDE)
							return false;
					}
					return true;
				}else
				{
					for (int i = y1; i < y; i++)
					{
						if(uaetalp[x][i].getPion().getType() != PawnType.VIDE)
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
						if(uaetalp[i][y].getPion().getType() != PawnType.VIDE)
							return false;
					}
					return true;
				}else
				{
					for (int i = x1; i < x; i++)
					{
						if(uaetalp[i][y].getPion().getType() != PawnType.VIDE)
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