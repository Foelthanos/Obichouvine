package s6.prog6.obichouvine.models.ia;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import s6.prog6.obichouvine.models.Block;
import s6.prog6.obichouvine.models.Board;
import s6.prog6.obichouvine.models.Move;
import s6.prog6.obichouvine.models.Pawn;
import s6.prog6.obichouvine.models.Block.BlockState;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Pawn.TypeSuedois;

public class MiniMax extends IA{

	public MiniMax(int t, int p, PawnType c) {
		super(t, p, c);
		// TODO Auto-generated constructor stub
	}

	public Move jouer(Board p) {
		int maxVal = Integer.MIN_VALUE;
		int val = 0;
		LinkedList<Move> lCoup = new LinkedList<Move>();
		ListIterator<Move> itCoup = lCoup.listIterator();
		System.out.println("Coups possible de l'ia :"+lCoup);
		Block[][] plateau = p.getBlock();
		for (int i = 0; i < p.GetxBoard(); i++) {
			for (int j = 0; j < p.GetyBoard(); j++) {
				if (plateau[i][j].getPawn().getType() == camp) {
					Move[] c = p.deplacementsPossibles(i, j);
					for (int k = 0; k < c.length; k++) {
						if (p.verifCoupGagnant(c[k])) {
							return c[k];
						}
						// Simulation du coup actuel
						p.deplacementsansverif(c[k]);
						int mange = p.manger(c[k]);
						val = min(p, profondeur - 1) + evalCoup(p, c[k], mange);
						if (val > maxVal) {
							maxVal = val;
							lCoup.clear();
							itCoup = lCoup.listIterator();
							itCoup.add(c[k]);
						} else if (val == maxVal) {
							itCoup.add(c[k]);
						}
						// Suppression de la simulation
						demanger(plateau, camp, mange, c[k]);
						p.deplacementsansverif(new Move(c[k].getxArr(), c[k]
								.getyArr(), c[k].getxDep(), c[k].getyDep()));
					}
				}
			}
		}
		java.util.Random gen = new Random();
		return lCoup.get(gen.nextInt(lCoup.size()));
	}
	
