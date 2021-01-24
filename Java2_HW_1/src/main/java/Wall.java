public class Wall implements Obstacle {
    float height;

    public Wall (float height){
        this.height = height;
    }

    @Override
    public boolean execute(Competitors competitor) {
        if (competitor.getJumpLimit() > height) {
            competitor.jump(height);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Wall{" +
                "height=" + height +
                '}';
    }
}
