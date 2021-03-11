public class Tests {
    private static final boolean A = true ;
    private static final boolean B = false ;

    @Test(priority = 2)
    public static void Test2Lvl(){
        System.out.println("\nTest of 2 priority level.");
        Logic.view();
        System.out.println("Not(a AND b) = " + Logic.logicNotAND());
    }

    @BeforeSuit
    public static void beforeSuit(){
        Logic arg = new Logic(A,B);
        System.out.println("\nTest \"BeforeSuit\".  Set value of arguments.");
        Logic.view();
        System.out.println();
    }

    @Test(priority = 1)
    public static void TestLowestLvl(){
        System.out.println("\nTest of lowest priority level.");
        Logic.view();
        System.out.println("a AND b = " + Logic.logicAND());
    }

    @Test
    public static void TestDefaultLvl(){
        System.out.println("\nTest of default priority level.");
        Logic.view();
        System.out.println("a OR b = " + Logic.logicOR());
    }

    @Test(priority = 10)
    public static void TestHighestLvl(){
        System.out.println("\nTest of highest priority level.");
        Logic.view();
        System.out.println("a XOR b = " + Logic.logicXOR());
    }

    @Test(priority = 6)
    public static void TestLvl6(){
        System.out.println("\nTest of 6 priority level.");
        Logic.view();
        System.out.println("Not(a XOR b) = " + Logic.logicNotXOR());
    }

    @AfterSuit
    public static void afterSuit(){
        System.out.println("\nTest \"AfterSuit\".  Reset value of arguments.");
        Logic.reset();
        Logic.view();
    }

    @Test(priority = 8)
    public static void Test8Lvl(){
        System.out.println("\nTest of 8 priority level.");
        Logic.view();
        System.out.println("Not(a OR b) = " + Logic.logicNotOR());
    }
}
