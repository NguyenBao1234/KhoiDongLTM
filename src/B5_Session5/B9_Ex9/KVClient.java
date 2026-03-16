package B5_Session5.B9_Ex9;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class KVClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n--- MENU ---");
                System.out.println("1. SET (Lưu dữ liệu)");
                System.out.println("2. GET (Lấy dữ liệu)");
                System.out.println("3. Thoát");
                System.out.print("Chọn: ");

                String choice = scanner.nextLine();
                String command = "";

                if (choice.equals("1")) {
                    System.out.print("Nhập key: ");
                    String k = scanner.nextLine();
                    System.out.print("Nhập value: ");
                    String v = scanner.nextLine();
                    command = "SET " + k + " " + v;
                } else if (choice.equals("2")) {
                    System.out.print("Nhập key cần lấy: ");
                    String k = scanner.nextLine();
                    command = "GET " + k;
                } else if (choice.equals("3")) break;
                else continue;

                // Gửi tới Server
                try (Socket socket = new Socket(host, port);
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    out.println(command);
                    String response = in.readLine();
                    System.out.println("=> Server trả về: " + response);
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi kết nối: " + e.getMessage());
        }
    }
}
