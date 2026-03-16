package B5_Session5.B7_Ex7;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatServer
{
    public static void main(String[] args)
    {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server đang đợi Client kết nối...");
            Socket socket = serverSocket.accept();
            System.out.println("Client đã kết nối!");

            // Luồng nhận tin nhắn
            Thread receiveThread = new Thread(() -> {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
                {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println("\nClient: " + msg);
                        System.out.print("Server: "); // Giữ prompt nhập liệu
                    }
                } catch (IOException e) {
                    System.out.println("Mất kết nối với Client.");
                }
            });

            // Luồng gửi tin nhắn
            Thread sendThread = new Thread(() -> {
                try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        System.out.print("Server: ");
                        String msg = scanner.nextLine();
                        out.println(msg);
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