package KPODZ1.Zoo.alive;

public abstract class Herbivore extends Animal {

    private int kindness;

    public Herbivore(int number, String name, int food, boolean healthy, int kindness) {
        super(number, name, food, healthy);
        setKindness(kindness);
    }

    @Override
    public boolean contactable() {
        if (kindness > 5) {
            return true;
        } else {
            return false;
        }
    }

    int getKindness() {
        return kindness;
    }

    void setKindness(int kindness) {
        if (kindness < 0) {
            this.kindness = 0;
            return;
        }
        if (kindness > 10) {
            this.kindness = 10;
            return;
        }
        this.kindness = kindness;
        return;
    }
}
