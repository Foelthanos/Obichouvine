package client;

import s6.prog6.obichouvine.controllers.GameController;

public class StartClient {

	 public static void main(String[] args) {
         if(args.length == 1) {
                 new StartClient(args[0]);
         } else {
                 System.out.println("pas d'adresse client");
         }
	 }
	
	 public StartClient(String host) {
	         new ClientController(host);
	 }
}
