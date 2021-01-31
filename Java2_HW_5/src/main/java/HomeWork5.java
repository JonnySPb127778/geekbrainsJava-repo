public class HomeWork5 {

    public static void main(String[] args) {

        final int size = 10000000;
        float[] array = new float[size];

    // Первый метод просто бежит по массиву и вычисляет значения.
        fillArrayOnes(array);
        CulcArray1(array);
    // Второй метод разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.
        fillArrayOnes(array);
        CulcArray2(array);
    // Третий метод в двумя потоками высчитывает новые значения в текущем массиве.
        fillArrayOnes(array);
        CulcArray3(array);
    }

    private static void CulcArray1(float[] arr) {
        System.out.println("Первый метод просто бежит по массиву и вычисляет значения.");
        long t0 = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println((System.currentTimeMillis() - t0) + " мс");
    }

    private static void CulcArray2(float[] arr) {

        final int half = arr.length / 2;

        float[] a1 = new float[half];
        float[] a2 = new float[half];

        System.out.println("Второй метод разбивает массив на два массива, в двух потоках высчитывает новые значения " +
                           "и потом склеивает эти массивы обратно в один.");
        long t0 = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, half);
        System.arraycopy(arr, half, a2, 0, half);

        Thread firstHalf = new Thread(() ->{
            for (int i = 0; i < half; i++) {
                a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread secondHalf = new Thread(() ->{
            for (int i = 0; i < half; i++) {
                a2[i] = (float)(a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        firstHalf.start();
        secondHalf.start();

        while (firstHalf.isAlive() ||  secondHalf.isAlive()); // ожидание завершения задач в потоках

        System.arraycopy(a1, 0, arr, 0, half);
        System.arraycopy(a2, 0, arr, half, half);

        System.out.println((System.currentTimeMillis() - t0) + " мс");
    }

    private static void CulcArray3(float[] arr) {

        System.out.println("Третий метод в двумя потоками высчитывает новые значения в текущем массиве.");
        long t0 = System.currentTimeMillis();

        Thread firstHalf = new Thread(() ->{
            for (int i = 0; i < arr.length/2; i++) {
                arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread secondHalf = new Thread(() ->{
            for (int i = arr.length/2; i < arr.length; i++) {
                arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        firstHalf.start();
        secondHalf.start();

        while (firstHalf.isAlive() ||  secondHalf.isAlive()); // ожидание завершения задач в потоках

        System.out.println((System.currentTimeMillis() - t0) + " мс");
    }

    public static void fillArrayOnes(float[] arr){
        for (int i=0; i<arr.length;i++) arr[i] = 1.0f;
    }
}
