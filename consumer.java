
class Consumer implements Runnable
{
    private int loop_counter;

    public Consumer(int loops) {
        loop_counter = loops;
    }

    public void run() {
        int num = 0;
        int val = 0;
        while(num < loop_counter) {
            num++;
        }
        System.out.println("Consumer finished");
    }
}