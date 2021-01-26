public class Robot implements Competitors {
    private String name = "H3PO";
    private final float jumpLimit;
    private final int runLimit;

    public Robot(String name, float jumpLimit, int runLimit) {
        this.name = name;
        this.jumpLimit = jumpLimit;
        this.runLimit = runLimit;
    }

    @Override
    public void jump(float height) {
        System.out.println("Robot " + name + " jump to " + height + " meters");
    }

    @Override
    public void run(int distance) {
        System.out.println("Robot " + name + " run " + distance + " meters");
    }

    public float getJumpLimit() {
        return jumpLimit;
    }

    public int getRunLimit() {
        return runLimit;
    }

    @Override
    public String getName() {
        return name;
    }

}

