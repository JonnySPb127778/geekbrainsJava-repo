import javafx.scene.control.PasswordField;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChatHistory {

//    private ConcurrentLinkedQueueeque<UserHistory> history;
    private static File file;


//    public void addClient(ClientHandler clientHandler) {
//        clients.add(clientHandler);
//        System.out.println("[DEBUG] client " + clientHandler.getNickName() + " added to broadcast queue");
//    }

    public static File checkFile(String nick) throws IOException {
        // проверка на существование объекта файл с заданным путём
        if (file == null) {
            file = new File(nick+".txt");
        }
        // проверка существует файл по заданному пути, если нет то создать
        file.createNewFile();


        return file;
    }

 //   @Override
 //   public void writeLineToFile(String line) {
 //       printWriter.println(line);
 //   }




}
