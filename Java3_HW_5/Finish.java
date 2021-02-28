import java.util.concurrent.CountDownLatch;

public class Finish extends Stage {
    private CountDownLatch finish;
    long cars;

    public Finish(CountDownLatch finish, int cars) {
        this.finish = finish;
        this.cars = (long)cars;
    }
    @Override
    public void go(Car c) {
        if(finish.getCount() == cars) System.out.print("WINNER!!! ");
        finish.countDown();
        System.out.println(c.getName() + " финишировал " + (cars - finish.getCount()) + "-м.");
    }
}
