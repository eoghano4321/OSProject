import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class mainCourse implements Course{
    @Override
    public void prepare(){
        System.out.println("Preparing main course");
    }

    public void makeFood(List<String> ordered){
        ForkJoinPool kitchen = new ForkJoinPool();
        makeFood cooked = new makeFood(ordered);
        System.out.println("You're main is done: " + kitchen.invoke(cooked));
    }
}

