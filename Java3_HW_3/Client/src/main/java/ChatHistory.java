import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChatHistory implements  ReadWriteLinesToFile {

 //   private ConcurrentLinkedQueue<String> history;
    private static File file;
    private PrintWriter printWriter;

    private ChatHistory(PrintWriter printWriter) throws FileNotFoundException {
        this.printWriter = printWriter;
    }

//    public static File checkFile(String nick) throws IOException {
//        // проверка на существование объекта файл с заданным путём
//        if (file == null) {
//            file = new File("src/users/history" + nick + ".txt"); //" +
//        }
//        // проверка существует файл по заданному пути, если нет то создать*/
//      //  file.createNewFile();
//        return file;
//    }

    public static ChatHistory getInstance(File file) throws FileNotFoundException {
        try {
            return new ChatHistory(new PrintWriter(new FileOutputStream(file, true), true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void close(){
        printWriter.close();
    }

    @Override
    public void writeLineToFile(String line) {
        printWriter.println(line);
    }

    @Override
    public void getLastLines(File file, Integer numberOfLines, ListView<String> listView) {
        String[] stringArray = new String[numberOfLines];
        int ls = 0;
        try(ReversedLinesFileReader reader = new ReversedLinesFileReader (file, Charset.defaultCharset())){
            for (; ls < numberOfLines; ls++) {
                String line = reader.readLine();
                if(line == null) break;
                stringArray[ls] = line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (ls--; ls >= 0; ls--) {
            listView.getItems().add(stringArray[ls]);
        }
    }
}
