package B5_Session5.B12_Ex12;
import java.io.*;
import java.net.*;

public class ObjectClient
{
    public static void main(String[] args)
    {
        String host = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(host, port);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {

            // Tạo một đối tượng Student mới
            Student s1 = new Student("Nguyen Van A", 20, 8.5);

            System.out.println("Đang gửi đối tượng Student sang Server...");

            // Gửi đối tượng
            oos.writeObject(s1);
            oos.flush();

            System.out.println("Gửi thành công!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
