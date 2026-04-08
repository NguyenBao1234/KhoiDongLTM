package B9_Session9.B3_Ex3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ThreadChatTCPServer
{
    public static void main(String[] args)
    {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {

            System.out.println("Server đang đợi kết nối tại cổng 5000...");
            Socket socket = serverSocket.accept();
            System.out.println("Client đã kết nối!");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // Luồng nhận tin nhắn
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true)
                    {
                        String msg = dis.readUTF();
                        System.out.println("\nClient: " + msg);
                        System.out.print("Server: ");
                    }
                } catch (IOException e) {
                    System.out.println("Kết nối đã đóng.");
                }
            });

            // Luồng gửi tin nhắn
            Thread sendThread = new Thread(() -> {
                Scanner sc = new Scanner(System.in);
                try {
                    while (true)
                    {
                        System.out.print("Server: ");
                        String msg = sc.nextLine();
                        dos.writeUTF(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            receiveThread.start();
            sendThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
