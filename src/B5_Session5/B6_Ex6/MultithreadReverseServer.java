package B5_Session5.B6_Ex6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultithreadReverseServer
{
    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server đa luồng đang chạy tại cổng " + port + "...");

            while (true)
            {
                // Chấp nhận kết nối từ Client
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress());

                // Tạo một Thread mới để xử lý Client này
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread
    {
        private Socket socket;
        public ClientHandler(Socket socket) { this.socket = socket;}

        @Override
        public void run()
        {
            try (
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String inputLine;
                // Server giữ kết nối và xử lý cho đến khi Client ngắt
                while ((inputLine = bufferedReader.readLine()) != null) {
                    System.out.println("Nhận từ " + socket.getInetAddress() + ": " + inputLine);

                    // Đảo ngược chuỗi
                    String reversed = new StringBuilder(inputLine).reverse().toString();

                    // Gửi lại cho Client
                    printWriter.println(reversed);
                }
            } catch (IOException e) {
                System.out.println("Client rời đi hoặc lỗi: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
