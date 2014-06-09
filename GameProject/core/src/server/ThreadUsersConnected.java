package server;

import java.io.PrintWriter;
import java.util.TreeMap;

public class ThreadUsersConnected implements Runnable {
        private ServerController c;
        private PrintWriter out;

        public ThreadUsersConnected(ServerController c, PrintWriter out) {
                super();
                this.c = c;
                this.out = out;
               
        }

        @Override
        public void run() {
        //      boolean diff = false;
                try
                {
                        while(true)
                        {
                                out.println("%Server% DebEnvoisInfos");
                                out.flush();


                                boolean diff = false;
                                while(!diff) {
                                        try {
                                                Thread.sleep(1000);
                                                
                                        } catch (InterruptedException e) {
                                                Thread.currentThread().interrupt();
                                                throw new InterruptedException();
                                        }
                                }
                               
                        }
                }
                catch(InterruptedException e)
                {
                        out.println("%Server% FinEnvoisInfos");
                        out.flush();
                }
        }

       
}
