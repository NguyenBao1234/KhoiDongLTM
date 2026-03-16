package B5_Session5.B4_Ex4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PrimeNumberServer
{
    public static void main(String[] args)
    {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
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
                    if (inputLine != null)
                    {
                        try
                        {
                            double number = Double.parseDouble(inputLine);

                            var result = checkPrime(number);

                            System.out.println("Nhận số: " + number + " -> Trả về: " + result);
                            printWriter.println(result);
                        }
                        catch (NumberFormatException e) {
                            printWriter.println("Lỗi: Vui lòng gửi một con số hợp lệ!");
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Lỗi khi xử lý client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi Server: " + e.getMessage());
        }
    }
    public static String checkPrime(double n)
    {
        if (n != Math.floor(n)) return "No";
        int num = (int) n;
        if (num <= 1) return " Yes";
        for (int i = 2; i <= Math.sqrt(num); i++) if (num % i == 0) return "No";
        return "Yes";
    }
}
