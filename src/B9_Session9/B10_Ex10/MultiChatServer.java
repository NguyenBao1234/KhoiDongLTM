package B9_Session9.B10_Ex10;

import java.io.*;
import java.net.*;
import java.util.*;

public class MultiChatServer {
    // Dùng Vector vì nó Thread-safe (an toàn khi nhiều luồng cùng truy cập)
    private static Vector<DataOutputStream> clientStreams = new Vector<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(7000)) {
            System.out.println("Multi-Client Server đang chạy tại cổng 7000...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Một Client mới đã tham gia: " + socket);

                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                clientStreams.add(dos); // Thêm vào danh sách để broadcast

                // Tạo luồng riêng để xử lý Client này
                Thread clientThread = new Thread(new ClientHandler(socket, dos));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm gửi tin nhắn tới tất cả mọi người
    public static void broadcast(String message) {
        for (DataOutputStream out : clientStreams) {
            try {
                out.writeUTF(message);
                out.flush();
            } catch (IOException e) {
                // Nếu lỗi (Client thoát đột ngột), có thể xóa khỏi list ở đây
            }
        }
    }

    // Lớp nội bộ để xử lý từng Client
    static class ClientHandler implements Runnable {
        private Socket socket;
        private DataOutputStream dos;

        public ClientHandler(Socket socket, DataOutputStream dos) {
            this.socket = socket;
            this.dos = dos;
        }

        @Override
        public void run() {
            try (DataInputStream dis = new DataInputStream(socket.getInputStream())) {
                while (true) {
                    String msg = dis.readUTF();
                    System.out.println("Nhận được: " + msg);
                    // Broadcast tin nhắn nhận được cho tất cả các Client
                    broadcast("Client [" + socket.getPort() + "]: " + msg);
                }
            } catch (IOException e) {
                System.out.println("Client " + socket.getPort() + " đã rời phòng.");
            } finally {
                clientStreams.remove(dos); // Xóa khỏi danh sách khi ngắt kết nối
            }
        }
    }
}
