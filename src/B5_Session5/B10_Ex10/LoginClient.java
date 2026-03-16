package B5_Session5.B10_Ex10;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class LoginClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())) ) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("--- ĐĂNG NHẬP HỆ THỐNG ---");
            System.out.print("Username: ");
            String user = scanner.nextLine();
            System.out.print("Password: ");
            String pass = scanner.nextLine();

            // Gửi thông tin lên Server
            out.println(user + " " + pass);

            // Nhận kết quả
            String response = in.readLine();
            System.out.println("Kết quả: " + response);

        } catch (IOException e) {
            System.err.println("Lỗi kết nối: " + e.getMessage());
        }
    }
}