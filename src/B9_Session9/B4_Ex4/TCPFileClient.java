package B9_Session9.B4_Ex4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TCPFileClient
{
    public static void main(String[] args)
    {
        String host = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(host, port);
             DataInputStream dis = new DataInputStream(socket.getInputStream());
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập tên file muốn tải từ Server: ");
            String fileName = scanner.nextLine();

            // 1. Gửi tên file yêu cầu
            dos.writeUTF(fileName);

            // 2. Nhận phản hồi từ Server
            String status = dis.readUTF();
            if (status.equals("OK")) {
                long fileSize = dis.readLong();
                System.out.println("Đang tải file (" + fileSize + " bytes)...");

                // 3. Nhận dữ liệu và lưu thành file mới (ví dụ thêm tiền tố "downloaded_")
                try (FileOutputStream fos = new FileOutputStream("downloaded_" + fileName)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    long totalRead = 0;

                    // Chỉ đọc đúng số lượng bytes của file
                    while (totalRead < fileSize && (bytesRead = dis.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalRead))) != -1) {
                        fos.write(buffer, 0, bytesRead);
                        totalRead += bytesRead;
                    }
                }
                System.out.println("Tải file hoàn tất! Lưu tại: downloaded_" + fileName);
            } else {
                System.out.println("Lỗi từ Server: " + status);
            }

        } catch (IOException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
}
