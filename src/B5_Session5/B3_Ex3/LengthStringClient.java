package B5_Session5.B3_Ex3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class LengthStringClient
{
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(hostname, port)) {
            // Luồng gửi dữ liệu sang Server
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            // Luồng nhận dữ liệu từ Server
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Nhập chuỗi từ bàn phím
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập chuỗi muốn gửi: ");
            String message = scanner.nextLine();

            // Gửi sang Server
            printWriter.println(message);

            // Nhận kết quả
            String response = bufferedReader.readLine();
            System.out.println("Kết quả từ Server: " + response);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
