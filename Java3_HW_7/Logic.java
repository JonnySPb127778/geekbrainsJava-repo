public class Logic {

    private static boolean a;
    private static boolean b;

    public Logic(boolean a, boolean b){
        this.a = a;
        this.b = b;
    }

    public static boolean logicAND(){
        return a && b;
    }

    public static boolean logicNotAND(){
        return !(a && b);
    }

    public static boolean logicOR(){
        return a || b;
    }

    public static boolean logicNotOR(){
        return !(a || b);
    }

    public static boolean logicXOR(){
        return a ^ b;
    }

    public static boolean logicNotXOR(){
        return !(a ^ b);
    }

    public static void reset(){
        a = false;
        b = false;
    }

    public static void view(){
        System.out.print("a = " + a + "   b = " + b + "    ");
    }

}
