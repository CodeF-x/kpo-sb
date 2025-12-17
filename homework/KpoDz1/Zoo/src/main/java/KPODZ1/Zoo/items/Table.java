package KPODZ1.Zoo.items;

public class Table extends Thing {

    int area;

    public Table(int number, String name) {
        super(number, name);
    }

    int getArea() {
        return area;
    }

    void setArea(int area) {
        this.area = area;
        return;
    }
}
