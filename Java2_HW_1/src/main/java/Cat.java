public class Cat implements Competitors {
    private String name = "Tom";
    private final float jumpLimit;
    private final int runLimit;

    public Cat(String name, float jumpLimit, int runLimit) {
        this.name = name;
        this.jumpLimit = jumpLimit;
        this.runLimit = runLimit;
    }


    @Override
    public void jump(float height) {
        System.out.println("Cat " + name + " jump to " + height + " meters");
    }

    @Override
    public void run(int distance) {
        System.out.println("Cat " + name + " run " + distance + " meters");
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

