package s6.prog6.obichouvine.models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.ListIterator;


public class Historique {

	public LinkedList<Move> l ;
	public ListIterator<Move> li;
	LinkedList<Move> lRefaire ;
	ListIterator<Move> liRefaire;
	String historiqueName;
	
	public Historique(String n)
	{
		historiqueName = n;
		l = new LinkedList<Move>();
		li =  l.listIterator();
		lRefaire = new LinkedList<Move>();
		liRefaire =  lRefaire.listIterator();
	}
	
	public void annuler()
	{
		if (li.hasPrevious())
		{
			liRefaire.add(li.previous());
			li.remove();
		}
		else
		{
			System.out.println("Historique est vide");
		}
	}
	
	public void refaire()
	{
		if (liRefaire.hasPrevious())
		{
			Move cp = liRefaire.previous();
			li.add(cp);
			liRefaire.remove();
			
		}
		else
		{
			System.out.println("Pas de coup a refaire");

		}
	}
	
	public void sauver(int longueur, int largeur)
	{
		Move cp;
		while(li.hasPrevious())
			li.previous();
		try {
			PrintWriter fo = new PrintWriter(historiqueName);
			fo.println(longueur + " " + largeur);
			while (li.hasNext())
			{
				cp = (Move) li.next();
				fo.println((int)cp.getxDep() + " " + (int)cp.getyDep() + " " + (int)cp.getxArr() + " " + (int)cp.getyArr());

			}
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public Board charger()
	{
		BufferedReader br = null;
		int longueur = 0, largeur = 0;
		
		try {
 
			String sCurrentLine;
			int i =0;
			br = new BufferedReader(new FileReader(historiqueName));
			
			while ((sCurrentLine = br.readLine()) != null) {
				if (i>=1)
				{
					String[] j = sCurrentLine.split(" ");
					Move cp = new Move(Integer.parseInt(j[0]),Integer.parseInt(j[1]),Integer.parseInt(j[2]),Integer.parseInt(j[3]));
					li.add(cp);
				}else if(i == 0) 
				{
					String [] taille = sCurrentLine.split(" ");
					longueur = Integer.parseInt(taille[0]);
					largeur = Integer.parseInt(taille[1]);
				}
				i++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		Board plat =  new Board(longueur, largeur, null);
		while (li.hasPrevious())
		{
			li.previous();
		}
		while (li.hasNext())
		{
			Move c = li.next();
			plat.deplacement(c);
		}
		return plat ;
	}

	
}
