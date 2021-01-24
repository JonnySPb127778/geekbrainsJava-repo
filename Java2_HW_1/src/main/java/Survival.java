public interface Survival {

    String getName();

    default void overcome(Obstacle... obstacles) {
        for (Obstacle obstacle : obstacles) {
            if (!obstacle.execute((Competitors) this)) {
                System.out.println(obstacle + " cannot be overcome by " + getName());
                return;
            }
        }
        System.out.println("All obstacles was overcome by " + getName());
    }
}
