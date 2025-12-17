package KPODZ1.Zoo.items;

public class Computer extends Thing {

    int memory;

    public Computer(int number, String name) {
        super(number, name);
    }

    int getMemory() {
        return memory;
    }

    void setMemory(int memory) {
        this.memory = memory;
        return;
    }
}
