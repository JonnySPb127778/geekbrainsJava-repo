import javafx.scene.control.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ReadWriteLinesToFile extends  AutoCloseable{
    void close() throws FileNotFoundException;
    void writeLineToFile(String line);
    void getLastLines(File file, Integer numberOfLines, ListView<String> listView) throws IOException;
}
