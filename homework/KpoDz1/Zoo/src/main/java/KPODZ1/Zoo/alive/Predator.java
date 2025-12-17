package KPODZ1.Zoo.alive;

public abstract class Predator extends Animal {

    public Predator(int number, String name, int food, boolean healthy) {
        super(number, name, food, healthy);
    }

    @Override
    public boolean contactable() {
        return false;
    }
}
