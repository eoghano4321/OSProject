package src;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.testng.ITest;

import junit.*;

class orderTest{
    @Test
    public void testFoodOrder(){
        

        String simulatedUserInput = "1\n1\n2\n3\n"; // Example input

        InputStream inputStream = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(inputStream);

        Consumer consumer = new Consumer();
        consumer.run();
        assertEquals(consumer.orderString, "Your order: [1, 2, 3]");
    }
}