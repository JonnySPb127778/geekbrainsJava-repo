import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer {
    private boolean bRunning;
    DataInputStream iStream;
    DataOutputStream oStream;
    Scanner consol = new Scanner(System.in);

    public MyServer(){
        bRunning = true;
        try (ServerSocket myServer = new ServerSocket(5000);) {
            System.out.println("Server started!");
            while(bRunning) {
                Socket socket = myServer.accept();
                try{
                    handle(socket);
                } catch (IOException ioException) {
                    System.out.println("Client connection was broken");
                }
            }
        } catch (IOException exc) {
           exc.printStackTrace();
        }
    }

    private void handle (Socket socket) throws IOException {
        iStream = new DataInputStream(socket.getInputStream());
        oStream = new DataOutputStream(socket.getOutputStream());

        Thread receiver = new Thread (() ->{
            while(true) {
                try{
                    String rxMsg = iStream.readUTF();
                    System.out.println("From Client: " + rxMsg);
                    if(rxMsg.equals("ip")) {
                        System.out.println("to Client: Server ip-address: /" + socket.getLocalAddress().toString());
                        oStream.writeUTF("Server ip-address: /" + socket.getLocalAddress().toString());
                        oStream.flush();
                    }
                    else {
                        oStream.writeUTF("Server repeat: " + rxMsg);
                        oStream.flush();
                    }
                }catch (IOException e){

                }
            }
        });

        Thread transmitter = new Thread (() ->{
            try {
                while(true) {
                    String txMsg = consol.next();
                    System.out.println("to Client: " + txMsg);

                    oStream.writeUTF(txMsg);
                    oStream.flush();
                }
            } catch (IOException exc){
                exc.printStackTrace();
            }
        });

        receiver.start();
        transmitter.start();
    }
// Где мы используем данный метод в примере непонятно? А нужен ли он?
//    public void setRunning(boolean bRunning){
//        this.bRunning = bRunning;
//    }

    public static void main(String[] args) throws Exception{
        new MyServer();
    }

}

