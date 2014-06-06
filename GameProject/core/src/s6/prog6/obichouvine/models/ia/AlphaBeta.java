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

public class AlphaBeta extends IA {
	public AlphaBeta(IaType t, PawnType c) {
		super(t, c);
		// TODO Auto-generated constructor stub
	}
	
	public AlphaBeta(IaType t, PawnType c, String pseudo) {
		super(t, c, pseudo);
		// TODO Auto-generated constructor stub
	}


	public Move jouer(Board p) {

		int maxVal = Integer.MIN_VALUE;
		int val = 0;
		LinkedList<Move> lCoup = new LinkedList<Move>();
		ListIterator<Move> itCoup = lCoup.listIterator();
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
						val = alphaBeta(p, profondeur-1, Integer.MIN_VALUE, Integer.MAX_VALUE,false)
								+ evalCoup(p, c[k], mange, profondeur);
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
		System.out.println("Valeur d'évaluation : "+maxVal);
		return lCoup.get(gen.nextInt(lCoup.size()));
	}
	
	public int alphaBeta(Board p,int prof,int alpha,int beta, boolean joueurMax){
		if(prof == 0) {
			return evalFinal(p);
		}
		else if(joueurMax) {
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
							int val = alphaBeta(p, prof-1, alpha, beta, false)+evalCoup(p, c[k], mange, prof);
							alpha = Math.max(alpha, val);
							 // Suppression de la simulation
							demanger(plateau, camp, mange, c[k]);
							p.deplacementsansverif(new Move(c[k].getxArr(),c[k].getyArr(), c[k].getxDep(), c[k].getyDep()));
							if(beta <= alpha)
								return alpha;
						}
					}
				}
			}
			return alpha;
		}
		else {
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
							int val = alphaBeta(p, prof-1, alpha, beta, true)-evalCoup(p, c[k], mange, prof);
							beta = Math.min(beta, val);
							// Suppression de la simulation
							demanger(plateau, adv, mange, c[k]);
							p.deplacementsansverif(new Move(c[k].getxArr(),c[k].getyArr(), c[k].getxDep(), c[k].getyDep()));
							if(beta <= alpha)
								 return beta;
						}
					}
				}
			}
			return beta;
		}
	}
	
	//gagner/perdre : 10000
	//manger un pion/perdre un pion : 10
	//distance roi/coin (combien de deplacement pour atteindre)(40-7/coup)
	//le roi est entourré de 1/2/3 (2/5/25)
	//nombre de pion allié autour du roi nombre de pion alliée autour du pion
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
	private int evalCoup(Board p, Move c, int mange, int prof) {
		int ret = 0;
		if(p.getBlock()[c.getxArr()][c.getyArr()].getPawn().getType() == this.camp) {
			if ((mange & 1) == 1) {
				ret += valManger + prof;
			}
			if ((mange & 2) == 2) {
				ret += valManger + prof;
			}
			if ((mange & 4) == 4) {
				ret += valManger + prof;
			}
			if ((mange & 8) == 8) {
				ret += valManger + prof;
			}
		}
		else {
			if ((mange & 1) == 1) {
				ret += valPerdre + prof;
			}
			if ((mange & 2) == 2) {
				ret += valPerdre + prof;
			}
			if ((mange & 4) == 4) {
				ret += valPerdre + prof;
			}
			if ((mange & 8) == 8) {
				ret += valPerdre + prof;
			}
		}
		if(this.bougerRoi
				&& p.getBlock()[c.getxArr()][c.getyArr()].getPawn().getType() == PawnType.SUEDOIS
				&& p.getBlock()[c.getxArr()][c.getyArr()].getPawn().getTypesuede() == TypeSuedois.KING) {
			/*int xProxDep;
			int yProxDep;
			if(c.getxDep() < 5)
				xProxDep = 0;
			else
				xProxDep = 8;
			if(c.getyDep() < 5)
				yProxDep = 0;
			else
				yProxDep = 8;
			int xProxArr;
			int yProxArr;
			if(c.getxArr() < 5)
				xProxArr = 0;
			else
				xProxArr = 8;
			if(c.getyArr() < 5)
				yProxArr = 0;
			else
				yProxArr = 8;
			double distDep = Math.sqrt(Math.pow((c.getxDep() - xProxDep), 2)+Math.pow((c.getyDep() - yProxDep), 2));
			double distArr = Math.sqrt(Math.pow((c.getxArr() - xProxArr), 2)+Math.pow((c.getyArr() - yProxArr), 2));
			ret += (int)((distArr-distDep)/2);*/
			ret+=1;
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
