package B5_Session5.B12_Ex12;

import java.io.*;
import java.net.*;

public class ObjectServer {
    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server nhận đối tượng đang đợi...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

                    // Đọc đối tượng và ép kiểu về Student
                    Student student = (Student) ois.readObject();

                    System.out.println("Đã nhận đối tượng từ Client:");
                    System.out.println(student.toString());

                } catch (ClassNotFoundException e) {
                    System.err.println("Không tìm thấy lớp đối tượng!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}