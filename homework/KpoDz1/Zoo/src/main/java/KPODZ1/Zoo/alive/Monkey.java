package KPODZ1.Zoo.alive;

public class Monkey extends Herbivore {

    int iq;

    public Monkey(int number, String name, int food, boolean healthy, int kindness) {
        super(number, name, food, healthy, kindness);
    }

    int getIq() {
        return iq;
    }

    void setIq(int iq) {
        this.iq = iq;
        return;
    }
}
