import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args)  {
        int port;
        if (args.length == 0) {
            System.out.println("Please specify a port number");
            System.exit(0);
        }

        port = Integer.parseInt(args[0]);

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started!");
            System.out.println("host: " + InetAddress.getLocalHost().getHostName());
            System.out.println("port: " + port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n Listening for connections... \n");

        while (true) {
            Socket newSocket = null;

            try {
                newSocket = serverSocket.accept();

                DataInputStream newInputStream = new DataInputStream(newSocket.getInputStream());
                DataOutputStream newOutputStream = new DataOutputStream(newSocket.getOutputStream());
                Thread myThread = new ConnectionHandler(newSocket, newInputStream, newOutputStream);
                myThread.start();
                System.out.println("Incoming connection from: " + newSocket.getInetAddress().getHostAddress());
            } catch (Exception e) {
                if (newSocket != null) {
                    try {
                        newSocket.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                e.printStackTrace();
            }
        }
    }

}