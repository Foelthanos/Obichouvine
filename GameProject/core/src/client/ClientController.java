package client;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;

import javax.sound.sampled.AudioFormat;
import javax.swing.JOptionPane;

import s6.prog6.obichouvine.models.Parameter;


public class ClientController {

        private LinkedList<Parameter> liPara;
       
       
        private Socket socket;
        private Socket socket_confirm;
       
        private BufferedReader in;
        private PrintWriter out;
        private BufferedReader in_confirm;
        private PrintWriter out_confirm;
       
        private Thread t; // ThreadListener
       
        private Thread tInfos; // ThreadListenerInfos
        private Thread tAttenteConnection;
        private Thread tSendRecord;
        private Thread tRecevoirLaMain;
       
        String login;
        /**
         * Constructeur du controleur
         * @param host : Adresse IP du serveur
         */
        public ClientController(String host) {
                try {
                        socket = new Socket(InetAddress.getByName(host), 2009);
                        socket_confirm = new Socket(InetAddress.getByName(host), 2011);
                        
                        in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
                        out = new PrintWriter(socket.getOutputStream());
                       
                        in_confirm = new BufferedReader (new InputStreamReader (socket_confirm.getInputStream()));
                        out_confirm = new PrintWriter(socket_confirm.getOutputStream());
                       
                        setLogin(null);
                        //TODO appel fenetre de connection
               } catch (UnknownHostException e) {
                        JOptionPane.showMessageDialog(null, "Le serveur est introuvable");
                } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Le serveur est indisponible pour le moment");
                }
        }
       


       
        /**
         * Setter du login
         * @param log : login de la personne connecté
         */
        public void setLogin(String log) {
                login = log;
        }

        /**
         * Getter du login
         * @return le login de la personne connecté
         */
        public String getLogin() {
                return login;
        }
       
        /**
         * Getter de l'ensemble des langues
         * @return Une collection des langues disponibles
         */

        /**
         * Getter du Thread tInfos
         * @return Un Thread
         */
        public Thread getTInfos() {
                return tInfos;
        }
       
        /**
         * Reset du Thread tInfos
         */
        public void setTInfos() {
                tInfos = new Thread(new ThreadListenerInfo(this));
        }
       
        /**
         * Getter du Thread tListener
         * @return Un Thread
         */
        public Thread getTListener() {
                return t;
        }

        /**
         * Reset du Thread tListenerInfos
         */
        public void setTListener() {
                t = new Thread(new ThreadListener(this));
        }

        /**
         * Getter du Thread tRecevoirLaMain
         * @return Un Thread
         */
        public Thread getTRecevoirLaMain() {
                return tRecevoirLaMain;
        }
       
        /**
         * Getter de l'ensemble des vues
         * @return Une collection des vues
         */
        private LinkedList<Parameter> getPara() {
                return liPara;
        }

        /**
         * Setter de l'ensemble des vues
         * @param vues : Une cosetLanguesParleesllection des vues
         */
        private void setPara(LinkedList<Parameter> liPara) {
                this.liPara = liPara;
        }
       
        /**
         * Getter du buffer d'écriture
         * @return Un objet de type PrintWriter
         */
        public PrintWriter getOut() {
                return out;
        }

        /**
         * Getter du buffer d'écriture
         * @return Un objet de  public type PrintWriter
         */
        public PrintWriter getOut_Confirm() {
                return out_confirm;
        }
       
        /**
         * Getter du buffer de lecture
         * @return Un objet de type BufferReader
         */
        public BufferedReader getIn() {
                return in;
        }

        /**
         * Getter du buffer de lecture
         * @return Un objet de type BufferReader
         */
        public BufferedReader getIn_Confirm() {
                return in_confirm;
        }
        /**
         * Méthode permettant de réaliser la connexion d'un personne
         * @param login : Le login de la personne
         * @param mdpCrypt : Le mot de passe de la personne
         * @param b : Si on doit retenir le login ou pas
         */
        public void connexion(String login) {
                if((login.length() != 0)) {
                        if(!login.equals("exit") && !login.equals("%Client%")) {
                                out.println(login);
                                out.flush();
                                String infoRetour = "";
                                try {
                                        infoRetour = in.readLine();
                                        if(infoRetour.equals("OK")) {
                                                // TODO message de connexion
                                                setLogin(login);
                                        } else {
                                                JOptionPane.showMessageDialog(null, infoRetour);
                                        }
                                } catch (IOException e) {
                                        JOptionPane.showMessageDialog(null, "Problème de connexion");
                                }
                        } else {
                                JOptionPane.showMessageDialog(null, "login incorrect");
                        }
                } else {
                        JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs");
                }
        }
       
        /**
         * Méthode permettant de démarrer une communication
         * @param loginOppose : login de l'autre personne
         * @param statut : statut de l'autre personne
         */
        public void communiquer(String loginOppose) {
                String confirm="";
                if(loginOppose != null)
                {
                               
                    try {
	                    confirm = in_confirm.readLine();
	                   
	                    if (confirm.equals("OK"))
	                    {
                            out.println("com");
                            out.flush();
                            try {
                                    if(login != null)
                                    {
                                        out.println(loginOppose);
                                        out.flush();
                                       
                                        tInfos.interrupt();
                                        String info = in.readLine();
                                        if(info != null) 
                                        {
                                            if(info.equals("Com : OK"))
                                            {
                                                t = new Thread(new ThreadListener(this));
                                                t.start();
                                            } else if (info.equals("Com : user not accept")) {
                                                    JOptionPane.showMessageDialog(null, "Votre correspondant a refusé la communication");
                                            } else if (info.equals("Com : user not connected")) {
                                                    JOptionPane.showMessageDialog(null, "Le correspondant que vous tentez de joindre n'est pas connecté");
                                            } else if (info.equals("Com : user in comm")) {
                                                    JOptionPane.showMessageDialog(null, "Le correspondant que vous tentez de joindre est déjà en communication");
                                            } else if (info.equals("Com : user not exists")) {
                                                    JOptionPane.showMessageDialog(null, "Le correspondant que vous tentez de joindre est inexistant");
                                            } else {
                                                    JOptionPane.showMessageDialog(null, "Il y a eu un problème lors de la connexion !");
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Il y a eu un problème lors de la connexion !");
                                            tInfos = new Thread(new ThreadListenerInfo(this));
                                            tInfos.start();
                                        }
                                    } else {
                                    	JOptionPane.showMessageDialog(null, "Tu n'as rien à faire la !!");
                                    }
	                            } catch (IOException e) {
	                                    JOptionPane.showMessageDialog(null, "Il y a eu un problème lors de la connexion !");
	                            }
	                    } else {
	                            JOptionPane.showMessageDialog(null, loginOppose + " a refusé la conversation");
	                    }
                    } catch (IOException e1) {
                            e1.printStackTrace();
                    }
                        }else{
                        JOptionPane.showMessageDialog(null, "Vous devez séléctionner un correspondant");
                }
        }
       
        /**
         * Méthode permettant d'envoyer un message texte
         * @param texte : le message texte à envoyer
         */
        public void envoyertexte(String texte){
                String ch = login + " : " + texte;
                out.println(ch);
                out.flush();
        }

        /**
         * Méthode gérant la fermeture de l'application en fermant tous les buffers et tous les sockets
         */
        public void quitter() {
                try {
                        out.println("quitter");
                        out.flush();

                       
                        in.close();
                        out.close();

                       
                        in_confirm.close();
                        out_confirm.close();
                       
                        socket.close();
                        socket_confirm.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                System.exit(0);
        }

        /**
         * Méthode permettant de passer la main lors d'une conversation
         */
        public void passerLaMain() {
                out_confirm.println("fini");
                out_confirm.flush();
               
                // pour ne pas quitter
                out_confirm.println("fini");
                out_confirm.flush();
        }



}

