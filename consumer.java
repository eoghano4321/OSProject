import java.net.*;
import java.io.*;

public class consumer {
    public void clientSocket(){
        try{
            //connect to the server on the local host
            Socket sock = new Socket("127.0.0.1", 6013);
            //set up input stream
            InputStream in = sock.getInputStream();
            //read from socket using input stream
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            //print out the menu
            String line;
            while((line = bin.readLine()) != null)
                System.out.println(line);

            //get input on menu selection
            //send input to server
            //get waiting time from server
            //get food done from server

            
            //close the socket connection
            sock.close();
        }
        catch(IOException ioe){
            System.err.println(ioe);
        }
    }
}
