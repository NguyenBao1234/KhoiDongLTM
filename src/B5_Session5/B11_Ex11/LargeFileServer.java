package B5_Session5.B11_Ex11;

import java.io.*;
import java.net.*;

public class LargeFileServer {
    public static void main(String[] args) {
        int port = 5000;
        String filePath = "LargeFile.zip";

        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server sẵn sàng truyền file lớn...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                     FileInputStream fis = new FileInputStream(filePath)) {

                    File file = new File(filePath);
                    long fileSize = file.length();

                    // 1. Gửi tên file và kích thước file trước
                    dos.writeUTF(file.getName());
                    dos.writeLong(fileSize);

                    // 2. Chia nhỏ file thành block 4KB để gửi
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    System.out.println("Đang gửi file: " + file.getName() + " (" + fileSize + " bytes)");

                    while ((bytesRead = fis.read(buffer)) != -1){
                        dos.write(buffer, 0, bytesRead);
                    }
                    dos.flush();
                    System.out.println("==> Gửi hoàn tất!");

                } catch (FileNotFoundException e) {
                    System.err.println("Không tìm thấy file!");
                } catch (IOException e) {
                    System.err.println("Lỗi kết nối: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}