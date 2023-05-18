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

                Hashtable<Integer, String> starters = new Hashtable<Integer, String>();
                Hashtable<Integer, String> mains = new Hashtable<Integer, String>();
                Hashtable<Integer, String> desserts = new Hashtable<Integer, String>();
                starters = createStarters();
                mains = createMains();
                desserts = createDesserts();
                System.out.println("MENU: Starters: " + starters + " Mains: " + mains + " Desserts: " + desserts);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(starters);
                objectOutputStream.writeObject(mains);
                objectOutputStream.writeObject(desserts);

                // get input from client for menu item

                ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
                Hashtable<Integer, List<Integer>> inorder = null;
                List<Integer> order = new ArrayList<Integer>();
                try {
                    inorder = (Hashtable<Integer, List<Integer>>) objectInputStream.readObject();
                } catch (ClassNotFoundException e) {
                    // handle class not found exception
                }
                for(int i = 0; i < inorder.size(); i++){
                    Integer food = inorder.get(i).get(0);

                    System.out.println("Food: " + food);
                    order.add(food);
                }

                List<String> ordered = new ArrayList<String>();
                for (int i = 0; i < order.size(); i = i+3) {
                    //List<Integer> starter = order.get(i);

                    System.out.println("Ordered: " + starters.get(order.get(i)));
                    System.out.println("Ordered: " + mains.get(order.get(i+1)));
                    System.out.println("Ordered: " + desserts.get(order.get(i+2)));
                    ordered.add(starters.get(order.get(i)));
                    ordered.add(mains.get(order.get(i+1)));
                    ordered.add(desserts.get(order.get(i+2)));
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
    

    // Create hashtable of menu items
    // This could be done in a database but for simplicity we will use a hashtable
    // This will be the same for all clients

    //create Starters
    public Hashtable<Integer, String> createStarters(){
        Hashtable<Integer, String> starters = new Hashtable<Integer, String>();
        starters.put(1, "Salad");
        starters.put(2, "Soup");
        starters.put(3, "Pasta");
        starters.put(4, "Bread");
        starters.put(5, "Cheese");
        starters.put(6, "Fish");
        return starters;
    }

    //create Mains
    public Hashtable<Integer, String> createMains(){
        Hashtable<Integer, String> mains = new Hashtable<Integer, String>();
        mains.put(1, "Steak");
        mains.put(2, "Chicken");
        mains.put(3, "Pork");
        mains.put(4, "Fish");
        mains.put(5, "Lamb");
        mains.put(6, "Vegetarian");
        return mains;
    }

    //create Desserts
    public Hashtable<Integer, String> createDesserts(){
        Hashtable<Integer, String> desserts = new Hashtable<Integer, String>();
        desserts.put(1, "Ice Cream");
        desserts.put(2, "Cake");
        desserts.put(3, "Pie");
        desserts.put(4, "Fruit");
        desserts.put(5, "Cheese");
        desserts.put(6, "Chocolate");
        return desserts;
    }
}
