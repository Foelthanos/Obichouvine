package modele;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.ListIterator;


public class Historique {

	LinkedList<Coup> l ;
	ListIterator<Coup> li;
	LinkedList<Coup> lRefaire ;
	ListIterator<Coup> liRefaire;
	String historiqueName;
	
	public Historique(String n)
	{
		historiqueName = n;
		l = new LinkedList<Coup>();
		li =  l.listIterator();
		lRefaire = new LinkedList<Coup>();
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
			Coup cp = liRefaire.previous();
			li.add(cp);
			liRefaire.remove();
			
		}
		else
		{
			System.out.println("Pas de coup a refaire");

		}
	}
	
	public void sauver(int longueur, int largeur, int tour)
	{
		Coup cp;
		while(li.hasPrevious())
			li.previous();
		try {
			PrintWriter fo = new PrintWriter(historiqueName);
			fo.println(longueur + " " + largeur);
			fo.println(tour);
			while (li.hasNext())
			{
				cp = (Coup) li.next();
				fo.println((int)cp.getxDep() + " " + (int)cp.getyDep() + " " + (int)cp.getxArr() + " " + (int)cp.getyArr());

			}
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void charger()
	{
		BufferedReader br = null;
		 
		
		try {
 
			String sCurrentLine;
			int i =0;
			br = new BufferedReader(new FileReader(historiqueName));

			while ((sCurrentLine = br.readLine()) != null) {
				if (i>=2)
				{
					String[] j = sCurrentLine.split(" ");
					Coup cp = new Coup(Integer.parseInt(j[0]),Integer.parseInt(j[1]),Integer.parseInt(j[2]),Integer.parseInt(j[3]));
					li.add(cp);
				}
				i++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  

 
	}

	
}
