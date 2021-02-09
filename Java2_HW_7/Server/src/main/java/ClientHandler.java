import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean running;
    private String nickName;
    private static int cnt = 0;

    String [] tmpStrArr = new String[5];
    String tmpStr;


    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        running = true;
        cnt++;
        nickName = "user" + cnt;
    }

    @Override
    public void run() {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            out.writeUTF("$NickName");
            out.flush();

            System.out.println("[DEBUG] client " + nickName + " start processing");
            while (running) {
                String msg = in.readUTF(); // Сообщения от клиента
                if (msg.charAt(0) == '$') { // Запрос от клиента?
                    switch (msg) {// Обработчик запросов от клиента
                        case "$list":
                            server.sendListUsers(this);
                            break;
                        case "$quit":
                            out.writeUTF(msg);
                            out.flush();
                            running = false;
                            break;
                        default:
                            out.writeUTF("Unknown command!");
                            out.flush();
                    }
                }
                else if(msg.charAt(0) == '@') {
                    tmpStrArr = msg.split("@");
                    System.out.println("[DEBUG] client " + nickName + " renamed by " + tmpStrArr[1]);
                    nickName = tmpStrArr[1];
                    server.broadCastMessage( " - " + nickName +" in chat! - ");
                    server.broadCastList();

                }
                else {
                    tmpStr = msg.replace('>', '@');
                    tmpStrArr = tmpStr.split("@");
                    if(tmpStrArr[1].equals("ALL")) server.broadCastMessage(msg);
                    else server.privateMessage(tmpStrArr[0], tmpStrArr[1], msg);
                }
                System.out.println("[DEBUG] message from client: " + msg);

            }

        } catch (Exception e) {
            System.err.println("Handled connection was broken");
            String nName = nickName;
            server.removeClient(this); // Удаление пользователя
            try {
                server.broadCastMessage(" - " + nName + " left chat! -");
                server.broadCastList();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
    }

    public String getNickName(){
        return nickName;
    }

}