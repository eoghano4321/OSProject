package src;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class makeFood extends RecursiveTask<String>{
    private List<String> order;

    public makeFood(List<String> order) {
      this.order = order;
    }

    protected String compute(){
        if(order.size() < 2){
            return cookFood();
        }else {
            int half = order.size()/2;
            makeFood cook1 = new makeFood(order.subList(0, half));
            makeFood cook2 = new makeFood(order.subList(half, order.size()));
            cook1.fork();
            return cook2.compute() + cook1.join();
        }
    }

    private String cookFood() {
        String cookedFood = "";
        for (int i =0; i < order.size(); i++){
            cookedFood += order.get(i);
            cookedFood += ", ";
        }
        return cookedFood;
      }
}


