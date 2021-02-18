package exercise3;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private final ArrayList<T> fruits = new ArrayList<>();

    public void addFruit(T fruit) {
        fruits.add(fruit);
    }

    public float getWeight() {
        if(fruits.size() > 0) {
            return fruits.size() * fruits.get(0).getWeight();
        }
        return 0;
    }

    public int getQuantityFruit() {
        return fruits.size();
    }

    public String getFruit(int index) {
        return fruits.get(index).toString();
    }

    public boolean compare(Box<?> box) {
        return this.getWeight() == box.getWeight();
    }

    public void emptyTheBox(Box<T> other) {
        if(this == other || null == other) return;
        other.fruits.addAll(fruits); // не понял данной записи? и почему не так: other.addAll(fruits);
        fruits.clear();
    }
}
