package KPODZ1.Zoo.alive;

public class Rabbit extends Herbivore {

    int jumpHeight;

    public Rabbit(int number, String name, int food, boolean healthy, int kindness) {
        super(number, name, food, healthy, kindness);
    }

    int getJummpHeight() {
        return jumpHeight;
    }

    void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
        return;
    }
}
