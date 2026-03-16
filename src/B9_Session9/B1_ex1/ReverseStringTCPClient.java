package B9_Session9.B1_ex1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ReverseStringTCPClient
{

    public static void main(String[] args)
    {
        String hostname = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(hostname, port))
        {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập chuỗi muốn gửi: ");
            String message = scanner.nextLine();

            // Gửi sang Server
            dos.writeUTF(message);
            dos.flush();

            // Nhận kết quả
            String response = dis.readUTF();
            System.out.println("Kết quả từ Server: " + response);

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}