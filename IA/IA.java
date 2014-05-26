package ia;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import modele.Case;
import modele.Coup;
import modele.Pion;
import modele.Pion.TypeSuedois;
import modele.Plateau;
import modele.Case.TypeCase;
import modele.Pion.TypePion;

public class IA {
	int profondeur;
	int niveau;

	// 1 - Facile
	// 2 - Intermédiaire
	// 3 - Expert

	public IA(int n) {
		niveau = 3;
		profondeur = n;
	}

	public Coup jouer(Plateau p, int t) {
		if (niveau == 3)
			return jouerExp(p, t);
		else
			return null;
	}

	public Coup jouerExp(Plateau p, int t) {
		int[] tab = null;
		int maxVal = Integer.MIN_VALUE;
		int val = 0;
		LinkedList<Coup> lCoup = new LinkedList<Coup>();
		ListIterator<Coup> itCoup = lCoup.listIterator();
		Case[][] plateau = p.getUaetalp();
		for (int i = 0; i < p.getX(); i++) {
			for (int j = 0; j < p.getY(); j++) {
				if (plateau[i][j].getPion().getType().val == t) {
					Coup[] c = p.getDeplacementsPossibles(i, j);
					for (int k = 0; k < c.length; k++) {
						if (p.verifCoupGagnant(c[k])) {
							return c[k];
						}
						// Simulation du coup actuel
						p.deplacementsansverif(c[k]);
						int mange = p.manger(c[k]);
						val = min(p, t, profondeur-1)
								+ eval(p, t, t, c[k], mange);
						if (val > maxVal) {
							maxVal = val;
							lCoup.clear();
							itCoup = lCoup.listIterator();
							itCoup.add(c[k]);
						} else if (val == maxVal) {
							itCoup.add(c[k]);
						}
						// Suppression de la simulation
						demanger(plateau, t, mange, c[k]);
						p.deplacementsansverif(new Coup(c[k].getxArr(), c[k]
								.getyArr(), c[k].getxDep(), c[k].getyDep()));
					}
				}
			}
		}
		java.util.Random gen = new Random();
		return lCoup.get(gen.nextInt(lCoup.size()));
	}

	private int min(Plateau p, int t, int profondeur) {
		if (profondeur == 0)
			return 0;
		else {
			int pred = t;
			if (t == 1)
				t = 2;
			else
				t = 1;
			int minVal = Integer.MAX_VALUE;
			int val = 0;
			Case[][] plateau = p.getUaetalp();
			for (int i = 0; i < p.getX(); i++) {
				for (int j = 0; j < p.getY(); j++) {
					if (plateau[i][j].getPion().getType().val == t) {
						Coup[] c = p.getDeplacementsPossibles(i, j);
						for (int k = 0; k < c.length; k++) {
							if (p.verifCoupGagnant(c[k])) {
								return -10000;
							}
							// Simulation du coup actuel
							p.deplacementsansverif(c[k]);
							int mange = p.manger(c[k]);
							val = max(p, t, profondeur-1)
									- eval(p, t, pred, c[k], mange);
							if (val < minVal) {
								minVal = val; // +si victoire 10000 si defaite
												// -10000 +pions manger*10
												// -pipons mangés +10
							}
							// Suppression de la simulation
							demanger(plateau, t, mange, c[k]);
							p.deplacementsansverif(new Coup(c[k].getxArr(),
									c[k].getyArr(), c[k].getxDep(), c[k]
											.getyDep()));
						}
					}
				}
			}
			return minVal;
		}
	}

	private int max(Plateau p, int t, int profondeur) {
		int pred = t;
		if (t == 1)
			t = 2;
		else
			t = 1;
		if (profondeur == 0)
			return 0;
		else {
			int maxVal = Integer.MIN_VALUE;
			int val = 0;
			Case[][] plateau = p.getUaetalp();
			for (int i = 0; i < p.getX(); i++) {
				for (int j = 0; j < p.getY(); j++) {
					if (plateau[i][j].getPion().getType().val == t) {
						Coup[] c = p.getDeplacementsPossibles(i, j);
						for (int k = 0; k < c.length; k++) {
							if (p.verifCoupGagnant(c[k])) {
								return +10000;
							}
							// Simulation du coup actuel
							p.deplacementsansverif(c[k]);
							int mange = p.manger(c[k]);
							val = min(p, t, profondeur-1)
									+ eval(p, t, t, c[k], mange);
							if (val > maxVal) {
								maxVal = val;
							}
							// Suppression de la simulation
							demanger(plateau, t, mange, c[k]);
							p.deplacementsansverif(new Coup(c[k].getxArr(),
									c[k].getyArr(), c[k].getxDep(), c[k]
											.getyDep()));
						}
					}
				}
			}
			return maxVal;
		}
	}

