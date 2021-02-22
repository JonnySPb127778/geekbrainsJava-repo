import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ChatController implements Initializable {

    private static final String HOST = "localhost";
    private static final int PORT = 8189;
    private static String host;
    private static int port;

    public ListView<String> chatView;
    public TextArea inChat;
    public TextField consoleIn;
    public TextField nick;
    public TextField whom;
    public TextField numberClients;
    public TextField setHost;
    public TextField setPort;

    public String nickName;
    public PasswordField password;

    private Network network;

    String [] tmpStrArr1 = new String[5];
    String [] tmpStrArr2 = new String[5];
    String tmpStr;
    boolean bReplacement;


    public void sendMessage(ActionEvent actionEvent) throws IOException {
        network.writeMessage(nickName + "@" + whom.getText() + "> " + consoleIn.getText());
        consoleIn.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void selectNickName(ActionEvent actionEvent) throws IOException {
        network.writeMessage("@new#" + nickName + "#" + password.getText());
        //network.writeMessage(nickName + "@" + whom.getText() + "> User " + nickName + " changed his nickname to " + nick.getText() + "!!!");
        nickName = nick.getText();
        //network.writeMessage("@" + nickName + "@" + password.getText());

    }

    public void selectALL(ActionEvent actionEvent) {
        whom.setText("ALL");
    }

    public void disconnect(ActionEvent actionEvent) throws IOException {
        network.writeMessage("$quit");
        network.close();

    }

    public void connect(ActionEvent actionEvent) throws IOException {

        if (setHost.getText().equals("")) {
            setHost.setText(HOST);
            host = HOST;
        }

        try {
            if (setPort.getText().equals("")) {
                setPort.setText(String.valueOf(PORT));
                port = PORT;
            } else port = Integer.parseInt(setPort.getText());
        } catch (NumberFormatException e){
            setPort.setText(String.valueOf(PORT));
            port = PORT;
        }

        nickName = nick.getText(); // Взять значение на момент старта
        if(nickName.equals("")) {
            nickName = "User" + (int) (Math.random() * 899 + 100);
            nick.setText(nickName);
        }
        if(password.getText().equals("")) password.setText("12345");


        network = Network.getInstance(HOST, PORT);

        new Thread(() -> {
            try {
                while (true) {
                    String rxMsg = network.readMessage();
                    if (rxMsg.charAt(0) == '$') { // Запрос от сервера?
                        switch (rxMsg) {// Обработчик запросов от сервера
                            case "$NickName":
                                network.writeMessage("@user#" + nickName + "#" + password.getText());
                                break;
                            default:
                                network.writeMessage("Unknown command!");
                        }
                    } else if(rxMsg.charAt(0) == '@'){ // Сообщение от сервера?
                        tmpStrArr1 = rxMsg.split(">");// выделение кода команды
                        switch (tmpStrArr1[0]) {// Обработчик сообщений от сервера
                            case "@List":
                                tmpStr = rxMsg.replace('>', '#');
                                tmpStrArr1 = tmpStr.split("#");
                                numberClients.setText(tmpStrArr1[2]); // выделение количества участников чата
                                inChat.clear();
                                inChat.setText(tmpStrArr1[1]);
                                break;

                            case "@Quit":
                                network.close();
                                break;
                            default:{
                                network.writeMessage("Unknown message!");
                            }
                        }
                    }
                    else {// Замена своего nickName на You
                        bReplacement = false;
                        tmpStrArr1 = rxMsg.split("@");
                        if(tmpStrArr1[0].equals(nickName)) {
                            tmpStrArr1[0] = "You";      // в поле от кого
                            bReplacement = true;
                        }
                        try {
                            tmpStrArr2 = tmpStrArr1[1].split(">");
                            if (tmpStrArr2[0].equals(nickName)) {
                                tmpStrArr2[0] = "You";      // в поле кому
                                bReplacement = true;
                            }
                        } catch (ArrayIndexOutOfBoundsException e){
                            bReplacement = false;
                        };

                        if (bReplacement)
                            rxMsg = tmpStrArr1[0] + "@" + tmpStrArr2[0] + "> " + tmpStrArr2[1];

                        String finalRxMsg = rxMsg;
                        Platform.runLater(() -> chatView.getItems().add(finalRxMsg));
                    }


                }
            } catch (IOException ioException) {
                System.err.println("Server was broken");
                Platform.runLater(() -> chatView.getItems().add("Server was broken"));
            }
        }).start();
    }
}