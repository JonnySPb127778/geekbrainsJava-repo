import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunner {
    public static void start (Class testX) throws InvocationTargetException, IllegalAccessException {
        List<Method> testMethods = new ArrayList<>();
        Method[] declaredMethods = testX.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if(declaredMethod.isAnnotationPresent(Test.class)) {
                testMethods.add(declaredMethod);
            }
        }

        testMethods.sort(Comparator.comparingInt((Method m)->m.getAnnotation(Test.class).priority()).reversed());

        for (Method declaredMethod : declaredMethods) {

            if(declaredMethod.isAnnotationPresent(BeforeSuit.class)) {
                if(testMethods.size() > 0 && testMethods.get(0).isAnnotationPresent(BeforeSuit.class)){
                    throw new RuntimeException("Method \"BeforeSuit\" is not the only one!");
                }
                testMethods.add(0,declaredMethod);
            }

            if(declaredMethod.isAnnotationPresent(AfterSuit.class)) {
                if(testMethods.size() > 0 && testMethods.get(testMethods.size()-1).isAnnotationPresent(AfterSuit.class)){
                    throw new RuntimeException("Method \"AfterSuit\" is not the only one!");
                }
                testMethods.add(declaredMethod);
            }
        }

        for (Method testMethod : testMethods) {
            testMethod.invoke(null);
        }
    }
}
