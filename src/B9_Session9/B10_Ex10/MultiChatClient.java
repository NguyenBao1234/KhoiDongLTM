package B9_Session9.B10_Ex10;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 7000);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // Thread nhận tin nhắn từ Server (Broadcast)
            new Thread(() -> {
                try {
                    while (true) {
                        System.out.println("\n" + dis.readUTF());
                        System.out.print("> ");
                    }
                } catch (IOException e) {
                    System.out.println("Mất kết nối Server.");
                }
            }).start();

            // Thread gửi tin nhắn
            Scanner sc = new Scanner(System.in);
            while (true) {
                String msg = sc.nextLine();
                dos.writeUTF(msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}