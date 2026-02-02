package B2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleSeverB5
{
    public static void main(String[] args) {
        int port = 9999;

        try {
            // Tạo ServerSocket lắng nghe trên port 9999
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("=== SERVER DANG CHAY ===");
            System.out.println("Server dang lang nghe tai port: " + port);
            System.out.println("Cho client ket noi...\n");

            // Vòng lặp chờ client kết nối
            while (true) {
                // Chấp nhận kết nối từ client
                Socket clientSocket = serverSocket.accept();
                System.out.println("✓ Client da ket noi: " + clientSocket.getInetAddress());

                // Tạo luồng đọc/ghi
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream())
                );
                PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream(), true
                );

                // Đọc dữ liệu từ client
                String message = in.readLine();
                System.out.println("Nhan tu client: " + message);

                // Chuyển thành chữ HOA
                String upperCase = message.toUpperCase();
                System.out.println("Gui lai client: " + upperCase);

                // Gửi lại cho client
                out.println(upperCase);

                // Đóng kết nối
                clientSocket.close();
                System.out.println("Client da ngat ket noi\n");
            }

        } catch (IOException e) {
            System.out.println("Loi server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

