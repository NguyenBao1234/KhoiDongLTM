package B9_Session9.B8_Ex8;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;

public class CharCountServer
{
    public static void main(String[] args)
    {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server (DataStream) đang đợi kết nối...");

            while (true)
            {
                try (Socket socket = serverSocket.accept();
                     DataInputStream dis = new DataInputStream(socket.getInputStream());
                     DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

                    var msg = dis.readUTF();
                    int count = msg.trim().replace(" ","").length();
                    dos.writeUTF("Số ký tự: "+count);
                    dos.flush();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
