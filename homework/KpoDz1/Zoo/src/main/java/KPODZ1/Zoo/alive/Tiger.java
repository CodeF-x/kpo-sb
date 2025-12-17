package KPODZ1.Zoo.alive;

public class Tiger extends Predator {

    int stripesNumber;

    public Tiger(int number, String name, int food, boolean healthy) {
        super(number, name, food, healthy);
    }

    int getStripesNumber() {
        return stripesNumber;
    }

    void setStripesNumber(int stripesNumber) {
        this.stripesNumber = stripesNumber;
        return;
    }
}
