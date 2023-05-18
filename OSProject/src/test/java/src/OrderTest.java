package src;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.testng.annotations.Test;


public class OrderTest{

    @Test
    public void testFoodOrder(){
        String simulatedUserInput = "1\n1\n2\n3\n"; // Example input

        InputStream inputStream = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(inputStream);

        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        Thread tProducer = new Thread(producer);
        tProducer.start();
        consumer.run();
        assert consumer.orderString.equals("Your order: [1, 2, 3]");
    }
}