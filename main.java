import java.util.List;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class main{
    public static void main(String[] args){
        
        Producer producer = new Producer(5);
        Consumer consumer = new Consumer(12);

        Thread tProducer = new Thread(producer);
        Thread tConsumer = new Thread(consumer);

        tProducer.start();
        tConsumer.start();

        try {
            tProducer.join();
            tConsumer.join();
        }
        catch (InterruptedException ie) {};

        ForkJoinPool kitchen = new ForkJoinPool();
        List<String> order = Arrays.asList("burger", "chips", "drink","pizza","Ice-Cream","chicken","fries","brownie","cookies","soda","coke","chips","pizza");
        makeFood cooked = new makeFood(order);
        System.out.println(kitchen.invoke(cooked));

    }
}