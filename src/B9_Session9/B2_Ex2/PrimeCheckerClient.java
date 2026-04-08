package B9_Session9.B2_Ex2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class PrimeCheckerClient
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
            System.out.print("Nhập số muốn check: ");
            String numberString = scanner.nextLine();


            // Gửi sang Server
            dos.writeDouble(Double.parseDouble(numberString));
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
