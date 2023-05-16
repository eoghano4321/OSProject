

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

    }
}