package B5_Session5.B11_Ex11;

import java.io.*;
import java.net.*;

public class LargeFileClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(host, port);
             DataInputStream dis = new DataInputStream(socket.getInputStream()))
        {

            // 1. Nhận thông tin file
            String fileName = dis.readUTF();
            long fileSize = dis.readLong();
            System.out.println("Đang nhận file: " + fileName + " [" + fileSize + " bytes]");

            try (FileOutputStream fos = new FileOutputStream("received_" + fileName)) {
                byte[] buffer = new byte[4096]; // Block 4KB
                int bytesRead;
                long totalReceived = 0;

                // 2. Nhận từng block và cập nhật tiến trình
                while (totalReceived < fileSize &&
                        (bytesRead = dis.read(buffer, 0, (int)Math.min(buffer.length, fileSize - totalReceived))) != -1) {

                    fos.write(buffer, 0, bytesRead);
                    totalReceived += bytesRead;

                    // Tính toán %
                    int progress = (int) ((totalReceived * 100) / fileSize);
                    printProgressBar(progress, totalReceived, fileSize);
                }
            }
            System.out.println("\n\nLưu file thành công!");

        } catch (IOException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }

    // Hàm hiển thị thanh tiến trình trên Console
    private static void printProgressBar(int percent, long current, long total) {
        StringBuilder bar = new StringBuilder("[");
        int filled = percent / 2; // Thanh dài 50 ký tự
        for (int i = 0; i < 50; i++) {
            if (i < filled) bar.append("=");
            else if (i == filled) bar.append(">");
            else bar.append(" ");
        }
        bar.append("] ").append(percent).append("% (").append(current / 1024).append("/").append(total / 1024).append(" KB)");
        System.out.print("\r" + bar.toString()); // \r để ghi đè lên dòng cũ
    }
}