package B5_Session5.B2_Ex2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PowClient
{
    public static void main(String[] args)
    {
        String hostname = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(hostname, port))
        {
            // Luồng gửi dữ liệu sang Server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // Luồng nhận dữ liệu từ Server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Nhập chuỗi từ bàn phím
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập số muốn gửi: ");
            String message = scanner.nextLine();

            // Gửi sang Server
            out.println(message);

            // Nhận kết quả
            String response = in.readLine();
            System.out.println("Kết quả từ Server: " + response);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
