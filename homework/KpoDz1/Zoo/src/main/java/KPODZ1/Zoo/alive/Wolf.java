package KPODZ1.Zoo.alive;

public class Wolf extends Predator {

    int legsLenght;

    public Wolf(int number, String name, int food, boolean healthy) {
        super(number, name, food, healthy);
    }

    int getLegsLenght() {
        return legsLenght;
    }

    void setLegsLength(int legsLenght) {
        this.legsLenght = legsLenght;
        return;
    }
}
