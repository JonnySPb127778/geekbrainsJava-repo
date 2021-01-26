public class Human implements Competitors {
    private String name = "Adam";
    private final float jumpLimit;
    private final int runLimit;

    public Human(String name, float jumpLimit, int runLimit) {
        this.name = name;
        this.jumpLimit = jumpLimit;
        this.runLimit = runLimit;
    }

    @Override
    public void jump(float height) {
        System.out.println("Human " + name + " jump to " + height + " meters");
    }

    @Override
    public void run(int distance) {
        System.out.println("Human "+ name + " run " + distance + " meters");
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

