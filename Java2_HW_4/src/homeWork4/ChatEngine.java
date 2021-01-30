package homeWork4;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ChatEngine {

    public TextField entryFieldText;
    public ListView<String> massagesList;

    public void send_btn(ActionEvent actionEvent) {
        send();
    }

    public void send() {
        massagesList.getItems().add(entryFieldText.getText() + "\n");
        entryFieldText.clear();
    }
}
