import java.net.*;
import java.io.*;
import java.util.*;

class Consumer implements Runnable
{
    private int loop_counter;
    public int maxOrderSize;
    public Scanner scanner = new Scanner(System.in);

    public Consumer(int loops) {
        loop_counter = loops;
    }

    public void run() {
        
        // Get number of customers
        System.out.println("How many customers are there?");
        
        int numCustomers = scanner.nextInt();
        
        this.maxOrderSize = 3 * numCustomers;
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
            //Scanner scanner = new Scanner(System.in);
            int input = this.scanner.nextInt();
            int[] order = new int[this.maxOrderSize]; // max order size is 3 * numCustomers
            int i = 0;


            // Could use a gui to display the menu and get the input instead of the console but we can do that later
            while (input != 0) {
                if(i >= this.maxOrderSize){
                    System.out.println("You can only order 3 dishes per person.");
                    input = 0;
                    continue;
                }
                else{
                    order[i] = input;
                    System.out.println("You entered: " + input);
                    input = this.scanner.nextInt();
                    i += 1;
                }
            }
            scanner.close();
            System.out.println("Your order: " + Arrays.toString(order));

            // Send the order to the server
            OutputStream out = sock.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(order);
            objectOutputStream.flush();
            

            // Get the waiting time from the server

           
            // Get food done from server

            
            //close the socket connection
            sock.close();
        }
        catch(IOException ioe){
            System.err.println(ioe);
        }
    }
}

