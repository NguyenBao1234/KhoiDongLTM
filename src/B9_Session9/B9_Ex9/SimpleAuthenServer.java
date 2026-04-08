package B9_Session9.B9_Ex9;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleAuthenServer
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
                    var msgs = msg.split(" ");
                    String response = "TK or MK khong dung";
                    if(msgs[0].equals("admin") && msgs[1].equals("admin123")) response = "Dang nhap thanh cong";
                    dos.writeUTF(response);
                    dos.flush();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