	private int eval(Plateau p, int t, int dernier, Coup c, int mange) {
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
		int roiX, roiY;
		int tmp = 0;
		Case[][] board = p.getUaetalp();
		roiX = p.GetPosKing();
		roiY = roiX % 10;
		roiX = roiX / 10;
		if (roiX != 0 && roiY != 0) {
			if (roiX == 0
					|| board[roiX - 1][roiY].getPion().getType() == TypePion.MOSCOVITE
					|| board[roiX - 1][roiY].getState() == TypeCase.FORTERESSE) {
				tmp++;
			}
			if (roiY == 0
					|| board[roiX][roiY - 1].getPion().getType() == TypePion.MOSCOVITE
					|| board[roiX][roiY - 1].getState() == TypeCase.FORTERESSE) {
				tmp++;
			}
			if (roiX == p.getX() - 1
					|| board[roiX + 1][roiY].getPion().getType() == TypePion.MOSCOVITE
					|| board[roiX + 1][roiY].getState() == TypeCase.FORTERESSE) {
				tmp++;
			}
			if (roiY == p.getY() - 1
					|| board[roiX][roiY + 1].getPion().getType() == TypePion.MOSCOVITE
					|| board[roiX][roiY + 1].getState() == TypeCase.FORTERESSE) {
				tmp++;

			}
		}
		if(t== TypePion.SUEDOIS.val){
			if (tmp == 1) {
				ret -= 2;
			} else if (tmp == 2) {
				ret -= 10;
			} else if (tmp == 3) {
				ret -= 30;
			}
		}
		else{
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

	/*
	 * gagner/perdre : 10000 manger un pion/perdre un pion : 10 distance
	 * roi/coin (combien de deplacement pour atteindre)(40-7/coup) le roi est
	 * entourré de 1/2/3 (2/5/25) nombre de pion allié autour du roi nombre de
	 * pion alliée autour du pion
	 */
	private void demanger(Case[][] plateau, int t, int mange, Coup c) {
		if (t == 1) { // si Suedois
			if ((mange & 1) == 1) {
				plateau[c.getxArr() - 1][c.getyArr()].setPion(new Pion(
						TypePion.MOSCOVITE));
			}
			if ((mange & 2) == 2) {
				plateau[c.getxArr()][c.getyArr() - 1].setPion(new Pion(
						TypePion.MOSCOVITE));
			}
			if ((mange & 4) == 4) {
				plateau[c.getxArr() + 1][c.getyArr()].setPion(new Pion(
						TypePion.MOSCOVITE));
			}
			if ((mange & 8) == 8) {
				plateau[c.getxArr()][c.getyArr() + 1].setPion(new Pion(
						TypePion.MOSCOVITE));
			}
		} else { // si Moscovite
			if ((mange & 1) == 1) {
				plateau[c.getxArr() - 1][c.getyArr()].setPion(new Pion(
						TypePion.SUEDOIS,TypeSuedois.PION));
			}
			if ((mange & 2) == 2) {
				plateau[c.getxArr()][c.getyArr() - 1].setPion(new Pion(
						TypePion.SUEDOIS,TypeSuedois.PION));
			}
			if ((mange & 4) == 4) {
				plateau[c.getxArr() + 1][c.getyArr()].setPion(new Pion(
						TypePion.SUEDOIS,TypeSuedois.PION));
			}
			if ((mange & 8) == 8) {
				plateau[c.getxArr()][c.getyArr() + 1].setPion(new Pion(
						TypePion.SUEDOIS,TypeSuedois.PION));
			}
		}
	}
	

}
