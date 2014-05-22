package ia;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import modele.Case;
import modele.Coup;
import modele.Pion;
import modele.Plateau;
import modele.Pion.TypePion;

public class IA {
	int profondeur;
	int niveau;
	//1 - Facile
	//2 - Intermédiaire
	//3 - Expert
	
	public IA(int n) {
		niveau = n;
		profondeur = 5;
	}
	
	public Coup jouer(Plateau p, int t) {
		if(niveau == 3)
			return jouerExp(p, t);
		else
			return null;
	}
	
	public Coup jouerExp(Plateau p, int t) {
		int maxVal = Integer.MIN_VALUE;
		int val = 0;
		LinkedList<Coup> lCoup = new LinkedList<Coup>();
		ListIterator<Coup> itCoup = lCoup.listIterator();
		Case[][] plateau = p.getUaetalp();
		for(int i=0 ; i<p.getX() ; i++) {
			for(int j=0 ; j<p.getY() ; j++) {
				if(plateau[i][j].getPion().getType().val == t) {
					Coup[] c = p.getDeplacementsPossibles(i, j);
					for(int k=0 ; k < c.length ; k++) {
						//Simulation du coup actuel
						p.deplacementsansverif(c[k]);
						int mange = p.manger(c[k]);
						val = min(p, t, profondeur);
						if(val > maxVal) {
							maxVal = val;
							lCoup.clear();
							itCoup = lCoup.listIterator();
							itCoup.add(c[k]);
						}
						else if(val == maxVal) {
							itCoup.add(c[k]);
						}
						//Suppression de la simulation
						demanger(plateau, t, mange, c[k]);
						p.deplacementsansverif(new Coup(c[k].getxArr(),c[k].getyArr(),c[k].getxDep(),c[k].getyDep()));
					}
				}
			}
		}
		java.util.Random gen = new Random();
		return lCoup.get(gen.nextInt(lCoup.size()));
	}
	
	private int min(Plateau p, int t, int profondeur) {
		if(profondeur == 0 /*|| p.victoire()*/)
			return eval(p, t,t);//1000 si victoire -1000 si defaite
		else {
			if(t == 1)
				t = 2;
			else
				t = 1;
			int minVal = Integer.MAX_VALUE;
			int val = 0;
			Case[][] plateau = p.getUaetalp();
			for(int i=0 ; i<p.getX() ; i++) {
				for(int j=0 ; j<p.getY() ; j++) {
					if(plateau[i][j].getPion().getType().val == t) {
						Coup[] c = p.getDeplacementsPossibles(i, j);
						for(int k=0 ; k < c.length ; k++) {
							//Simulation du coup actuel
							p.deplacementsansverif(c[k]);
							int mange = p.manger(c[k]);
							val = max(p, t, profondeur);
							if(val < minVal) {
								minVal = val;
							}
							//Suppression de la simulation
							demanger(plateau, t, mange, c[k]);
							p.deplacementsansverif(new Coup(c[k].getxArr(),c[k].getyArr(),c[k].getxDep(),c[k].getyDep()));
						}
					}
				}
			}
			return minVal;
		}
	}
	
	private int max(Plateau p, int t, int profondeur) {
		int pred = t;
		if(t == 1)
			t = 2;
		else
			t = 1;
		if(profondeur == 0 /*|| p.victoire()*/)
			return eval(p, t,pred);//1000 si victoire -1000 si defaite
		else {
			int maxVal = Integer.MAX_VALUE;
			int val = 0;
			Case[][] plateau = p.getUaetalp();
			for(int i=0 ; i<p.getX() ; i++) {
				for(int j=0 ; j<p.getY() ; j++) {
					if(plateau[i][j].getPion().getType().val == t) {
						Coup[] c = p.getDeplacementsPossibles(i, j);
						for(int k=0 ; k < c.length ; k++) {
							//Simulation du coup actuel
							p.deplacementsansverif(c[k]);
							int mange = p.manger(c[k]);
							val = min(p, t, profondeur);
							if(val > maxVal) {
								maxVal = val;
							}
							//Suppression de la simulation
							demanger(plateau, t, mange, c[k]);
							p.deplacementsansverif(new Coup(c[k].getxArr(),c[k].getyArr(),c[k].getxDep(),c[k].getyDep()));
						}
					}
				}
			}
			return maxVal;
		}
	}
	
	private int eval(Plateau p, int t,int dernier) { // t= joueur qui est incarné par IA
		/* TODO a fixer
		int retour = 0;
		if(p.verifGagne(retour))
		if(p.verifGagne(c)(t))
			retour += 10000;
		else if(p.defaite(t))
			retour -= 10000;
		*/
		return 0;
	}

	private void demanger(Case[][] plateau, int t,int mange,Coup c){
		if(t == 1){ // si Suedois
			if((mange & 1) == 1){
				plateau[c.getxArr()-1][c.getyArr()].setPion(new Pion(TypePion.MOSCOVITE));
			}
			if((mange & 2) == 2){
				plateau[c.getxArr()][c.getyArr()-1].setPion(new Pion(TypePion.MOSCOVITE));
			}
			if((mange & 4) == 4){
				plateau[c.getxArr()+1][c.getyArr()].setPion(new Pion(TypePion.MOSCOVITE));
			}
			if((mange & 8) == 8){
				plateau[c.getxArr()][c.getyArr()+1].setPion(new Pion(TypePion.MOSCOVITE));
			}
		}
		else{ // si Moscovite
			if((mange & 1) == 1){
				plateau[c.getxArr()-1][c.getyArr()].setPion(new Pion(TypePion.SUEDOIS));
			}
			if((mange & 2) == 2){
				plateau[c.getxArr()][c.getyArr()-1].setPion(new Pion(TypePion.SUEDOIS));
			}
			if((mange & 4) == 4){
				plateau[c.getxArr()+1][c.getyArr()].setPion(new Pion(TypePion.SUEDOIS));
			}
			if((mange & 8) == 8){
				plateau[c.getxArr()][c.getyArr()+1].setPion(new Pion(TypePion.SUEDOIS));
			}
		}
	}
	
}	
/*
 * gagner/perdre : 10000 manger un pion/perdre un pion : 10 distance
 * roi/coin (combien de deplacement pour atteindre)(40-7/coup) le roi est
 * entourré de 1/2/3 (2/5/25) nombre de pion allié autour du roi nombre de
 * pion alliée autour du pion nombre de pion ennemi autour du pion
 */
