package KPODZ1.Zoo.items;

import KPODZ1.Zoo.interfaces.IInventory;

public abstract class Thing implements IInventory {

    int number;
    String name;

    public Thing(int number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
        return;
    }

    @Override
    public String getName() {
        return name;
    }

}
