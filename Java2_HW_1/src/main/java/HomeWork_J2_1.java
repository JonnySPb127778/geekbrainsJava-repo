public class HomeWork_J2_1 {

    public static void main(String[] args) {
        Competitors[] persons = {
                new Human("Jonny",1, 300),
                new Human("Tonny",2, 1000),
                new Human("Lenny",3, 10000),
                new Cat("Tom", 2, 100),
                new Cat("Max", 3, 150),
                new Robot("H3PO", 2, 2000)
        };


        for (Competitors competitors : persons) {
            competitors.overcome(
                    new Wall(1.2f),
                    new RunningTrack(100),
                    new Wall(2.0f),
                    new RunningTrack(1000),
                    new Wall(2.5f));
        }
    }
}
