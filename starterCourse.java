import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class starterCourse implements Course {
    @Override
    public void prepare(){
        System.out.println("Preparing starter");
    }

    public void makeFood(List<String> ordered){
        ForkJoinPool kitchen = new ForkJoinPool();
        makeFood cooked = new makeFood(ordered);
        System.out.println("You're starter is done: " + kitchen.invoke(cooked));
    }
}
