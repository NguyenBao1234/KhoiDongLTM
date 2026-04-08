package B9_Session9.B4_Ex4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPFileServer
{
    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server truyền file đang đợi kết nối...");

            while (true)
            {
                try (Socket socket = serverSocket.accept()) {
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                    // 1. Nhận tên file từ Client

                    String clientFileName = dis.readUTF();

                    // Sử dụng File.separator để tương thích mọi hệ điều hành (Windows/Linux)
                    String path = "E:\\Applications and Programs\\JetBrains Inteli J\\IntelliJ IDEA Community Edition 2025.2.4\\Projects\\KhoiDongLTM\\src\\B9_Session9\\B4_Ex4\\"+ clientFileName;
                    File file = new File(path);
                    System.out.println("Đang tìm file tại: " + file.getAbsolutePath());

                    if (file.exists() && file.isFile()) {
                        // 2. Gửi thông báo file tồn tại và độ dài file
                        dos.writeUTF("OK");
                        dos.writeLong(file.length());

                        // 3. Đọc file và gửi dữ liệu
                        try (FileInputStream fis = new FileInputStream(file)) {
                            byte[] buffer = new byte[4096]; // Bộ đệm 4KB
                            int bytesRead;
                            while ((bytesRead = fis.read(buffer)) != -1) {
                                dos.write(buffer, 0, bytesRead);
                            }
                            dos.flush();
                        }
                        System.out.println("Đã gửi file: " + path);
                    } else {
                        dos.writeUTF("ERROR: File không tồn tại!");
                    }
                } catch (IOException e) {
                    System.out.println("Lỗi xử lý: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
