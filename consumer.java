import java.net.*;
import java.io.*;

class Consumer implements Runnable
{
    private int loop_counter;

    public Consumer(int loops) {
        loop_counter = loops;
    }

    public void run() {
        int num = 0;
        int val = 0;
        while(num < loop_counter) {
            num++;
        }
        System.out.println("Consumer finished");
    }
    
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

