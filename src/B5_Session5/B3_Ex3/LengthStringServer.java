package B5_Session5.B3_Ex3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class LengthStringServer
{
    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server đang chạy và đợi kết nối tại cổng " + port + "...");

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Client đã kết nối!");

                    // Tạo luồng nhận dữ liệu
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    // Tạo luồng gửi dữ liệu
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

                    // Đọc chuỗi từ client
                    String inputLine = bufferedReader.readLine();
                    if (inputLine != null) {
                        System.out.println("Nhận từ Client: " + inputLine);
                        // Gửi lại chuỗi in hoa
                        printWriter.println("Độ dài " + inputLine.length());
                    }
                } catch (IOException e) {
                    System.out.println("Lỗi khi xử lý client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi Server: " + e.getMessage());
        }
    }
}
