package B5_Session5.B5_Ex5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SumServer {
    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server (DataStream) đang đợi kết nối...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream dis = new DataInputStream(socket.getInputStream());
                     DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

                    // Bước 1: Đọc số lượng phần tử mà Client định gửi
                    int count = dis.readInt();
                    double sum = 0;

                    // Bước 2: Lặp đúng 'count' lần để nhận từng số
                    for (int i = 0; i < count; i++) {
                        double num = dis.readDouble();
                        sum += num;
                    }

                    // Bước 3: Gửi kết quả tổng về cho Client
                    dos.writeDouble(sum);
                    dos.flush();

                    System.out.println("Đã xử lý xong danh sách gồm " + count + " số. Tổng = " + sum);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}