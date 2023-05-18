package src;
public class main{
    public static void main(String[] args){
        
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        Thread tProducer = new Thread(producer);
        Thread tConsumer = new Thread(consumer);

        tProducer.start();
        tConsumer.start();

        try {
            tProducer.join();
            tConsumer.join();
        }
        catch (InterruptedException ie) {};

    }
}