import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {


    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private static Network instance;
    private static String host;
    private static int port;

    public static Network getInstance(String serverHost, int serverPort) {
        if (instance == null) {
            instance = new Network(serverHost, serverPort);
        }
        return instance;
    }


    private Network(String host, int port) {
        this.host = host;
        this.port = port;

        try {
            socket = new Socket(host, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.err.println("Problem with " + host + " server on port: " + port);
        }
    }

    public void writeMessage(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
    }

    public String readMessage() throws IOException {
        return in.readUTF();
    }

    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}