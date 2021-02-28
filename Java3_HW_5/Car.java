import java.util.concurrent.CountDownLatch;

public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }

    private CountDownLatch start;
    private Race race;
    private String name;

    public String getName() {
        return name;
    }
    public int getSpeed() {
        return 20 + (int) (Math.random() * 10);  // Скорость машины меняется на каждом  трассы
    }
    public Car(Race race, CountDownLatch start) {
        this.race = race;
        CARS_COUNT++;
        this.name = "Участник под №" + CARS_COUNT;
        this.start = start;
    }
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            start.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            start.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
    }
}
