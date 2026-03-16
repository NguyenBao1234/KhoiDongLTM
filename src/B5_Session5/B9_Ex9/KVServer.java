package B5_Session5.B9_Ex9;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class KVServer {
    // Sử dụng HashMap để lưu trữ dữ liệu trong bộ nhớ (In-memory)
    private static final Map<String, String> database = new HashMap<>();

    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Mini-KeyValue Server đang chạy tại cổng " + port + "...");

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    String inputLine = in.readLine();
                    if (inputLine != null) {
                        String response = processCommand(inputLine.trim());
                        out.println(response);
                    }
                } catch (IOException e) {
                    System.out.println("Lỗi xử lý kết nối: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processCommand(String commandLine) {
        // Tách lệnh dựa trên khoảng trắng
        String[] parts = commandLine.split("\\s+", 3);
        String action = parts[0].toUpperCase();

        switch (action) {
            case "SET":
                if (parts.length < 3) return "ERROR: Cú pháp phải là SET <key> <value>";
                database.put(parts[1], parts[2]);
                return "OK: Đã lưu " + parts[1];

            case "GET":
                if (parts.length < 2) return "ERROR: Cú pháp phải là GET <key>";
                String value = database.get(parts[1]);
                return (value != null) ? value : "ERROR: Key không tồn tại";

            default:
                return "ERROR: Lệnh không hợp lệ (Dùng SET hoặc GET)";
        }
    }
}