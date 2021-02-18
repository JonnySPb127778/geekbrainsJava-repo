package exercise1_2;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {

        String[] numbers = new String[]{"раз", "два", "три", "четыре", "пять"};
        showArray(numbers);
        swapTwoElements(numbers, 2, 4);
        showArray(numbers);

        ArrayList <String> stringList = arrayToList(numbers);
        showArrayList(stringList);
    }


    private static <T> void swapTwoElements(T[] arr, int indexA, int indexB) {
       if(arr == null)
           System.out.println("У массива нет элементов (не выделена память)!");
       else if(indexA >= arr.length && indexB >= arr.length && indexA < 0 && indexB < 0)
           System.out.println("Индексы массива указывают за пределы массива!");
       else if(indexA == indexB)
           System.out.println("Индексы массива равны, операция перемещения не имеет смыла!");
       else {
           T tmp = arr[indexA];
           arr[indexA] = arr[indexB];
           arr[indexB] = tmp;
       }
    }
    
    public static <T> ArrayList<T> arrayToList(T[] arr) {
        ArrayList<T> list = new ArrayList<>();
        Collections.addAll(list, arr);
        return list;
    }

    public static <T> void showArray(T[] arr) {
        for (int i = 0; i < arr.length-1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length-1] + "\n");
    }

    private static void showArrayList(ArrayList<String> strList) {
        for (int i = 0; i < strList.size()-1; i++) {
            System.out.print(strList.get(i) + ", ");
        }
        System.out.print(strList.get(strList.size()-1) + "\n");
    }

}
