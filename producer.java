import java.net.*;
import java.io.*;

class Producer implements Runnable
{
    private int loop_counter;

    public Producer(int loops) {
        this.loop_counter = loops;
        
    }

    public void run() {
        int num = 0;
        while(num < loop_counter) {
            num++;
        }
        System.out.println("Producer finished");
    
    }
  
  public void serverSocket(){
        try{
            ServerSocket sock = new ServerSocket(6013);
            // now listen for connections
            while (true){
                Socket client = sock.accept();
                //connection made
                PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
                // create menu and send it ...
                // get input from client for menu item
                // send back waiting time
                // wait amount of time
                // send food done

                pout.println(new java.util.Date().toString());
                // close the socket and resume listening for more connections
                client.close();
            }
        }
        catch(IOException ioe){
            System.err.println(ioe);
        }
  }
}
