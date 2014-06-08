package client;

import java.io.IOException;


public class ThreadListenerInfo implements Runnable {
        private ClientController c;
       
        public ThreadListenerInfo(ClientController c) {
                this.c = c;
        }

        public void run() {
                //System.out.println("J'ouvre threadListenerInfos\n");
                String ch = "";
                try {
                        ch = c.getIn().readLine();
                } catch (IOException e) {
                        e.printStackTrace();
                }
               
                while(!ch.equals("%Server% FinEnvoisInfos")) {
                        try {
                                if(ch.equals("%Server% DebEnvoisInfos")) {
                                        ch = c.getIn().readLine();
                                }
                                ch = c.getIn().readLine();
                                /*if(ch == null) {
                                        break;
                                }*/
                                Thread.sleep(1);
                        } catch (IOException e) {
                                e.printStackTrace();
                        } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                break;
                        }
                }
                //System.out.println("Je sors de threadListenerInfos\n");
        }
}

