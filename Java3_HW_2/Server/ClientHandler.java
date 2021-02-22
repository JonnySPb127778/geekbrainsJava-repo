import javax.sql.DataSource;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;

public class ClientHandler implements Runnable {

    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean running;
    private String nickName;
    private boolean authorization = false;
    private static int cnt = 0;

    private static final String INSERT_USER = "insert into ChatUsers(Nickname, password) values (?, ?)";
    private static final String SELECT_USER = "select * from ChatUsers where Nickname = ? and password = ?";

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

            out.writeUTF("$NickName"); //Запрос у  пользователя $NickName + Password
            out.flush();

            System.out.println("[DEBUG] client " + nickName + " start processing");
            while (running) {
                String msg = in.readUTF(); // Сообщения от клиента
                if (msg.charAt(0) == '$') { // Запрос от клиента?
                    switch (msg) {// Обработчик запросов от клиента
                        case "$list":
                            server.sendListUsers(this);
                            break;
                        case "$new": tmpStrArr = msg.split("@");

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
                    tmpStrArr = msg.split("#");
                    switch (tmpStrArr[0]) {
                        case "@user" :
                            authorization = authorizationUser(tmpStrArr[1], tmpStrArr[2]);
                            break;
                        case "@new"  :
                            createUser(tmpStrArr[1], tmpStrArr[2]);
                            authorization = authorizationUser(tmpStrArr[1], tmpStrArr[2]);
                            break;
                        //case "@rename"  :
                        //    authorizationUser(tmpStrArr[1], tmpStrArr[2]);
                        //    break;
                    }

                }
                else {
                    if(authorization) {
                        tmpStr = msg.replace('>', '@');
                        tmpStrArr = tmpStr.split("@");
                        if(tmpStrArr[1].equals("ALL")) server.broadCastMessage(msg);
                        else server.privateMessage(tmpStrArr[0], tmpStrArr[1], msg);
                    }
                    else {
                        sendMessage("- You are not authorization user -\n- Execute authorization! -");
                    }
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

    private static  Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:ChatUsers.db3");
    }

    private void createUser(String username, String password) {
        try(Connection connection = getConnection();
            PreparedStatement ps = getConnection().prepareStatement(INSERT_USER);) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private boolean authorizationUser(String username, String password) throws IOException {
        boolean bAuthorization = false;
        try(Connection connection = getConnection();
            PreparedStatement ps = getConnection().prepareStatement(SELECT_USER);) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet result = ps.executeQuery();
            if(result.next()) {
                bAuthorization = true;
                System.out.println("[DEBUG] client " + nickName + " renamed by " + username);
                nickName = username;
                sendMessage("- Welcome -");

                server.broadCastMessage( " - " + nickName +" in chat! - ");
                server.broadCastList();
            }
            else {
                bAuthorization = false;
                System.out.println("[DEBUG] client " + nickName + " with " + password + " not authorization !" + username);
                sendMessage("- Incorrect username or password entered -\n- You are not authorization! -");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return bAuthorization;
    }

}