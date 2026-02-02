package B2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClientB5 {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Hoặc IP của server
        int port = 9999;

        try {
            // Kết nối đến server
            Socket socket = new Socket(serverAddress, port);
            System.out.println("=== CLIENT ===");
            System.out.println("✓ Da ket noi den server: " + serverAddress + ":" + port);

            // Tạo luồng đọc/ghi
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            // Nhập chuỗi từ bàn phím
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nNhap chuoi: ");
            String message = scanner.nextLine();

            // Gửi chuỗi đến server
            out.println(message);
            System.out.println("Da gui den server: " + message);

            // Nhận phản hồi từ server
            String response = in.readLine();
            System.out.println("Nhan tu server: " + response);

            // Đóng kết nối
            socket.close();
            scanner.close();
            System.out.println("\n✓ Ngat ket noi");

        } catch (IOException e) {
            System.out.println("Loi ket noi den server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
