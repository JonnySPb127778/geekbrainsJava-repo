import java.util.concurrent.CountDownLatch;

public class Racing {
    public static final int CARS_COUNT = 4;
    public static CountDownLatch startLatch = new CountDownLatch(CARS_COUNT+1);
    public static CountDownLatch finishLatch = new CountDownLatch(CARS_COUNT);

    public static void main(String[] args) throws InterruptedException {

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(80, CARS_COUNT/2), new Road(40), new Finish(finishLatch, CARS_COUNT));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, startLatch);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        while (startLatch.getCount() > 1); // ждём всех машин на старте (main thread должен быть последним)
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        startLatch.countDown(); // Старт
//        startLatch.await();

        finishLatch.await();// Ждём последнюю машину
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка завершилась!!!");
    }
}



