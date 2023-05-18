package src;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class mainTest {
    @Test
    void testMainCourse() {
        List<String> ordered = new ArrayList<String>();
        ordered.add("soup");

        mainCourse main = new mainCourse();
        main.prepare();
        assert main.getFood(ordered).equals("soup, ");
    }
}
