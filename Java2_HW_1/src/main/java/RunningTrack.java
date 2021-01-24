public class RunningTrack implements Obstacle {
    int length;

    public RunningTrack (int length){
        this.length = length;
    }

    @Override
    public boolean execute(Competitors competitor) {
        if (competitor.getRunLimit() > length) {
            competitor.run(length);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Track{" +
                "length=" + length +
                '}';
    }
}

