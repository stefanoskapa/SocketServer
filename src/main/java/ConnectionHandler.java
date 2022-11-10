import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

class ConnectionHandler extends Thread {
    final DataInputStream inputStream;
    final DataOutputStream outputStream;
    final Socket socket;


    public ConnectionHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        String inputString;

        LISTENER:
        while (true) {
            try {
                inputString = inputStream.readUTF();
                if (inputString.equals("killme"))
                    break LISTENER;
                else
                    System.out.println("Client sent: " + inputString);

            } catch (SocketException e) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            this.inputStream.close();
            this.outputStream.close();
            System.out.println("Client disconnected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}