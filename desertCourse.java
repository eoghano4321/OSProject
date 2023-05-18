import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class desertCourse implements Course{

    @Override
    public void prepare(){
        System.out.println("Preparing desert");
    }
    public void makeFood(List<String> ordered){
        ForkJoinPool kitchen = new ForkJoinPool();
        makeFood cooked = new makeFood(ordered);
        System.out.println("You're dessert is done: " + kitchen.invoke(cooked));
    }
}
