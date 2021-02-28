import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

    public Tunnel(int length, int capacity) {
        this.capacity = capacity;
        this.length = length;
        this.description = "\"Тоннель " + length + " метров\"";
        this.smp = new Semaphore(capacity);
    }
    @Override
    public void go(Car c) {
        int speed = c.getSpeed();
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                smp.acquire();
                System.out.println(c.getName() + " начал этап: " + description);

                Thread.sleep( 1000 * length / speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description + ", пройдя его со скоростью " + speed + " м/с");
                smp.release(); // машина покинула тоннель, может заезжать следующая
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

