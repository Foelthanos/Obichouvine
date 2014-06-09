package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ThreadDemandeComm implements Runnable {
        private BufferedReader in_Son;
        private PrintWriter out_Son;
        private PrintWriter out_SonOppose;
        private BufferedReader in_confirmOppose;
       
        private ServerController c;
       
        private String login;

        public ThreadDemandeComm(ServerController c) {
                this.c = c;
        }

        public void run() {
                String ch = "";
                String ch2 = "";
                boolean quitter = false;
                while(!quitter) {
                        try {
                                //System.out.println("je rentre dans demande com " + login);
                                ch = in_Son.readLine();
                                //System.out.println("je rentre premiere info dans demande com " + login);
                                if(ch == null || ch.equals("exit")) {
                                        quitter = true;
                                } else {
                                       
                                       // out_SonOppose = c.getThreadParClient(ch).getBufferSonOut();
                                        in_confirmOppose = c.getThreadParClient(ch).getBufferConfirmIn();
                                       
                                        out_SonOppose.println(login);
                                        out_SonOppose.flush();
                                       
                                        //System.out.println("j'attends confirm " + login);
                                        ch2 = in_confirmOppose.readLine();
                                        //System.out.println("je sors du confirm " + login);
                                       
                                        if(ch2.equals("OK")){
                                                out_Son.println("exit");
                                                out_Son.flush();
                                                quitter = true;
                                        }
                                       
                                        c.getThreadParClient(login).getBufferConfirmOut().println(ch2);
                                        c.getThreadParClient(login).getBufferConfirmOut().flush();
                                       
                                }
                        } catch (IOException e) {
                                e.printStackTrace();
                                quitter = true;
                        }
                }
                //System.out.println("je sors dans demande com " + login);
        }
}

