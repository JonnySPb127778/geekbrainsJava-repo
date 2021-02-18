package exercise3;

public class Main {
    public static void main(String[] args) {
        Box<Apple>  box1 = new Box();
        Box<Orange> box2 = new Box();

        Apple apple1 = new Apple();
        Apple apple2 = new Apple();
        Apple apple3 = new Apple();
        Apple apple4 = new Apple();

        Orange orange1 = new Orange();
        Orange orange2 = new Orange();
        Orange orange3 = new Orange();
        Orange orange4 = new Orange();


        box1.addFruit(apple1);
    //    box1.addFruit(orange2); // И точно не даёт положить не тот фрукт
        box1.addFruit(apple3);


        box2.addFruit(orange1);
    //    box2.addFruit(apple2);// И точно не даёт положить не тот фрукт
        box2.addFruit(orange3);


        showBox(box1);
        showBox(box2);


    }

    private static void showBox(Box box) {
        System.out.println("Коробка "+ box.toString());
        for (int i = 0; i < box.getQuantityFruit(); i++) {
            System.out.println(box.getFruit(i));
        }
    }
}
