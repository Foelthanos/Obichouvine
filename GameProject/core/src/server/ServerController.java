package server;

import java.io.IOException;
import java.net.ServerSocket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.TreeMap;


public class ServerController{
        private TreeMap<Integer, ThreadClient> threads;
       
        private ServerSocket socket;
        private ServerSocket socket_confirm;
       
        private int id;
       
       
        public ServerController() {
                id = 0;
                try {
                        setThreads(new TreeMap<Integer, ThreadClient>());
                        System.out.println("Serveur OK, version 1.14");
                        socket = new ServerSocket(2009);
                        socket_confirm = new ServerSocket(2011);

                        while(true) {
                        //      Thread t = new Thread(new ThreadClient(socket.accept(), this, getThreads().size()));
                                Thread t = new Thread(new ThreadClient(socket.accept(), socket_confirm.accept(), this, getThreads().size()));
                                t.start();
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
               
        }


        private TreeMap<Integer, ThreadClient> getThreads() {
                return threads;
        }

        private void setThreads(TreeMap<Integer, ThreadClient> threads) {
                this.threads = threads;
        }
       
        public int setThreads(ThreadClient t) {
                int max = 0;
                for(ThreadClient th : getThreads().values()) {
                        if(max < th.getNumThread()) {
                                max = th.getNumThread();
                        }
                }
                int numThread = max + 1;
                getThreads().put(numThread, t);
                return numThread;
        }
       
        public void removeThread(ThreadClient t) {
                getThreads().remove(t.getNumThread());
        }
       
        public ThreadClient getThread(int numThread) {
                return getThreads().get(numThread);
        }
       
        public ThreadClient getThreadParClient(String login) {
                ThreadClient retour = null;
                for(ThreadClient t : getThreads().values()) {
                        /*if(t.getUser().getLogin().equals(login)) {
                                retour = t;
                        }*/
                }
                return retour;
        }
       
        /**
         * Methode permettant de connaitre tous les utilisateurs qui sont connectes
         * @return une TreeMap d'utilisateur connecte indexee sur le login
         */
       /* public TreeMap<String, Utilisateur> getUsersConnected() {
                TreeMap<String, Utilisateur> tmp = new TreeMap<String, Utilisateur>();
                for(Utilisateur u : getUsers().values()) {
                        if(!u.getStatut().equals(Statut.Hors_Ligne)) {
                                tmp.put(u.getLogin().toLowerCase(), u);
                        }
                }
                return tmp;
        }*/

        public int nextFreeId() {
                id++;
                return id;
        }
        public int getId() {
                return id;
        }
}
