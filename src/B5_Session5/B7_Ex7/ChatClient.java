package B5_Session5.B7_Ex7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient
{
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try {
            Socket socket = new Socket(host, port);
            System.out.println("Đã kết nối tới Server. Bắt đầu chat!");

            // Luồng nhận tin nhắn
            Thread receiveThread = new Thread(() -> {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println("\nServer: " + msg);
                        System.out.print("Client: ");
                    }
                } catch (IOException e) {
                    System.out.println("Mất kết nối với Server.");
                }
            });

            // Luồng gửi tin nhắn
            Thread sendThread = new Thread(() ->{
                try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        System.out.print("Client: ");
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
            System.out.println("Không thể kết nối tới Server.");
        }
    }
}
