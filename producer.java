import java.net.*;
import java.io.*;

public class producer {
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