	private int min(Board p, int profondeur) {
		if (profondeur == 0)
			return evalFinal(p);
		else {

			int minVal = Integer.MAX_VALUE;
			int val = 0;
			Block[][] plateau = p.getBlock();
			for (int i = 0; i < p.GetxBoard(); i++) {
				for (int j = 0; j < p.GetyBoard(); j++) {
					if (plateau[i][j].getPawn().getType() == adv) {
						Move[] c = p.deplacementsPossibles(i, j);
						for (int k = 0; k < c.length; k++) {
							if (p.verifCoupGagnant(c[k])) {
								return -10000;
							}
							// Simulation du coup actuel
							p.deplacementsansverif(c[k]);
							int mange = p.manger(c[k]);
							val = max(p, profondeur - 1)
									- evalCoup(p, c[k], mange);
							if (val < minVal) {
								minVal = val; // +si victoire 10000 si defaite
												// -10000 +pions manger*10
												// -pipons mangés +10
							}
							// Suppression de la simulation
							demanger(plateau, adv, mange, c[k]);
							p.deplacementsansverif(new Move(c[k].getxArr(),c[k].getyArr(), c[k].getxDep(), c[k].getyDep()));
						}
					}
				}
			}
			return minVal;
		}
		
		
	}
	private int max(Board p, int profondeur) {
		if (profondeur == 0)
			return evalFinal(p);
		else {
			int maxVal = Integer.MIN_VALUE;
			int val = 0;
			Block[][] plateau = p.getBlock();
			for (int i = 0; i < p.GetxBoard(); i++) {
				for (int j = 0; j < p.GetyBoard(); j++) {
					if (plateau[i][j].getPawn().getType() == camp) {
						Move[] c = p.deplacementsPossibles(i, j);
						for (int k = 0; k < c.length; k++) {
							if (p.verifCoupGagnant(c[k])) {
								return +10000;
							}
							// Simulation du coup actuel
							p.deplacementsansverif(c[k]);
							int mange = p.manger(c[k]);
							val = min(p, profondeur - 1)
									+ evalCoup(p, c[k], mange);
							if (val > maxVal) {
								maxVal = val;
							}
							// Suppression de la simulation
							demanger(plateau, camp, mange, c[k]);
							p.deplacementsansverif(new Move(c[k].getxArr(),c[k].getyArr(), c[k].getxDep(), c[k].getyDep()));
						}
					}
				}
			}
			return maxVal;
		}
	}
	/*
	 * gagner/perdre : 10000 manger un pion/perdre un pion : 10 distance
	 * roi/coin (combien de deplacement pour atteindre)(40-7/coup) le roi est
	 * entourré de 1/2/3 (2/5/25) nombre de pion allié autour du roi nombre de
	 * pion alliée autour du pion
	 */
	private void demanger(Block[][] plateau, PawnType dernier, int mange, Move c) {
		if (dernier == PawnType.SUEDOIS) {
			if ((mange & 1) == 1) 
			{
				plateau[c.getxArr() - 1][c.getyArr()].setPawn(new Pawn(PawnType.MOSCOVITE));
			}
			if ((mange & 2) == 2)
			{
				plateau[c.getxArr()][c.getyArr() - 1].setPawn(new Pawn(PawnType.MOSCOVITE));
			}
			if ((mange & 4) == 4) 
			{
				plateau[c.getxArr() + 1][c.getyArr()].setPawn(new Pawn(PawnType.MOSCOVITE));
			}
			if ((mange & 8) == 8) 
			{
				plateau[c.getxArr()][c.getyArr() + 1].setPawn(new Pawn(PawnType.MOSCOVITE));
			}
		} else { // si Moscovite
			if ((mange & 1) == 1)
			{
				plateau[c.getxArr() - 1][c.getyArr()].setPawn(new Pawn(PawnType.SUEDOIS, TypeSuedois.PION));
			}
			if ((mange & 2) == 2) 
			{
				plateau[c.getxArr()][c.getyArr() - 1].setPawn(new Pawn(PawnType.SUEDOIS, TypeSuedois.PION));
			}
			if ((mange & 4) == 4)
			{
				plateau[c.getxArr() + 1][c.getyArr()].setPawn(new Pawn(PawnType.SUEDOIS, TypeSuedois.PION));
			}
			if ((mange & 8) == 8) 
			{
				plateau[c.getxArr()][c.getyArr() + 1].setPawn(new Pawn(PawnType.SUEDOIS, TypeSuedois.PION));
			}
		}
	}
	private int evalCoup(Board p, Move c, int mange) {
		int ret = 0;
		if ((mange & 1) == 1) {
			ret += 10;
		}
		if ((mange & 2) == 2) {
			ret += 10;
		}
		if ((mange & 4) == 4) {
			ret += 10;
		}
		if ((mange & 8) == 8) {
			ret += 10;
		}
		return ret;
	}

	private int evalFinal(Board p) {
		int ret = 0;
		int roiX, roiY;
		int tmp = 0;
		Block[][] board = p.getBlock();
		roiX = p.GetPosKing();
		roiY = roiX % 10;
		roiX = roiX / 10;
		if (roiX != 0 && roiY != 0) {
			if (roiX == 0
					|| board[roiX - 1][roiY].getPawn().getType() == PawnType.MOSCOVITE
					|| board[roiX - 1][roiY].getState() == BlockState.FORTERESSE) {
				tmp++;
			}
			if (roiY == 0
					|| board[roiX][roiY - 1].getPawn().getType() == PawnType.MOSCOVITE
					|| board[roiX][roiY - 1].getState() == BlockState.FORTERESSE) {
				tmp++;
			}
			if (roiX == p.GetxBoard() - 1
					|| board[roiX + 1][roiY].getPawn().getType() == PawnType.MOSCOVITE
					|| board[roiX + 1][roiY].getState() == BlockState.FORTERESSE) {
				tmp++;
			}
			if (roiY == p.GetyBoard() - 1
					|| board[roiX][roiY + 1].getPawn().getType() == PawnType.MOSCOVITE
					|| board[roiX][roiY + 1].getState() == BlockState.FORTERESSE) {
				tmp++;

			}
		}
		if (camp == PawnType.SUEDOIS) {
			if (tmp == 1) {
				ret -= 2;
			} else if (tmp == 2) {
				ret -= 10;
			} else if (tmp == 3) {
				ret -= 30;
			}
		} else {
			if (tmp == 1) {
				ret += 2;
			} else if (tmp == 2) {
				ret += 10;
			} else if (tmp == 3) {
				ret += 30;
			}
		}
		return ret;
	}
}
