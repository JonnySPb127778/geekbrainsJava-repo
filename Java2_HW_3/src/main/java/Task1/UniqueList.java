package Task1;

import java.util.TreeMap;

public class UniqueList {
    public static void main(String[] args){
        String song = (
                "Oh jingle bells jingle bells " +
                "jingle all the way " +
                "Oh what fun it is to ride " +
                "In a one horse open sleigh");

        String[] words =  song.split(" +");

        TreeMap<String, Integer> map = new TreeMap<>();

        for (String word: words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        System.out.print(map);
    }
}
