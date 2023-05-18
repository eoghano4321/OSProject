import java.net.*;
import java.io.*;
import java.util.*;

class Consumer implements Runnable
{
    private int loop_counter;
    public int maxOrderSize;
    public Scanner scanner = new Scanner(System.in);
    public int numCustomers;

    public Consumer(int loops) {
        loop_counter = loops;
    }

    public void run() {
        
        // Get number of customers
        System.out.println("How many customers are there?");
        
        numCustomers = scanner.nextInt();
        
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
            Hashtable<Integer, String> starters = null;
            Hashtable<Integer, String> mains = null;
            Hashtable<Integer, String> desserts = null;
            try {
                starters = (Hashtable<Integer, String>) objectInputStream.readObject();
                mains = (Hashtable<Integer, String>) objectInputStream.readObject();
                desserts = (Hashtable<Integer, String>) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                // handle class not found exception
            }
            // Process the menu data received from the server
            System.out.println("MENU: Starters: ");
            for (Integer key : starters.keySet()) {
                System.out.println(key + ". " + starters.get(key));
            }
            System.out.println("MENU: Mains: ");
            for (Integer key : mains.keySet()) {
                System.out.println(key + ". " + mains.get(key));
            }
            System.out.println("MENU: Desserts: ");
            for (Integer key : desserts.keySet()) {
                System.out.println(key + ". " + desserts.get(key));
            }

            int[] order = new int[this.maxOrderSize]; // max order size is 3 * numCustomers
            for(int j = 0; j < this.maxOrderSize; j = j + 3){
                System.out.println("Please enter the number of the starter");
                int input = this.scanner.nextInt();
                if(input > 6){
                    System.out.println("Please enter a valid number");
                    input = this.scanner.nextInt();
                }
                else{
                    System.out.println("You entered: " + input);
                    order[j] = input;
                }
                System.out.println("Please enter the number of the main");
                input = this.scanner.nextInt();
                if(input > 6){
                    System.out.println("Please enter a valid number");
                    input = this.scanner.nextInt();
                }
                else{
                    System.out.println("You entered: " + input);
                    order[j+1] = input;
                }
                System.out.println("Please enter the number of the dessert");
                input = this.scanner.nextInt();
                if(input > 6){
                    System.out.println("Please enter a valid number");
                    input = this.scanner.nextInt();
                }
                else{
                    System.out.println("You entered: " + input);
                    order[j+2] = input;
                }
                
            }
            


            // Could use a gui to display the menu and get the input instead of the console but we can do that later
            // while (input != 0) {
            //     if(i >= this.maxOrderSize){
            //         System.out.println("You can only order 3 dishes per person.");
            //         input = 0;
            //         continue;
            //     }
            //     else{
            //         order[i] = input;
            //         System.out.println("You entered: " + input);
            //         input = this.scanner.nextInt();
            //         i += 1;
            //     }
            // }
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

