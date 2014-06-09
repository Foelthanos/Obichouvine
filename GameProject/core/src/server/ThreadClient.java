package server;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.GregorianCalendar;
import java.util.TreeMap;

public class ThreadClient implements Runnable {
        private ServerController controleur;
        private int numThread;
       
       
        private Socket socket;
        private Socket socket_cofirm;
        private BufferedReader in;
        private PrintWriter out;
        private BufferedReader in_confirm;
        private PrintWriter out_confirm;
       

        public ThreadClient(Socket socket, Socket socket_confirm, ServerController c, int numT) {
                this.socket = socket;
                this.socket_cofirm = socket_confirm;
                controleur = c;
                numThread = numT;
        }

        public void run() {
                try {
                        numThread = getControleur().setThreads(this);
                        try {
                                in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
                                out = new PrintWriter(socket.getOutputStream());
                                in_confirm = new BufferedReader (new InputStreamReader (socket_cofirm.getInputStream()));
                                out_confirm = new PrintWriter(socket_cofirm.getOutputStream());
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                       
                       
                        boolean t = true;
                        while(t) {
                                String msg = in.readLine();
                                if(msg.equals("connexion")) {
                                       
                                        int msgInfo = 0;
                                        boolean stopConn = false;
                                        while(msgInfo != 1) {
                                                msgInfo = connexion();
                                                if(msgInfo == -1) {
                                                        // Le client veut quitter la fenetre de déconnexion
                                                        msgInfo = 1;
                                                        stopConn = true;
                                                }
                                                // if(msgInfo == 0) Le mot de passe ou le login est incorrect
                                                // if(msgInfo == 1) OK
                                        }
                                        if(!stopConn ) {
                                                boolean quitter = false;
                                                while(!quitter) {
                                                       Thread demandeComm = new Thread(new ThreadDemandeComm(getControleur()));
                                                        demandeComm.start();
                                                        Thread sendInfos = new Thread(new ThreadUsersConnected(getControleur(), getBufferOut()));
                                                        sendInfos.start();
                                                        String msg2 = "";
                                                        //System.out.println("je suis la " + user.getLogin());
                                                        msg2 = in.readLine();
                                                        //System.out.println("je suis pas la " + user.getLogin());
                                                        sendInfos.interrupt();
                                                       //System.out.println("j'attends interupte " + user.getLogin());
                                                        if(!demandeComm.isInterrupted())
                                                                demandeComm.interrupt();
                                                        //System.out.println("je sors interupt " + user.getLogin());
                                                        if(msg2.equals("com")) {
                                                                //System.out.println("Je commence la communication" + getUser().getLogin());
                                                                communication();
                                                                //System.out.println("Je finis la communication" + getUser().getLogin());
                                                        } else if(msg2.equals("deconnexion")) {

                                                        } else if(msg2.equals("quitter")) {
                                                                quitter = true;
                                                                t = false;
                                                        } else if(msg2.equals("modification")) {

                                                        }
                                                }
                                        }
                                } else if(msg.equals("enregistrement")) {
                                        while(inscription() == 0);
                                } else if(msg.equals("quitter"))
                                        t = false;
                                else
                                        t = false;
                        }
                        out.close();
                        in.close();
                        socket.close();

                        getControleur().removeThread(this);

                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
       
        public int getNumThread() {
                return numThread;
        }
       
        public PrintWriter getBufferOut() {
                return out;
        }
       
        public PrintWriter getBufferConfirmOut() {
                return out_confirm;
        }
       
        public BufferedReader getBufferConfirmIn() {
                return in_confirm;
        }

        public void setNumThread(int numThread) {
                this.numThread = numThread;
        }

        public ServerController getControleur() {
                return controleur;
        }
       
        public void setControleur(ServerController c) {
                controleur = c;
        }
       
        public int connexion() {
                int ok = 0;
                try {
                        String login = "";
                        login = in.readLine();
                        if(login == null || login.equals("exit")) {
                                ok = -1;
                        } else {
                               
                               /* if(u == null || (!u.getLogin().equals(login))) {
                                        out.println("login incorrect");
                                        out.flush();
                                } else if(!u.getMdp().equals(mdpCrypt)) {
                                        out.println("mot de passe incorrect");
                                        out.flush();
                                } else {
                                        if(u.getStatut().equals(Statut.Hors_Ligne)) {
                                                out.println("OK");
                                                out.flush();
                                                u.setStatut(Statut.Disponible);
                                                user = u;
                                                log(log() + " -> le client est identifié !");
                                                ok = 1;
                                        } else {
                                                out.println("utilisateur déjà connecté");
                                                out.flush();
                                        }
                                }*/
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return ok;
        }
       
       
        public int inscription() {
                int ok = 0;
                TreeMap<String,String> languesParlees = null;
                try {
                        String login = in.readLine();
                        if(login == null || login.equals("exit")) {
                                ok = -1;
                        } else {
                                
                                String mdpCrypt = in.readLine();

                                String nom = in.readLine();

                                String prenom = in.readLine();

                                String sexe = in.readLine();

                                String type = in.readLine();

                                String LangueMaternelle = in.readLine();
                                if (type.equals("Interprete"))
                                {
                                        String nb="";
                                        nb= in.readLine();
                                        languesParlees = new TreeMap<String, String>();
                                        for(int i=0;i<Integer.valueOf(nb);i++){
                                                String langueTmp= in.readLine();
                                                languesParlees.put(langueTmp, langueTmp);
                                        }
                                }
                               
                               out.println("user create");
                                out.flush();
                                ok = 1;
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return ok;
        }
       
        public void communication() {
                String loginOppose = "";
                try {
                        loginOppose = in.readLine();
                        if(true){//getControleur().getUser(loginOppose) != null) {
                                /*if(getControleur().getUser(loginOppose).getStatut().equals(Statut.Hors_Ligne)) {
                                        out.println("Com : user not connected");
                                        out.flush();
                                } else if(getControleur().getUser(loginOppose).getStatut().equals(Statut.En_Communication)) {
                                        out.println("Com : user in comm");
                                        out.flush();
                                } else {
                                        OutputStream outSoundCLientOppose = getControleur().getThreadParClient(loginOppose).getBufferSoundOut();
                                        PrintWriter out_confirm_oppose = getControleur().getThreadParClient(loginOppose).getBufferConfirmOut();
                                        Thread sendSound = new Thread(new ThreadSound(getBufferSoundIn(), outSoundCLientOppose, getBufferConfirmIn(), getBufferConfirmOut(), out_confirm_oppose));
                                        sendSound.start();
                                        log(log() + " -> loginOppose : " + loginOppose);
                                        PrintWriter outCLientOppose = getControleur().getThreadParClient(loginOppose).getBufferOut();
                                        out.println("Com : OK");
                                        out.flush();
                                        String ch = "";
                                        boolean quitter = false;
                                        user.setStatut(Statut.En_Communication);
                                        GregorianCalendar tmp = new GregorianCalendar();
                                        String nomFich = tmp.get(GregorianCalendar.YEAR) + "-" +
                                                        (tmp.get(GregorianCalendar.MONTH) +1) + "-" +
                                                        tmp.get(GregorianCalendar.DAY_OF_MONTH) + "_" +
                                                        tmp.get(GregorianCalendar.HOUR_OF_DAY) + "_";
                                        String meta_donnee = "";
                                        if(loginOppose.compareToIgnoreCase(user.getLogin()) < 0) {
                                                nomFich += loginOppose + "-" + user.getLogin() + "_"  + getControleur().nextFreeId() + ".xml";
                                               
                                                meta_donnee =   "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                                                                "<conversation>\n\t" +
                                                                                        "<description>\n\t\t" +
                                                                                                "<temps annee=\"" + tmp.get(GregorianCalendar.YEAR)
                                                                                                + "\" mois=\"" + (tmp.get(GregorianCalendar.MONTH) +1)
                                                                                                + "\" jour=\"" + tmp.get(GregorianCalendar.DAY_OF_MONTH)
                                                                                                + "\" heure=\"" + tmp.get(GregorianCalendar.HOUR_OF_DAY)
                                                                                                + "\" minute=\"" + tmp.get(GregorianCalendar.MINUTE) + "\" />\n\t\t" +
                                                                                                "<utilisateur1 login=\"" + user.getLogin()
                                                                                                + "\" langue=\"" + user.getLangueMaternelle() + "\" />\n\t\t" +
                                                                                                "<utilisateur2 login=\"" + loginOppose + "\"" +
                                                                                                "langue=\"" + getControleur().getUser(loginOppose).getLangueMaternelle() + "\" />\n\t" +
                                                                                        "</description>\n";
                                        } else {
                                                try {
                                                        Thread.sleep(10);
                                                } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                }
                                                nomFich +=  user.getLogin()+ "-" + loginOppose + "_" + getControleur().getId() + ".xml";
                                        }
                                       
                                       
                                        FileWriter f1 = null;
                                        try {  
                                                f1 = new FileWriter(nomFich, true);
                                                f1.write(meta_donnee,0,meta_donnee.length());
                                        } catch(IOException ex) {
                                                ex.printStackTrace();
                                        } finally {
                                                if(f1 != null) {
                                                        try {
                                                                f1.close();
                                                        } catch (IOException e) {
                                                                e.printStackTrace();
                                                        }
                                                }
                                        }
                                       
                                        while(!quitter) {
                                                FileWriter f = null;
                                                try {  
                                                        f = new FileWriter(nomFich, true);
                                                        try {
                                                                ch = in.readLine();
                                                                if(ch != null) {
                                                                        if(ch.equals("%Client% : exit")) {
                                                                                quitter = true;
                                                                                out.println("%Client% : exit");
                                                                                out.flush();
                                                                                ch = "%Client2% : exit";
                                                                                outCLientOppose.println(ch);
                                                                                outCLientOppose.flush();
                                                                        } else if(ch.equals("%Client2% : exit")) {
                                                                                quitter = true;
                                                                        } else {
                                                                                passerMsg(ch, outCLientOppose);
                                                                                String ligneXML = "\t<msgTexte>" + ch + "</msgTexte>\n";
                                                                                f.write(ligneXML,0,ligneXML.length());
                                                                               
                                                                        }
                                                                } else {
                                                                        quitter = true;
                                                                }
                                                        } catch (IOException e) {
                                                                out.println("%Serveur% : msg failed");
                                                                out.flush();
                                                        }
                                                } catch(IOException ex) {
                                                        ex.printStackTrace();
                                                } finally {
                                                        if(f != null) {
                                                                try {
                                                                        f.close();
                                                                } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                }
                                                        }
                                                }
                                        }
                                        user.setStatut(Statut.Disponible);
                                       
                                        if(loginOppose.compareToIgnoreCase(user.getLogin()) < 0) {
                                                FileWriter f = null;
                                                try {  
                                                        f = new FileWriter(nomFich, true);
                                                        String fin = "</conversation>";
                                                        f.write(fin,0,fin.length());
                                                       
                                                } catch(IOException ex) {
                                                        ex.printStackTrace();
                                                } finally {
                                                        if(f != null) {
                                                                try {
                                                                        f.close();
                                                                } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                }
                                                        }
                                                }
                                        }
                                }*/
                        } else {
                                out.println("Com : user not exists");
                                out.flush();
                        }
                } catch (IOException e1) {
                        e1.printStackTrace();
                }
        }
       
        public void passerMsg(String txt, PrintWriter outCLientOppose) {
                outCLientOppose.println(txt);
                outCLientOppose.flush();
                out.println("%Serveur% : msg ok");
                out.flush();
        }
}

