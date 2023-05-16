
class Producer implements Runnable
{
    private int loop_counter;

    public Producer(int loops) {
        this.loop_counter = loops;
        
    }

    public void run() {
        int num = 0;
        while(num < loop_counter) {
            num++;
        }
        System.out.println("Producer finished");
    }
}
