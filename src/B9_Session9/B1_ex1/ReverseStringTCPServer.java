package B9_Session9.B1_ex1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReverseStringTCPServer
{
    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server (DataStream) đang đợi kết nối...");
            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream dis = new DataInputStream(socket.getInputStream());
                     DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

                    var msg = dis.readUTF();
                    var rMsg = new StringBuilder(msg).reverse().toString();

                    dos.writeUTF(rMsg);
                    dos.flush();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
