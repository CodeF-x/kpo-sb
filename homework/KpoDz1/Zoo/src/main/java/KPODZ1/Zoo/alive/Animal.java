package KPODZ1.Zoo.alive;

import KPODZ1.Zoo.interfaces.IAlive;
import KPODZ1.Zoo.interfaces.IInventory;

public abstract class Animal implements IAlive, IInventory {

    private int number;
    private String name;
    private int food;

    private boolean isHealthy;

    public Animal(int number, String name, int food, boolean healthy) {
        this.number = number;
        this.name = name;
        this.food = food;
        this.isHealthy = healthy;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
        return;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getFood() {
        return this.food;
    }

    @Override
    public void setFood(int food) {
        this.food = food;
        return;
    }

    public boolean checkUp() {
        return isHealthy;
    }

    public abstract boolean contactable();
}
