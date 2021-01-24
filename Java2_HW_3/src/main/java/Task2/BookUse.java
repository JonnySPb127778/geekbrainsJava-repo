package Task2;

public class BookUse {
    public static void main(String[] args) {
        PhoneBook myBook = PhoneBook.createInstance();

        myBook.addContact("Lisa", "89218884877");
        myBook.addContact("Katya", "+7(921)878-36-27");
        myBook.addContact("Eugene", "8(921)3332098");
        myBook.addContact("Eugene", "8(921)895-7822");
        myBook.addContact("Eugene", "2245876");
        myBook.addContact("Eugene", "8(812)3772237");
        myBook.addContact("Nadya", "8(921)888-32-57");
        myBook.addContact("Nadya", "8(8412)88-33-77");

        System.out.println("Lisa\thave "+ myBook.getQuantityNumbers("Lisa") + " number(s): " + myBook.getContacts("Lisa") );
        System.out.println("Katya\thave "+ myBook.getQuantityNumbers("Katya") + " number(s): " + myBook.getContacts("Katya") );
        System.out.println("Eugene\thave "+ myBook.getQuantityNumbers("Eugene") + " number(s): " + myBook.getContacts("Eugene") );
        System.out.println("Nadya\thave "+ myBook.getQuantityNumbers("Nadya") + " number(s): " + myBook.getContacts("Nadya") );

    }
}
