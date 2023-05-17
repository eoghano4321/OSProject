import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

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
        System.out.println("Producer started");
        serverSocket();
        System.out.println("Producer finished");
    
    }
  
    public void serverSocket(){
        try{
            ServerSocket sock = new ServerSocket(6013);
            // now listen for connections
            
            Socket client = sock.accept();
            OutputStream outputStream = client.getOutputStream();
            while (client.isConnected()){
                //connection made
                PrintWriter pout = new PrintWriter(client.getOutputStream(), true);

                Hashtable<Integer, String> menu = new Hashtable<Integer, String>();
                menu = createMenu();
                System.out.println("Menu: " + menu);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(menu);

                // get input from client for menu item

                ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
                int[] order = null;
                try {
                    order = (int[]) objectInputStream.readObject();
                } catch (ClassNotFoundException e) {
                    // handle class not found exception
                }
                List<String> ordered = new ArrayList<String>();
                for (int key = 0; key < order.length; key++) {
                    if(order[key] != 0){
                        System.out.println("Ordered: " + menu.get(order[key]));
                        ordered.add(menu.get(order[key]));
                    }
                }


                // fork 
                ForkJoinPool kitchen = new ForkJoinPool();
                makeFood cooked = new makeFood(ordered);
                System.out.println("You're food is done: " + kitchen.invoke(cooked));


                // send back waiting time
                // wait amount of time
                // send food done

                pout.println(new java.util.Date().toString());
                // close the socket and resume listening for more connections
                client.close();
            }
            sock.close();
        }
        catch(IOException ioe){
            System.err.println(ioe);
        }
    }
    

    public Hashtable<Integer, String> createMenu(){
        Hashtable<Integer, String> menu = new Hashtable<Integer, String>();
        menu.put(1, "Steak");
        menu.put(2, "Chicken");
        menu.put(3, "Pasta");
        menu.put(4, "Salad");
        menu.put(5, "Soup");
        menu.put(6, "Bread");
        menu.put(7, "Water");
        menu.put(8, "Soda");
        menu.put(9, "Beer");
        menu.put(10, "Wine");
        return menu;
    }
}
