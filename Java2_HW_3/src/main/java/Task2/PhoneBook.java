package Task2;

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Set;

public class PhoneBook {
    private static PhoneBook instance;
    private Map<String, Set<String>> book;

    public PhoneBook() {
        book = new TreeMap<>();
    }

    public static PhoneBook createInstance(){
        if(instance == null) instance = new PhoneBook();
        return instance;
    }

    public Set<String> getContacts(String name) {
        return book.get(name);
    }

    public int getQuantityNumbers(String name) {
        String[] numbers = book.get(name).toString().split(", ");
        return numbers.length;
    }

    public void addContact(String name, String number) {
        validate(number);
        if(!book.containsKey(name)) {
            book.put(name, new TreeSet<>());
        }
        book.get(name).add(number);
    }

    private void validate(String num){
        if(!num.matches("[0-9,(,),+,-]{7,16}")){
            throw new IllegalArgumentException(num + " not a telephone number!");
        };
    }
}
