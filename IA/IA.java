package ia;

import ia.Fappos.Elempos;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;



public class IA {
	int profondeur;
	int type;
	// 1 - Gogol
	// 2 - MinMax
	// 3 - AlphaBeta
	TypePion camp;
	TypePion adv;

	public IA(int t, int p, TypePion c) {
		type = t;
		profondeur = p;
		camp = c;
		if (camp == TypePion.SUEDOIS) {
			adv = TypePion.MOSCOVITE;
		} else {
			adv = TypePion.SUEDOIS;
		}
	}

	public Coup jouer(Plateau p) {
		if (type == 1)
			return null;// TODO gogol
		else if (type == 2)
			return jouerExp(p);
		else if (type == 3)
			return joueralpha(p);
		else
			return null;
	}

	public Coup jouerExp(Plateau p) {
		int maxVal = Integer.MIN_VALUE;
		int val = 0;
		LinkedList<Coup> lCoup = new LinkedList<Coup>();
		ListIterator<Coup> itCoup = lCoup.listIterator();
		Case[][] plateau = p.getUaetalp();
		for (int i = 0; i < p.getX(); i++) {
			for (int j = 0; j < p.getY(); j++) {
				if (plateau[i][j].getPion().getType() == camp) {
					Coup[] c = p.getDeplacementsPossibles(i, j);
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
						p.deplacementsansverif(new Coup(c[k].getxArr(), c[k]
								.getyArr(), c[k].getxDep(), c[k].getyDep()));
					}
				}
			}
		}
		java.util.Random gen = new Random();
		return lCoup.get(gen.nextInt(lCoup.size()));
	}

	private int min(Plateau p, int profondeur) {
		if (profondeur == 0)
			return evalFinal(p);
		else {

			int minVal = Integer.MAX_VALUE;
			int val = 0;
			Case[][] plateau = p.getUaetalp();
			for (int i = 0; i < p.getX(); i++) {
				for (int j = 0; j < p.getY(); j++) {
					if (plateau[i][j].getPion().getType() == adv) {
						Coup[] c = p.getDeplacementsPossibles(i, j);
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

	private int max(Plateau p, int profondeur) {
		if (profondeur == 0)
			return evalFinal(p);
		else {
			int maxVal = Integer.MIN_VALUE;
			int val = 0;
			Case[][] plateau = p.getUaetalp();
			for (int i = 0; i < p.getX(); i++) {
				for (int j = 0; j < p.getY(); j++) {
					if (plateau[i][j].getPion().getType() == camp) {
						Coup[] c = p.getDeplacementsPossibles(i, j);
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

	private int evalCoup(Plateau p, Coup c, int mange) {
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

	private int evalFinal(Plateau p) {
		int ret = 0;
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
		if (camp == TypePion.SUEDOIS) {
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

	/*
	 * gagner/perdre : 10000 manger un pion/perdre un pion : 10 distance
	 * roi/coin (combien de deplacement pour atteindre)(40-7/coup) le roi est
	 * entourré de 1/2/3 (2/5/25) nombre de pion allié autour du roi nombre de
	 * pion alliée autour du pion
	 */
	private void demanger(Case[][] plateau, TypePion dernier, int mange, Coup c) {
		if (dernier == TypePion.SUEDOIS) {
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
						TypePion.SUEDOIS, TypeSuedois.PION));
			}
			if ((mange & 2) == 2) {
				plateau[c.getxArr()][c.getyArr() - 1].setPion(new Pion(
						TypePion.SUEDOIS, TypeSuedois.PION));
			}
			if ((mange & 4) == 4) {
				plateau[c.getxArr() + 1][c.getyArr()].setPion(new Pion(
						TypePion.SUEDOIS, TypeSuedois.PION));
			}
			if ((mange & 8) == 8) {
				plateau[c.getxArr()][c.getyArr() + 1].setPion(new Pion(
						TypePion.SUEDOIS, TypeSuedois.PION));
			}
		}
	}

	public int alphaBeta(Plateau p, int prof, int alpha, int beta,
			boolean joueur) { /* alpha est toujours inférieur à beta */
		if (prof == 0) {
			return evalFinal(p);
		} else {
			int val;
			Case[][] plateau = p.getUaetalp();
			if (!joueur) {
				val = Integer.MAX_VALUE;

				for (int i = 0; i < p.getX(); i++) {
					for (int j = 0; j < p.getY(); j++) {
						if ((adv == TypePion.SUEDOIS && plateau[i][j]
								.getPion().getType() == TypePion.SUEDOIS)
								|| (adv == TypePion.MOSCOVITE && plateau[i][j].getPion().getType() == TypePion.MOSCOVITE)) {
							Coup[] c = p.getDeplacementsPossibles(i, j);
							for (int k = 0; k < c.length; k++) {
								if (p.verifCoupGagnant(c[k])) {
									return +10000;
								}
								System.out.println(i+" "+j + "-> c : " + c.length + " // prof : " + prof);
								// Simulation du coup actuel
								p.deplacementsansverif(c[k]);
								int mange = p.manger(c[k]);
								int tmp = alphaBeta(p, (prof - 1), alpha, beta,
										!joueur) + evalCoup(p, c[k], mange);
								if (tmp < val) {
									val = tmp;
								}
								if (joueur) {
									demanger(plateau, camp, mange, c[k]);
								} else {
									demanger(plateau, adv, mange, c[k]);
								}
								p.deplacementsansverif(new Coup(c[k].getxArr(),
										c[k].getyArr(), c[k].getxDep(), c[k]
												.getyDep()));
								if (alpha > val) {
									return val;
								}
								beta = Math.min(beta, val);
							}
						}
					}
				}
			} else {
				val = Integer.MIN_VALUE;
				for (int i = 0; i < p.getX(); i++) {
					for (int j = 0; j < p.getY(); j++) {
						if ((camp == TypePion.SUEDOIS && plateau[i][j]
								.getPion().getType() == TypePion.SUEDOIS)
								|| (camp == TypePion.MOSCOVITE&& plateau[i][j].getPion().getType() == TypePion.MOSCOVITE)) {
							Coup[] c = p.getDeplacementsPossibles(i, j);
							for (int k = 0; k < c.length; k++) {
								if (p.verifCoupGagnant(c[k])) {
									return +10000;
								}
								// Simulation du coup actuel
								p.deplacementsansverif(c[k]);
								int mange = p.manger(c[k]);
								int tmp = alphaBeta(p, (prof - 1), alpha, beta,
										!joueur) + evalCoup(p, c[k], mange);
								if (tmp > val) {
									val = tmp;
								}
								if (joueur) {
									demanger(plateau, camp, mange, c[k]);
								} else {
									demanger(plateau, adv, mange, c[k]);
								}
								p.deplacementsansverif(new Coup(c[k].getxArr(),
										c[k].getyArr(), c[k].getxDep(), c[k]
												.getyDep()));
								if (val > beta) {
									return val;
								}
								alpha = Math.max(alpha, val);
							}
						}
					}
				}
			}

			return val;

		}

	}

	public Coup joueralpha(Plateau p) {
		int maxVal = Integer.MIN_VALUE;
		int val = 0;
		LinkedList<Coup> lCoup = new LinkedList<Coup>();
		ListIterator<Coup> itCoup = lCoup.listIterator();
		Case[][] plateau = p.getUaetalp();
		for (int i = 0; i < p.getX(); i++) {
			for (int j = 0; j < p.getY(); j++) {
				if (plateau[i][j].getPion().getType() == camp) {
					Coup[] c = p.getDeplacementsPossibles(i, j);
					for (int k = 0; k < c.length; k++) {
						if (p.verifCoupGagnant(c[k])) {
							return c[k];
						}
						// Simulation du coup actuel
						p.deplacementsansverif(c[k]);
						int mange = p.manger(c[k]);
						val = alphaBeta(p, profondeur, Integer.MIN_VALUE,
								Integer.MAX_VALUE, false)
								+ evalCoup(p, c[k], mange);
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
						p.deplacementsansverif(new Coup(c[k].getxArr(), c[k]
								.getyArr(), c[k].getxDep(), c[k].getyDep()));
					}
				}
			}
		}
		java.util.Random gen = new Random();
		return lCoup.get(gen.nextInt(lCoup.size()));
	}

	private int dijkstraRoi(Plateau p) {
		boolean cont = true;
		Fappos fap = new Fappos();
		int xRoiOr = p.GetPosKing();
		int yRoiOr = xRoiOr % 10;
		xRoiOr = (xRoiOr - yRoiOr) / 10;
		p.getUaetalp()[xRoiOr][yRoiOr].setPion(new Pion(TypePion.VIDE));
		fap.ajouter(fap.new Elempos(0, xRoiOr, yRoiOr));
		while (!fap.vide() && cont) {
			Elempos elem = fap.retirer();
			if (p.getUaetalp()[elem.x][elem.y].getState() == TypeCase.FORTERESSE) {
				p.getUaetalp()[xRoiOr][yRoiOr].setPion(new Pion(
						TypePion.SUEDOIS, TypeSuedois.KING));
				int ret = (int) (40 - Math.pow(2, elem.comparator));
				if (ret > 0)
					if (camp == TypePion.SUEDOIS)
						return ret;
					else
						return -ret;
				else
					return 0;
			}
			Coup c[] = p.getDeplacementsPossibles(elem.x, elem.y);
			for (int i = 0; i < c.length; i++) {
				fap.ajouter(fap.new Elempos(elem.comparator + 1,
						c[i].getxArr(), c[i].getyArr()));
			}
			if (elem.comparator > 5)
				cont = false;
		}
		p.getUaetalp()[xRoiOr][yRoiOr].setPion(new Pion(TypePion.SUEDOIS,
				TypeSuedois.KING));
		return 0;
	}

}
