package B5_Session5.B5_Ex5;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SumClient
{
    public static void main(String[] args) {
        String serverIp = "127.0.0.1";
        int port = 5000;

        try (Socket socket = new Socket(serverIp, port);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("(VD Danh Sach: 1 4 5 7)");
            String line = scanner.nextLine();
            String[] parts = line.trim().split("\\s+");

            // Bước 1: Gửi số lượng phần tử trước để Server biết đường mà lặp
            dos.writeInt(parts.length);

            // Bước 2: Gửi từng số một dưới dạng Double
            for (String part : parts) {
                dos.writeDouble(Double.parseDouble(part));
            }
            dos.flush();

            // Bước 3: Nhận kết quả cuối cùng
            double sum = dis.readDouble();
            System.out.println("Tổng nhận được từ Server: " + sum);

        } catch (IOException | NumberFormatException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
}
