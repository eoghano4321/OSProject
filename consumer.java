import java.net.*;
import java.io.*;
import java.util.*;

class Consumer implements Runnable
{
    public int maxOrderSize;
    public Scanner scanner = new Scanner(System.in);
    public int numCustomers;

    public Consumer() {
    }

    public void run() {
        
        // Get number of customers
        System.out.println("How many customers are there?");
        
        numCustomers = scanner.nextInt();
        
        this.maxOrderSize = 3 * numCustomers; // The maximum order size is 3 * the number of customers - 3 courses per customer
        clientSocket(); // Call the client socket method
        System.out.println("Consumer finished"); // Print out that the consumer has finished once the client socket method has finished
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
                // Get each of the input streams from the server
                // These are the starters, mains and desserts as a hashtable to display in the menu
                starters = (Hashtable<Integer, String>) objectInputStream.readObject();
                mains = (Hashtable<Integer, String>) objectInputStream.readObject();
                desserts = (Hashtable<Integer, String>) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                // handle class not found exception
            }
            // Process the menu data received from the server
            // Print out the menu to the user

            // Receive and print out the starters
            System.out.println("MENU: Starters: ");
            for (Integer key : starters.keySet()) {
                System.out.println(key + ". " + starters.get(key));
            }
            // Receive and print out the mains
            System.out.println("MENU: Mains: ");
            for (Integer key : mains.keySet()) {
                System.out.println(key + ". " + mains.get(key));
            }
            // Receive and print out the desserts
            System.out.println("MENU: Desserts: ");
            for (Integer key : desserts.keySet()) {
                System.out.println(key + ". " + desserts.get(key));
            }

            int[] order = new int[this.maxOrderSize];

            // Get input from user for menu item
            // Loop through the number of customers
            for(int j = 0; j < this.maxOrderSize; j = j + 3){
                // For each customer they can order 3 courses so add to the order array 3 times per customer

                // Get starter order input
                System.out.println("Please enter the number of the starter");
                int input = this.scanner.nextInt();
                // If the number entered is less than 1 or greater than the size of the starters hashtable then ask for a valid number
                // We use the variable value size to make this code independent of the number of items in the menu and fully extensible
                if(input > starters.size() || input < 1){ 
                    System.out.println("Please enter a valid number");
                    input = this.scanner.nextInt();
                    order[j] = input;
                }
                // Else add the number to the order array
                else{
                    System.out.println("You entered: " + input);
                    order[j] = input;
                }

                // Get main order input
                System.out.println("Please enter the number of the main");
                input = this.scanner.nextInt();
                if(input > mains.size() || input < 1){
                    System.out.println("Please enter a valid number");
                    input = this.scanner.nextInt();
                    order[j+1] = input;
                }
                else{
                    System.out.println("You entered: " + input);
                    order[j+1] = input;
                }

                // Get dessert order input
                System.out.println("Please enter the number of the dessert");
                input = this.scanner.nextInt();
                if(input > desserts.size() || input < 1){
                    System.out.println("Please enter a valid number");
                    input = this.scanner.nextInt();
                    order[j+2] = input;
                }
                else{
                    System.out.println("You entered: " + input);
                    order[j+2] = input;
                }
                
            }
            
            // Close the scanner
            scanner.close();
            // Print the array of orders on the consumer side
            System.out.println("Your order: " + Arrays.toString(order));

            // Send the order to the server
            OutputStream out = sock.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(order);

            // Flush the output stream
            objectOutputStream.flush();
            
            //close the socket connection
            sock.close();
        }
        catch(IOException ioe){
            System.err.println(ioe);
        }
    }
}

