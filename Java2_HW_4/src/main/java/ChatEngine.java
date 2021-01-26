import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatEngine {

    public TextField entryFieldText;
    public TextArea massagesField;

    public void send(ActionEvent actionEvent) {
        massagesField.setText(entryFieldText.getText());
    }
}
