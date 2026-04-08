package B9_Session9.B3_Ex3;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ThreadChatTCPClient
{
    public static void main(String[] args)
    {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Đã kết nối tới Server!");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // Luồng nhận tin nhắn
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String msg = dis.readUTF();
                        System.out.println("\nServer: " + msg);
                        System.out.print("Client: ");
                    }
                } catch (IOException e) {
                    System.out.println("Mất kết nối với Server.");
                }
            });

            // Luồng gửi tin nhắn
            Thread sendThread = new Thread(() -> {
                Scanner sc = new Scanner(System.in);
                try {
                    while (true) {
                        System.out.print("Client: ");
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
