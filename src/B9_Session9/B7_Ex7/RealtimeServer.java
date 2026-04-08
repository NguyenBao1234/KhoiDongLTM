package B9_Session9.B7_Ex7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;

public class RealtimeServer
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
                    String response = "unexpected request";
                    if(msg.equals("time"))
                    {
                        response = LocalTime.now().toString();
                    }

                    dos.writeUTF(response);
                    dos.flush();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
