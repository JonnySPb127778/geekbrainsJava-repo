import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyClient {
    public static void main(String[] args) throws IOException {

        Scanner consol = new Scanner(System.in);
        AtomicBoolean bRun = new AtomicBoolean(true);

        Socket clnSocket = new Socket("localhost", 5000);
        DataInputStream iStream = new DataInputStream(clnSocket.getInputStream());
        DataOutputStream oStream = new DataOutputStream(clnSocket.getOutputStream());

        Thread receiver = new Thread(() -> {
            try {
                while(true){
                    String rxMsg = iStream.readUTF();
                    System.out.println("From Server: " + rxMsg);
                    if(rxMsg.equals("EXIT")){
                        System.out.println("The command to EXIT was received from the server!");
                        bRun.set(false);
                        break;
                    }
                }
            } catch (IOException exc){
                System.err.println("Server was broken!");
            }
        });
        receiver.setDaemon(true);
        receiver.start();


        try {
            while (bRun.get()) {
                String txMsg = consol.next();
                if(txMsg.equals("exit")) break;
                oStream.writeUTF(txMsg);
                oStream.flush();
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }

    }
}
