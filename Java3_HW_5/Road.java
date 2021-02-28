public class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "\"Дорога " + length + " метров\"";
    }
    @Override
    public void go(Car c) {
        int speed = c.getSpeed();
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(1000 * length / speed);
            System.out.println(c.getName() + " закончил этап: " + description + ", пройдя его со скоростью " + speed + " м/с");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

