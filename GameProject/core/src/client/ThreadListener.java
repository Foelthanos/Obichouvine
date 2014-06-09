package client;

import java.io.IOException;

import javax.swing.JOptionPane;

public class ThreadListener implements Runnable {
        private ClientController c;
       
       
        public ThreadListener(ClientController c) {
                this.c = c;
        }



        public void run() {
        	String ch;
            try {
            	ch = "";
                boolean quitter = false;
                while(!quitter)
                {
                    ch = c.getIn().readLine();
                    if(ch != null)
                    {
                            if(!ch.equals("%Client% : exit") && !ch.equals("%Client2% : exit")) {
                            	;
                            } else if(ch.equals("%Client2% : exit")){
                                    quitter = true;
                                    c.getOut().println("%Client2% : exit");
                                    c.getOut().flush();
                    } else {
                            quitter = true;
                            JOptionPane.showMessageDialog(null, "Le correspondant est déconnecté");
                    }
                    Thread.sleep(500);
                    }

                } 
            }catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Il y a un problème dans la lecture des messages");
            }  catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
            }
        }
}
