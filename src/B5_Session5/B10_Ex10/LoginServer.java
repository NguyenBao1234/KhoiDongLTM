package B5_Session5.B10_Ex10;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class LoginServer {
    // Lưu danh sách tài khoản cố định
    private static final Map<String, String> users = new HashMap<>();

    static {
        users.put("user1", "123");
        users.put("user2", "456");
        users.put("admin", "admin123");
    }

    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Login Server đang đợi kết nối...");

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    // Nhận chuỗi từ Client (định dạng: "username password")
                    String input = in.readLine();
                    if (input != null) {
                        String[] parts = input.trim().split("\\s+");

                        if (parts.length == 2) {
                            String username = parts[0];
                            String password = parts[1];

                            // Kiểm tra logic đăng nhập
                            if (users.containsKey(username) && users.get(username).equals(password)) {
                                out.println("LOGIN SUCCESS");
                                System.out.println("Đăng nhập thành công: " + username);
                            } else {
                                out.println("LOGIN FAIL");
                                System.out.println("Đăng nhập thất bại: " + username);
                            }
                        } else {
                            out.println("ERROR: Sai định dạng gửi");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}