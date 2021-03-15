import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Server {

    private static final int DEFAULT_PORT = 8189;

    private ConcurrentLinkedDeque<ClientHandler> clients;

    public Server(int port) {
        clients = new ConcurrentLinkedDeque<>();
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("[DEBUG] server started on port: " + port);
            while (true) {
                Socket socket = server.accept(); // get connection
                ClientHandler handler = new ClientHandler(socket, this);
                System.out.println("[DEBUG] client " + handler.getNickName() + " accepted");
                addClient(handler);
                new Thread(handler).start();
            }
        } catch (Exception e) {
            System.err.println("Server was broken");
        }
    }

    public void addClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
        System.out.println("[DEBUG] client " + clientHandler.getNickName() + " added to broadcast queue");
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        System.out.println("[DEBUG] client " + clientHandler.getNickName() + " removed from broadcast queue");
    }

    public void broadCastMessage(String msg) throws IOException {
        for (ClientHandler client : clients) {
            client.sendMessage(msg);
        }
    }

    public void privateMessage(String from, String whom, String msg) throws IOException {
        for (ClientHandler client : clients) {
            if(client.getNickName().equals(from)) client.sendMessage(msg);
            if(client.getNickName().equals(whom)) client.sendMessage(msg);
        }
    }

    public void sendListUsers (ClientHandler client) throws IOException {
        String msg = new String();
        msg = "@List>You";
        int numClients = 0;

        for(ClientHandler user : clients) {
            if(client.getNickName().equals(user.getNickName())){
                msg = "@List>"+user.getNickName()+" (You)";
                break;
            }
        }

        for(ClientHandler user : clients){
            numClients++;
            if(!client.getNickName().equals(user.getNickName())){
                msg = msg + "\n" + user.getNickName();
            }
        }
        client.sendMessage(msg + "\n#" + numClients);
    }

    public void broadCastList() throws IOException {
        for (ClientHandler client : clients) {
            sendListUsers(client);
        }
    }

    public static void main(String[] args) throws Exception {
        int port = -1;
        if (args !=  null && args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        if (port == -1) {
            port = DEFAULT_PORT;
        }



        new Server(port);

    }
}