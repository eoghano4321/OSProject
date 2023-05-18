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
                int[] order = null;
                try {
                    order = (int[]) objectInputStream.readObject();
                } catch (ClassNotFoundException e) {
                    // handle class not found exception
                }
                Restaurant starterKitchen = new starterRestaurant();
                Restaurant mainKitchen = new mainRestaurant();
                Restaurant desertKitchen = new desertRestaurant();

                
    
                List<String> chosenStarters = new ArrayList<String>();
                List<String> chosenMains = new ArrayList<String>();
                List<String> chosenDeserts = new ArrayList<String>();
                for (int i = 0; i < order.length; i = i+3) {
                    if(order[i] != 0){
                        System.out.println("Ordered: " + starters.get(order[i]));
                        chosenStarters.add(starters.get(order[i]));
                    }
                    if(order[i+1] != 0){
                        System.out.println("Ordered: " + mains.get(order[i+1]));
                        chosenMains.add(mains.get(order[i+1]));
                    }
                    if(order[i+2] != 0){
                        System.out.println("Ordered: " + desserts.get(order[i+2]));
                        chosenDeserts.add(desserts.get(order[i+2]));
                    }
                }


                // fork 
                Course starter = starterKitchen.orderCourse();
                Course main = mainKitchen.orderCourse();
                Course desert = desertKitchen.orderCourse();
                starter.makeFood(chosenStarters);
                main.makeFood(chosenMains);
                desert.makeFood(chosenDeserts);


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
