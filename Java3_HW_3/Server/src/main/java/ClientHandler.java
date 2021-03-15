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
    private static final String CHANGE_NICK = "update ChatUsers set Nickname = ? where Nickname = ?";
    private static final String SELECT_USER = "select * from ChatUsers where Nickname = ? and password = ?";
    private static final String SELECT_NICK = "select * from ChatUsers where Nickname = ?";

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

            out.writeUTF("$NickPassword"); //Запрос у  пользователя $NickName + Password
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
                            out.writeUTF(" - Unknown command! - ");
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
                            if(nickExists(tmpStrArr[1])) {
                                sendMessage(" - Such user already exists -\n- Create another user - ");
                            }
                            else {
                                createUser(tmpStrArr[1], tmpStrArr[2]);
                                sendMessage(" - User created -\n- Welcome - ");
                                sendMessage("@NewNick>" + nickName);
                                authorization = true;
                                System.out.println("[DEBUG] new client " + nickName + " named by " + tmpStrArr[1]);
                                nickName = tmpStrArr[1];
                                server.broadCastMessage( " - " + nickName +" in chat! - ");
                                server.broadCastList();
                            }
                            break;
                        case "@rename"  :
                            if(userExists(tmpStrArr[1], tmpStrArr[2])) {
                                System.out.println("[DEBUG] client " + nickName + " renamed by " + tmpStrArr[3]);
                                server.broadCastMessage( " - User " + nickName +" change nickname to " + tmpStrArr[3] + " - ");
                                renameUser(nickName, tmpStrArr[3]);
                                nickName = tmpStrArr[3];
                                sendMessage("@NewNick>" + nickName);

                                server.broadCastList();
                            }
                            break;
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
                        sendMessage(" - You are not authorization user - \n - Execute authorization! - ");
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
        return DriverManager.getConnection("jdbc:sqlite:ChatUsers.db");
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

    private void renameUser(String username, String newname) {
        try(Connection connection = getConnection();
            PreparedStatement ps = getConnection().prepareStatement(CHANGE_NICK);) {
            ps.setString(1, newname);
            ps.setString(2, username);
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
                System.out.println("[DEBUG] client " + nickName + " named by " + username);
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


    private boolean userExists(String username, String password) throws IOException {
        boolean bExists = false;
        try(Connection connection = getConnection();
            PreparedStatement ps = getConnection().prepareStatement(SELECT_USER);) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet result = ps.executeQuery();
            if(result.next()) {
                bExists = true;
                System.out.println("[DEBUG] client " + nickName + " with " + password + " exists!");
            }
            else {
                System.out.println("[DEBUG] client " + nickName + " with " + password + " not exists!");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bExists;
    }

    private boolean nickExists(String username) throws IOException {
        boolean bExists = false;
        try(Connection connection = getConnection();
            PreparedStatement ps = getConnection().prepareStatement(SELECT_NICK);) {
            ps.setString(1, username);
            ResultSet result = ps.executeQuery();
            if(result.next()) {
                bExists = true;
                System.out.println("[DEBUG] client " + nickName + " exists!");
            }
            else {
                System.out.println("[DEBUG] client " + nickName + " not exists!");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bExists;
    }

}