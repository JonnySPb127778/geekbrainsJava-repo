import org.junit.Test;
import org.junit.Assert;

public class Tests {
    TestedMethods testSubjects = new TestedMethods();

    @Test(expected = RuntimeException.class)
    public void testArrayAfterLast4_Exp(){
        int[] arrInp = {1, 2, 3, 4, 5};
    //    int[] expectedArr= {};
    //    RuntimeException.class exception = testSubjects.copyArrayAfterLast4(arrInp);
    //    Assert.assertThrows(RuntimeException.class, java.lang.RuntimeException);
        int[] arrOut = testSubjects.copyArrayAfterLast4(arrInp);
    }

    @Test
    public void testArrayAfterLast4_Last(){
        int[] arrInp = {1, 2, 3, 5, 4};
        int[] expectedArr= {};
        int[] arrOut = testSubjects.copyArrayAfterLast4(arrInp);
        Assert.assertArrayEquals(expectedArr,arrOut);
    }

    @Test
    public void testArrayAfterLast4_Ones(){
        int[] arrInp = {1, 2, 3, 4, 5};
        int[] expectedArr= {5};
        int[] arrOut = testSubjects.copyArrayAfterLast4(arrInp);
        Assert.assertArrayEquals(expectedArr,arrOut);
    }

    @Test
    public void testArrayAfterLast4_First(){
        int[] arrInp = {4, 1, 2, 3, 5};
        int[] expectedArr= {1, 2, 3, 5};
        int[] arrOut = testSubjects.copyArrayAfterLast4(arrInp);
        Assert.assertArrayEquals(expectedArr,arrOut);
    }

    @Test
    public void testArrayAfterLast4_Middle(){
        int[] arrInp = {1, 2, 4, 3, 5};
        int[] expectedArr= {3, 5};
        int[] arrOut = testSubjects.copyArrayAfterLast4(arrInp);
        Assert.assertArrayEquals(expectedArr,arrOut);
    }

    @Test
    public void testArrayOf1and4_ExtraNumbers(){
        int[] arrInp = {1, 4, 4, 1, 5};
        Assert.assertFalse(testSubjects.arrayOf1and4(arrInp));
    }

    @Test
    public void testArrayOf1and4_OnlyOnes(){
        int[] arrInp = {1, 1, 1, 1, 1};
        Assert.assertFalse(testSubjects.arrayOf1and4(arrInp));
    }
    @Test
    public void testArrayOf1and4_OnlyFours(){
        int[] arrInp = {4, 4, 4, 4, 4};
        Assert.assertFalse(testSubjects.arrayOf1and4(arrInp));
    }

    @Test
    public void testArrayOf1and4_OnesAndFours(){
        int[] arrInp = {1, 4, 1, 4, 1};
        Assert.assertTrue(testSubjects.arrayOf1and4(arrInp));
    }
}
