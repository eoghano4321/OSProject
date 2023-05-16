import java.net.*;
import java.io.*;
import java.util.*;

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
        clientSocket();
        System.out.println("Consumer finished");
    }
    
    public void clientSocket(){
        try{
            //connect to the server on the local host
            Socket sock = new Socket("127.0.0.1", 6013);
            //set up input stream
            InputStream in = sock.getInputStream();

            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            Hashtable<Integer, String> menu = null;
            try {
                menu = (Hashtable<Integer, String>) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                // handle class not found exception
            }
            // Process the menu data received from the server
            for (Integer key : menu.keySet()) {
                System.out.println(key + ". " + menu.get(key));
            }
            System.out.println("Please enter the numbers of the item you would like to order: (Press enter after each number and enter 0 when finished)");
            Scanner scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            // Could use a gui to display the menu and get the input instead of the console but we can do that later
            while (input != 0) {
                System.out.println("You entered: " + input);
                input = scanner.nextInt();
            }
            scanner.close();
            // Need to add the inputs to an array and send it to the server for it to process

            //read from socket using input stream
            // BufferedReader bin = new BufferedReader(new InputStreamReader(in));

            // String line;
            // while((line = bin.readLine()) != null)
            //     System.out.println(line);

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

