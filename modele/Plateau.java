package modele;

import modele.Case;
import modele.Coup;
import modele.Pion;
import modele.Case.TypeCase;
import modele.Pion.TypePion;
import modele.Pion.TypeSuedois;

public class Plateau {
	Case[][] uaetalp;
	int xUaetalp;
	int yUaetalp;
	
	public int getX() {
		return xUaetalp;
	}
	public int getY() {
		return yUaetalp;
	}
	public Case[][] getUaetalp() {
		return uaetalp;
	}
	
	
	public Plateau(int x , int y)
	{
		xUaetalp = x;
		yUaetalp = y;
		uaetalp = new Case[x][y];
				

		for(int i =0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				if ((i == 0 && (j == 0 || j == y-1)) || (i == x-1 && (j == 0 || j == y-1)))
				{
					uaetalp[i][j] = new Case(TypeCase.FORTERESSE,new Pion(TypePion.VIDE));
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
					uaetalp[i][j] = new Case(TypeCase.ROUGE,new Pion(TypePion.MOSCOVITE));
				}
				else if((i == x/2 && j==y/2))
				{
					uaetalp[i][j] = new Case(TypeCase.TRONE,new Pion(TypePion.SUEDOIS,TypeSuedois.KING));
				}
				else if(i == x/2 || j == y/2)
				{
					uaetalp[i][j] = new Case(TypeCase.BLANC,new Pion(TypePion.SUEDOIS,TypeSuedois.PION));
				}
				else
				{
					uaetalp[i][j] = new Case(TypeCase.BLANC,new Pion(TypePion.VIDE));
				}
			}
		}	
	}
	
	public void affichPlateau()
	{
		for (int i = 0; i < xUaetalp; i++)
		{
			for (int j = 0; j < yUaetalp; j++)
			{
				TypeCase state = uaetalp[i][j].getState();
				TypePion typepion = uaetalp[i][j].getPion().getType();
				TypeSuedois typesuede = uaetalp[i][j].getPion().getTypesuede();
				
				if (state == TypeCase.BLANC)
				{
					System.out.print("B");
				}else if (state == TypeCase.ROUGE)
				{
					System.out.print("R");
				}else if (state == TypeCase.FORTERESSE)
				{
					System.out.print("F");
				}else if (state == TypeCase.TRONE)
				{
					System.out.print("T");
				}
				if (typepion == TypePion.MOSCOVITE)
				{
					System.out.print("M");
				}else if (typepion == TypePion.SUEDOIS && typesuede == TypeSuedois.PION)
				{
					System.out.print("S");
				}else if (typepion == TypePion.SUEDOIS && typesuede == TypeSuedois.KING)
				{
					System.out.print("K");
				}else if (typepion == TypePion.VIDE)
				{
					System.out.print(".");
				}
				System.out.print(" ");
			}
			System.out.println("");
		}
	}
	
	
	public int deplacement(Coup c)
	{
		if (verifDeplacment(c))
		{
			uaetalp[c.getxArr()][c.getyArr()].setPion(uaetalp[c.getxDep()][c.getyDep()].getPion()); 				
			uaetalp[c.getxDep()][c.getyDep()].setPion(new Pion(TypePion.VIDE));
			verifManger(c);
			if (verifGagne(c))
			{
				return 4;
			}
			if (verifRaishiTuishi(c) == 0)
				return 5;
			return verifRaishiTuishi (c);
		}
		return 3;
	}
	public int deplacementsansverif(Coup c) //optimiser pour IA
	{	
			uaetalp[c.getxArr()][c.getyArr()].setPion(uaetalp[c.getxDep()][c.getyDep()].getPion()); 				
			uaetalp[c.getxDep()][c.getyDep()].setPion(new Pion(TypePion.VIDE));
			int manger = verifManger(c);
			return manger;
	}
	
	private boolean verifDeplacment(Coup c) {
		int x = c.getxDep();
		int y = c.getyDep();
		int x1 = c.getxArr();
		int y1 = c.getyArr();
		if (uaetalp[x][y].getPion().getType() != TypePion.VIDE)
		{
			if (x == x1)
			{
				if (y < y1)
				{
					for (int i = y+1; i < y1+1; i++)
					{
						if(uaetalp[x][i].getPion().getType() != TypePion.VIDE)
							return false;
					}
					return true;
				}else
				{
					for (int i = y1; i < y; i++)
					{
						if(uaetalp[x][i].getPion().getType() != TypePion.VIDE)
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
						if(uaetalp[i][y].getPion().getType() != TypePion.VIDE)
							return false;
					}
					return true;
				}else
				{
					for (int i = x1; i < x; i++)
					{
						if(uaetalp[i][y].getPion().getType() != TypePion.VIDE)
							return false;
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public int verifRaishiTuishi (Coup c){
		int posKing = GetPosKing();
		Coup[] p  = getDeplacementsPossibles((posKing-posKing%10)/10, posKing%10);
		int j = 0;
		for(int i = 0; i <  p.length; i++)
		{
			if(uaetalp[p[i].getxArr()][p[i].getyArr()].getState() == TypeCase.FORTERESSE)
				j++;
				
		}
		return j;
		
	}
	
	public Boolean verifGagne(Coup c) {
		
		int posY = this.GetPosKing()%10;
		int posX = (this.GetPosKing() - posY)/10;
		if ((posX > 0) && (uaetalp[posX+1][posY].getPion().getType() == TypePion.MOSCOVITE
				|| uaetalp[posX+1][posY].getState() == TypeCase.FORTERESSE 
				|| uaetalp[posX+1][posY].getState() == TypeCase.TRONE ))
		{	
			if ((posX <  xUaetalp - 1 ) && (uaetalp[posX+1][posY].getPion().getType() == TypePion.MOSCOVITE
					|| uaetalp[posX+1][posY].getState() == TypeCase.FORTERESSE 
					|| uaetalp[posX+1][posY].getState() == TypeCase.TRONE ))
			{
				if((posY > 0) &&(uaetalp[posX][posY-1].getPion().getType() == TypePion.MOSCOVITE 
						|| uaetalp[posX][posY-1].getState() == TypeCase.FORTERESSE 
						|| uaetalp[posX][posY-1].getState() == TypeCase.TRONE) )
				{
					if((posY < yUaetalp - 1) && (uaetalp[posX][posY+1].getPion().getType() == TypePion.MOSCOVITE
							|| uaetalp[posX][posY+1].getState() == TypeCase.FORTERESSE 
							|| uaetalp[posX][posY+1].getState() == TypeCase.TRONE ))
					{
						return true;
					}
	
				}
	
			}
		}
		if (uaetalp[posX][posY].getState() == TypeCase.FORTERESSE)
			return true;
		Pion pionActuel =  uaetalp[c.getxArr()][c.getyArr()].getPion();
		Pion pionAdverse = null;
		if (pionActuel.getType() == TypePion.SUEDOIS )
		{
			pionAdverse = new Pion(TypePion.MOSCOVITE);
		}
		else
		{
			pionAdverse = new Pion(TypePion.SUEDOIS, TypeSuedois.PION);
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
				if (uaetalp[i][j].getPion().getType() == TypePion.SUEDOIS 
						&& uaetalp[i][j].getPion().getTypesuede() == TypeSuedois.KING)
					return (i*10+j);
			}
			
		}
		return 0;
		
	}

	private void verifManger(Coup c) {
		
		int x1 = c.getxArr();
		int y1 = c.getyArr();
		Pion pionActuel = uaetalp[x1][y1].getPion();
		Pion pionAdverse = null;

		if (pionActuel.getType() == TypePion.SUEDOIS )
		{
			pionAdverse = new Pion(TypePion.MOSCOVITE);
		}
		else
		{
			pionAdverse = new Pion(TypePion.SUEDOIS, TypeSuedois.PION);
		}
		
		
		try{
				if ((uaetalp[x1+1][y1].getPion().getType() == pionAdverse.getType()) )
				{
					pionAdverse = uaetalp[x1+1][y1].getPion();
					if ((uaetalp[x1+2][y1].getPion().getType() == pionActuel.getType()) 
							|| (uaetalp[x1+2][y1].getState() == TypeCase.FORTERESSE) )
					{
						if ((pionAdverse.getType() == TypePion.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
								|| pionAdverse.getType() == TypePion.MOSCOVITE)
							uaetalp[x1+1][y1].setPion(new Pion(TypePion.VIDE));
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
							|| (uaetalp[x1][y1+2].getState() == TypeCase.FORTERESSE) )
					{
						if ((pionAdverse.getType() == TypePion.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
								|| pionAdverse.getType() == TypePion.MOSCOVITE)
							uaetalp[x1][y1+1].setPion(new Pion(TypePion.VIDE));
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
							|| (uaetalp[x1-2][y1].getState() == TypeCase.FORTERESSE) )
					{
						if ((pionAdverse.getType() == TypePion.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
								|| pionAdverse.getType() == TypePion.MOSCOVITE)
							uaetalp[x1-1][y1].setPion(new Pion(TypePion.VIDE));
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
							|| (uaetalp[x1][y1-2].getState() == TypeCase.FORTERESSE) )
					{
						if ((pionAdverse.getType() == TypePion.SUEDOIS && pionAdverse.getTypesuede() != TypeSuedois.KING) 
								|| pionAdverse.getType() == TypePion.MOSCOVITE)
							uaetalp[x1][y1-1].setPion(new Pion(TypePion.VIDE));
					}
				}
			}
		catch(ArrayIndexOutOfBoundsException e)
		{
						
		}
		
	}
	
	public Coup[] getDeplacementsPossibles(int x,int y) {

		Coup[] coups= new Coup[xUaetalp+yUaetalp];
		
		int l = 0;
		
		int i = x+1;
		while (i < xUaetalp-1 && uaetalp[i][y].getPion().getType() == TypePion.VIDE)
		{
			if (uaetalp[i][y].getState() == TypeCase.FORTERESSE 
					|| uaetalp[i][y].getState() == TypeCase.TRONE)
			{
				if( uaetalp[x][y].getPion().getType() == TypePion.SUEDOIS 
				&& uaetalp[x][y].getPion().getTypesuede() == TypeSuedois.KING)
				{
					coups[l] = new Coup(x, y, i, y);
					l++;
				}
			}else
			{
				coups[l] = new Coup(x, y, i, y);
				l++;
			}
			i++;
		}
		
		i=x-1;
		
		while (i > -1 && uaetalp[i][y].getPion().getType() == TypePion.VIDE)
		{
			if (uaetalp[i][y].getState() == TypeCase.FORTERESSE 
					|| uaetalp[i][y].getState() == TypeCase.TRONE)
			{
				if( uaetalp[x][y].getPion().getType() == TypePion.SUEDOIS 
				&& uaetalp[x][y].getPion().getTypesuede() == TypeSuedois.KING)
				{
					coups[l] = new Coup(x, y, i, y);
					l++;
				}
			}else
			{
				coups[l] = new Coup(x, y, i, y);
				l++;
			}
			i--;
		}
		
		i=y-1;

		while (i > -1 && uaetalp[x][i].getPion().getType() == TypePion.VIDE)
		{
			if (uaetalp[x][i].getState() == TypeCase.FORTERESSE 
					|| uaetalp[x][i].getState() == TypeCase.TRONE)
			{
				if( uaetalp[x][y].getPion().getType() == TypePion.SUEDOIS 
				|| uaetalp[x][y].getPion().getTypesuede() == TypeSuedois.KING)
				{
					coups[l] = new Coup(x, y, x, i);
					l++;
				}
			}else
			{
				coups[l] = new Coup(x, y, x, i);
				l++;
			}
			i--;
		}
		
		i=y+1;
		while (i < yUaetalp-1 && uaetalp[x][i].getPion().getType() == TypePion.VIDE )
		{
			if (uaetalp[x][i].getState() == TypeCase.FORTERESSE 
					|| uaetalp[x][i].getState() == TypeCase.TRONE)
			{
				if( uaetalp[x][y].getPion().getType() == TypePion.SUEDOIS 
				&& uaetalp[x][y].getPion().getTypesuede() == TypeSuedois.KING)
				{
					coups[l] = new Coup(x, y, x, i);
					l++;
				}
			}else
			{
				coups[l] = new Coup(x, y, x, i);
				l++;
			}
			i++;
		}
		
		Coup[] coupss= new Coup[l];
		for (int k = 0; k < l; k++)
		{
			coupss[k] = new Coup(coups[k].getxDep(), coups[k].getyDep(), coups[k].getxArr(), coups[k].getyArr());
		}
		return coupss;
		
	}

	

	private boolean verifDeplacment(Coup c) {
		int x = c.getxDep();
		int y = c.getyDep();
		int x1 = c.getxArr();
		int y1 = c.getyArr();
		if (uaetalp[x][y].getPion().getType() != TypePion.VIDE)
		{
			if (x == x1)
			{
				if (y < y1)
				{
					for (int i = y+1; i < y1+1; i++)
					{
						if(uaetalp[x][i].getPion().getType() != TypePion.VIDE)
							return false;
					}
					return true;
				}else
				{
					for (int i = y1; i < y; i++)
					{
						if(uaetalp[x][i].getPion().getType() != TypePion.VIDE)
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
						if(uaetalp[i][y].getPion().getType() != TypePion.VIDE)
							return false;
					}
					return true;
				}else
				{
					for (int i = x1; i < x; i++)
					{
						if(uaetalp[i][y].getPion().getType() != TypePion.VIDE)
							return false;
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public Case[][] cpy()
	{
		Case[][] plat = uaetalp.clone(); 
		
		for (int i=0; i< xUaetalp;i++)
		{
			plat[i] = uaetalp[i].clone();
		}
		return plat;
	}
}